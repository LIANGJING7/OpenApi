package com.jing.project.jinggateway;
import com.jing.jingclientsdk.utils.SignUtils;
import com.jing.project.jingcommon.model.entity.InterfaceInfo;
import com.jing.project.jingcommon.model.entity.User;
import com.jing.project.service.impl.inner.InnerInterfaceInfoService;
import com.jing.project.service.impl.inner.InnerUserInterfaceInfoService;
import com.jing.project.service.impl.inner.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private ServerHttpRequest request;
    private ServerHttpResponse response;
    private String HOST_URL="http://localhost:8090";
   private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        request = exchange.getRequest();
        String path = HOST_URL + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识"+request.getId());
        log.info("请求路径"+path);
        log.info("请求方法"+method);
        log.info("请求参数"+request.getQueryParams());
        log.info("请求来源地址"+request.getLocalAddress().getHostString());
        log.info("请求来源地址+端口号"+request.getRemoteAddress());
        String sourceAddress = request.getLocalAddress().getHostString();
        response = exchange.getResponse();
//        1.访问控制黑白名单
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            return handleNoAuth(response);
        }
//        2.用户鉴权(判断ak/sk是否合法)
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        User invokeUser = null;
        try{
           invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
          log.error("getInvokeUser error",e);
        }
        if (invokeUser == null){
            return handleNoAuth(response);
        }
//      进行认证数据库中查找secretKey 进行md5单向加密
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(timestamp, secretKey);
        if(!serverSign.equals(sign)){
            return handleNoAuth(response);
        }
        Long currentTime = System.currentTimeMillis() / 1000L;
        final Long FIVE_MINUTES = 60 * 5L;
//     3.  时间不能超过5分钟
        if((Long.parseLong(timestamp) - currentTime)>FIVE_MINUTES){
            return handleNoAuth(response);
        }
//     4.      判断模拟接口是否存在
        InterfaceInfo interfaceInfo = null;
        try{
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        }catch(Exception e){
            log.error("getInterfaceInfo is error",e);
        }
        if(interfaceInfo == null){
            return handleNoAuth(response);
        }
//     6.请求转发调用模拟接口+ 响应日志
      return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());

    }
    /**
     * 无权限响应
     * @param response
     * @return
     */
    public Mono handleNoAuth( ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
    public Mono handleInvokeError( ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
//            缓存数据工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
//            拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
//                装饰,增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//                等调用完转发接口才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往返回值里写数据
//                            拼接字符串
                            return super.writeWith(
                                  fluxBody.map(dataBuffer -> {
                                      //6.todo:调用次数+1 更改数据库中的调用次数与剩余次数 invokeCount+1
                                      try {
                                          innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                      } catch (Exception e) {
                                          log.error("invokeCount is error",e);
                                      }
                                      byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    DataBufferUtils.release(dataBuffer);//释放掉内存
                                    // 构建日志
                                    StringBuilder sb2 = new StringBuilder(200);
                                    sb2.append("<--- {} {} \n");
                                    List<Object> rspArgs = new ArrayList<>();
                                    rspArgs.add(originalResponse.getStatusCode());
                                    //rspArgs.add(requestUrl);
                                    String data = new String(content, StandardCharsets.UTF_8);//data
                                    sb2.append(data);
//                                    打印日志
                                    log.info("响应结果"); //log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                    return bufferFactory.wrap(content);
                                }));
                        } else {
                            // 7.调用失败,返回错误响应码
                            handleNoAuth(response);
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关响应错误\n" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
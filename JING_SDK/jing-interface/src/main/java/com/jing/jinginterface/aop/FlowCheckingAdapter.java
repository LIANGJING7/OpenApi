package com.jing.jinginterface.aop;

import com.jing.jinginterface.common.ErrorCode;
import com.jing.jinginterface.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class FlowCheckingAdapter {

    @Around("execution(* com.jing.jinginterface.controller..*.*(..))")
    public Object flowCheckingAdapter(ProceedingJoinPoint joinPoint) throws Throwable{
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String jing = request.getHeader("jing");
        if(!"jinggateway".equals(jing)){
         throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return joinPoint.proceed();

    }
}

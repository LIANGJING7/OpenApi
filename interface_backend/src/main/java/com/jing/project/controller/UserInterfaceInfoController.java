package com.jing.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jing.project.common.DeleteRequest;
import com.jing.project.constant.CommonConstant;
import com.jing.project.exception.BusinessException;
import com.jing.project.jingcommon.common.BaseResponse;
import com.jing.project.jingcommon.common.ErrorCode;
import com.jing.project.jingcommon.common.ResultUtils;
import com.jing.project.jingcommon.model.entity.User;
import com.jing.project.jingcommon.model.entity.UserInterfaceInfo;
import com.jing.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.jing.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.jing.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.jing.project.service.UserInterfaceInfoService;
import com.jing.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/userInterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param userInterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        // 校验
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userInterfaceInfo.setUserId(loginUser.getId());
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newUserInterfaceInfoId = userInterfaceInfo.getId();
        return ResultUtils.success(newUserInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        if (oldUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param userInterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
        // 参数校验
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = userInterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userInterfaceInfoService.getById(id);
        if (oldUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserInterfaceInfo> getUserInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        return ResultUtils.success(userInterfaceInfo);
    }

    /**
     * 获取列表
     *
     * @param userInterfaceInfoQueryRequest
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfo(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        UserInterfaceInfo userInterfaceInfoQuery = new UserInterfaceInfo();
        if (userInterfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(userInterfaceInfoQueryRequest, userInterfaceInfoQuery);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfoQuery);
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.list(queryWrapper);
        return ResultUtils.success(userInterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param userInterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest, HttpServletRequest request) {
        if (userInterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfoQuery = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoQueryRequest, userInterfaceInfoQuery);
        long current = userInterfaceInfoQueryRequest.getCurrent();
        long size = userInterfaceInfoQueryRequest.getPageSize();
        String sortField = userInterfaceInfoQueryRequest.getSortField();
        String sortOrder = userInterfaceInfoQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userInterfaceInfoQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfo> userInterfaceInfoPage = userInterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(userInterfaceInfoPage);
    }

    @PostMapping("/getCount")
    public BaseResponse<Boolean> getUserInterfaceInvokeCount(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest, HttpServletRequest request){
        Long userId = userInterfaceInfoUpdateRequest.getUserId();
        Long interfaceInfoId = userInterfaceInfoUpdateRequest.getInterfaceInfoId();
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        if (userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getUserId() <= 0 || userInterfaceInfoUpdateRequest.getInterfaceInfoId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        1.判断接口状态是否为0 下线
        if(userInterfaceInfoUpdateRequest.getStatus() <= 0 ){
            throw new BusinessException(ErrorCode.INTERFACEINVOKE_ERROR);
        }
        userInterfaceInfoUpdateRequest.setLeftNum(10);
        BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
//        2.判断user_interfaceInfo表是否有该接口数据,没有则插入数据
        QueryWrapper <UserInterfaceInfo>  queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId",userId);
        queryWrapper.eq("interfaceInfoId",interfaceInfoId);
        UserInterfaceInfo newUserInterfaceInfo = userInterfaceInfoService.getOne(queryWrapper);
        if(newUserInterfaceInfo == null){
            boolean save = userInterfaceInfoService.save(userInterfaceInfo);
            return  ResultUtils.success(save);
        }
        //        3.判断次数是否超过100
        if(!(newUserInterfaceInfo.getLeftNum()+10 <= 100)){
             throw new BusinessException(ErrorCode.INTERFACEINVOKE_ERROR);
        }

//        4.向user_interfaceInfo 表更新数据
        Integer leftNum = newUserInterfaceInfo.getLeftNum();
        newUserInterfaceInfo.setLeftNum(leftNum + 10);
        boolean result = userInterfaceInfoService.updateById(newUserInterfaceInfo);
        return ResultUtils.success(result);
    }

    @GetMapping("/getLeftNum")
    public BaseResponse<Integer> getUserInterfaceInvokeCount(UserInterfaceInfoAddRequest userInterfaceInfoAddRequest,HttpServletRequest request){
        Long userId = userInterfaceInfoAddRequest.getUserId();
        Long interfaceInfoId = userInterfaceInfoAddRequest.getInterfaceInfoId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        UserInterfaceInfo one = userInterfaceInfoService.getOne(queryWrapper);
        System.out.println("--------------------------------------"+one);
        return ResultUtils.success(one.getLeftNum());
    }
//    /**
//     *
//     * 根据条件查询
//     * @param userInterfaceInfoQueryRequest
//     * @param request
//     * @return
//     */
//    @GetMapping("/list/condition")
//    public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfoByCondition(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest,HttpServletRequest request){
////        if(userInterfaceInfoQueryRequest == null){
////            throw new BusinessException(ErrorCode.PARAMS_ERROR);
////        }
////        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
////        BeanUtils.copyProperties(userInterfaceInfoQueryRequest,userInterfaceInfo);
////        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoService.getUserInterfaceInfoByCondition(userInterfaceInfo);
////        return ResultUtils.success(userInterfaceInfoList);
//
//
//    }


    // endregion

}

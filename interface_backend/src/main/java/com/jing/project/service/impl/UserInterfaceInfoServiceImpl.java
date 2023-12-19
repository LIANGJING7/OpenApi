package com.jing.project.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.project.exception.BusinessException;
import com.jing.project.jingcommon.common.ErrorCode;
import com.jing.project.jingcommon.model.entity.UserInterfaceInfo;
import com.jing.project.mapper.UserInterfaceInfoMapper;

import com.jing.project.service.UserInterfaceInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userid = userInterfaceInfo.getUserId();
        Long interfaceinfoid = userInterfaceInfo.getInterfaceInfoId();

//        创建时
        if (add) {
            if (userid == 0 || interfaceinfoid == 0 || ObjectUtils.anyNull(userid, interfaceinfoid)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }

    @Override
    @CacheEvict(value = "user")
    public boolean invokeCount(long interfaceInfoId, long userId) {
//        判断
        if(interfaceInfoId <= 0 || userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceInfoId);
        updateWrapper.eq("userId",userId);
        updateWrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }

}






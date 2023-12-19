package com.jing.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jing.project.jingcommon.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo post, boolean add);
    boolean invokeCount(long interfaceInfoId,long userId);

}

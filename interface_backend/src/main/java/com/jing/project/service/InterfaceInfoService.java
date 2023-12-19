package com.jing.project.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.jing.project.jingcommon.model.entity.InterfaceInfo;
import com.jing.project.model.dto.interfaceinfo.PersonInterfaceInfoDataRequest;

import java.util.List;


/**
 *
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo post, boolean add);

    List<InterfaceInfo> getInterfaceInfoByCondition(InterfaceInfo interfaceInfo);

    List<PersonInterfaceInfoDataRequest> getPersonInterfaceInfo(Long userId);
}

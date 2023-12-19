package com.jing.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.jing.project.jingcommon.model.entity.InterfaceInfo;
import com.jing.project.model.dto.interfaceinfo.PersonInterfaceInfoDataRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Entity generator.domain.InterfaceInfo
 */
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    List<InterfaceInfo> getInterfaceInfoByCondition( @Param("interfaceInfo") InterfaceInfo interfaceInfo);

    List<PersonInterfaceInfoDataRequest> getPersonInterfaceInfo( @Param("userId") Long userId);

}





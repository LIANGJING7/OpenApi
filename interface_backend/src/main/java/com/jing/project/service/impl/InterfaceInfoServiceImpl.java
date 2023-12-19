package com.jing.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jing.project.exception.BusinessException;
import com.jing.project.jingcommon.common.ErrorCode;
import com.jing.project.jingcommon.model.entity.InterfaceInfo;
import com.jing.project.mapper.InterfaceInfoMapper;
import com.jing.project.model.dto.interfaceinfo.PersonInterfaceInfoDataRequest;
import com.jing.project.service.InterfaceInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String url = interfaceInfo.getUrl();
        String name = interfaceInfo.getName();
        // 创建时，参数不能为空
        if (add) {
            if (StringUtils.isAnyBlank(url, name) || ObjectUtils.anyNull(url, name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }

    @Override
    @Cacheable(cacheNames = "user", key = "'id'")
    public List<InterfaceInfo> getInterfaceInfoByCondition(InterfaceInfo interfaceInfo) {

        return interfaceInfoMapper.getInterfaceInfoByCondition(interfaceInfo);
    }

    @Override
    @Cacheable(cacheNames = "user", key = "'userId'")
    public List<PersonInterfaceInfoDataRequest> getPersonInterfaceInfo(Long userId) {
        return interfaceInfoMapper.getPersonInterfaceInfo(userId);
    }

}





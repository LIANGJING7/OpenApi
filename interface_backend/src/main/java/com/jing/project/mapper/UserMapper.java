package com.jing.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jing.project.jingcommon.model.entity.User;


/**
 * @Entity com.jing.project.model.domain.User
 */
public interface UserMapper extends BaseMapper<User> {

    User getUser(String s);

}





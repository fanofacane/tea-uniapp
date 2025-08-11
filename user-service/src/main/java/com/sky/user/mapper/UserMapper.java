package com.sky.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.user.domain.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from users where open_id = #{openid}")
    User getUserByopenId(String openid);
}

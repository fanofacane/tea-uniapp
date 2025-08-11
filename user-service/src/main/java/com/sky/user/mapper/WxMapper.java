package com.sky.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.user.domain.pojo.WxKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxMapper extends BaseMapper<WxKey> {
}

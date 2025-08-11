package com.sky.user.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("users")
public class User {
    private Integer id;
    private String account;
    private String avatar;
    private String mobile;
    private String nickname;
    private String token;
    private String openId;
    private String address;
}

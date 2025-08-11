package com.sky.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.user.domain.DTO.LoginDTO;
import com.sky.user.domain.pojo.Result;
import com.sky.user.domain.pojo.User;

public interface LoginService extends IService<User> {
    Result wxLogin(LoginDTO loginDTO);
}

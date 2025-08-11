package com.sky.user.controller;
import com.sky.user.domain.DTO.LoginDTO;
import com.sky.user.domain.pojo.Result;
import com.sky.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("wxLogin")
@RestController
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result login(@RequestBody LoginDTO loginDTO) {
        log.info("登录:{}",loginDTO);
        return loginService.wxLogin(loginDTO);

    }
}

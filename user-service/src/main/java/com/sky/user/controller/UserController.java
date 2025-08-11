package com.sky.user.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ChatClient chatClient;
    @GetMapping(value = "/modifyAddress")
    public Address addressConverted(@RequestParam String address){
        return chatClient.prompt()
                .user(address)
                .call()
                .entity(Address.class);
    }
    public record Address(
            String nickName,    // 收件人姓名
            String mobile,      // 联系电话
            String province,    // 省
            String city,        // 市
            String district,    // 区/县
            String detail       // 详细地址
    ) {}
}

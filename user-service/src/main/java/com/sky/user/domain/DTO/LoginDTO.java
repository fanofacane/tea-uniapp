package com.sky.user.domain.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    private String code;
    private String imgUrl;
    private String nickName;
}

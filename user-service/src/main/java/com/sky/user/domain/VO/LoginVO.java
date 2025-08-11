package com.sky.user.domain.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {
    private String account;
    private String avatar;
    private Integer id;
    private String mobile;
    private String nickname;
    private String token;
    private String address;
}

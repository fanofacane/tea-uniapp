package com.sky.user.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WxKey {
    private Integer id;
    private String session_key;
    private String open_id;

    public WxKey(String open_id, String session_key) {
        this.open_id = open_id;
        this.session_key = session_key;
    }
}

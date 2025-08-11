package com.sky.user.service.serviceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.user.domain.DTO.LoginDTO;
import com.sky.user.domain.VO.LoginVO;
import com.sky.user.domain.pojo.Result;
import com.sky.user.domain.pojo.User;
import com.sky.user.domain.pojo.WxKey;
import com.sky.user.mapper.UserMapper;
import com.sky.user.mapper.WxMapper;
import com.sky.user.service.LoginService;
import com.sky.user.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    // 微信小程序配置信息
    private static final String APPID = "wxc78ae1332ddd4fb4";
    private static final String SECRET = "cd8d847ae6bef30c8da8dbafcba27423";
    private static final String JSCODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WxMapper wxMapper;
    @Override
    public Result wxLogin(LoginDTO loginDTO) {
        try {
            // 1. 从请求中获取前端传来的code
            if (loginDTO.getCode() == null || loginDTO.getCode().isEmpty()) {
                return Result.error("code为空");
            }

            // 2. 构建请求参数
            Map<String, String> params = new HashMap<>();
            params.put("appid", APPID);
            params.put("secret", SECRET);
            params.put("js_code", loginDTO.getCode());
            params.put("grant_type", "authorization_code");

            // 3. 发送请求到微信服务器获取session_key和openid
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(
                    JSCODE2SESSION_URL + "?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}",
                    String.class,
                    params
            );
            System.err.println("微信返回的数据"+response);
            // 4. 解析微信返回的JSON数据
            JSONObject jsonObject = JSONUtil.parseObj(response);

            // 检查是否有错误
            if (jsonObject.containsKey("errcode")) {
                int errcode = jsonObject.getInt("errcode");
                String errmsg = jsonObject.getStr("errmsg");
                return Result.error("微信登录失败"+"errcode"+errcode+"errmsg"+errmsg);
            }

            // 5. 获取openid和session_key
            String openid = jsonObject.getStr("openid");
            String sessionKey = jsonObject.getStr("session_key");

            if (openid == null || sessionKey == null) {
                return Result.error("获取openid或session_key失败");
            }
            //6.查询是否存在该用户
            User user1 = lambdaQuery().eq(User::getOpenId, openid).one();
            if(user1!=null) {
                return Result.success(user1);
            }

            // 7.生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("open_id", openid);
            claims.put("sessionKey", sessionKey);
            String jwt = JwtUtils.generateToken(claims);

            // 8. 返回自定义登录态给前端
            Random random = new Random();
            LoginVO loginVO = LoginVO.builder()
                    .token(jwt)
                    .nickname(loginDTO.getNickName())
                    .avatar(loginDTO.getImgUrl())
                    .account(String.valueOf(random.nextInt(900000) + 100000))
                    .build();
            User user = BeanUtil.toBean(loginVO, User.class);
            user.setOpenId(openid);
            save(user);
            wxMapper.insert(new WxKey(openid, sessionKey));
            loginVO.setId(user.getId());
            return Result.success(loginVO);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("服务器内部错误: " + e.getMessage());
        }
    }

    }

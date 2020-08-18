package com.plat.webm.news.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.plat.webm.exception.Result;
import com.plat.webm.news.entity.News;
import com.plat.webm.user.entity.User;
import com.plat.webm.user.service.IUserService;
import com.plat.webm.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swx
 * @since 2020-08-05
 */
@Api(value = "登录Controller", tags = {"登录接口"})
@CrossOrigin //ajax跨域注解
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    IUserService userService;

    //登录
    @ApiOperation(value = "登录", notes = "参数：登录账号userid，密码password", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "admin", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "password", paramType = "query", required = true)
    })
    @PostMapping("/login")
    public Object login(@RequestParam String userid,
                        @RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        String userId = userid == null ? "" : userid.trim();
        if (userId.equals("")) {
            jsonObject.put("code", "100");
            jsonObject.put("msg", "请输入登录账号");
            return jsonObject;
        }
        User userForBase = userService.getById(userId);
        if (userForBase == null) {
            jsonObject.put("code", "101");
            jsonObject.put("msg", "登录失败,用户不存在");
            return jsonObject;
        } else {
            String origin = password == null ? "" : password.trim();
            if (userId.equals("")) {
                jsonObject.put("code", "102");
                jsonObject.put("msg", "请输入密码");
                return jsonObject;
            }

            String md5 = MD5.getMD5(origin);
            System.out.println("---- " + md5);
            if (!userForBase.getPassword().equals(md5)) {
                jsonObject.put("code", "103");
                jsonObject.put("msg", "登录失败,密码错误");
                return jsonObject;
            } else {
                String token = getToken(userForBase);
                jsonObject.put("code", "0");
                jsonObject.put("msg", "登录成功");
                jsonObject.put("token", token);
                jsonObject.put("data", userForBase);
                return jsonObject;
            }
        }
    }

    public String getToken(User user) {
        String token = "";
        token = JWT.create().withAudience(user.getUserid())// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(user.getPassword()));// 以 password 作为 token 的密钥
        return token;
    }

    public static void checkToken(HttpServletRequest httpServletRequest, IUserService userService) {

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 执行认证
        if (token == null) {
            throw new RuntimeException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new JWTDecodeException("401");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("401");
        }
    }

    @ApiOperation(value = "修改密码", notes = "参数：原密码oldPassword，新密码password", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "oldPassword", paramType = "query", required = true),
            @ApiImplicitParam(name = "newPassword", value = "newPassword", paramType = "query", required = true),
            @ApiImplicitParam(name = "okPassword", value = "okPassword", paramType = "query", required = true)
    })
    @PostMapping("/updatePassword")
    public JSONObject updatePassword(HttpServletRequest httpServletRequest, String oldPassword, String newPassword, String okPassword) {

        JSONObject jsonObject = new JSONObject();

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 执行认证
        if (token == null) {
            throw new RuntimeException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new JWTDecodeException("401");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }

        String p1 = user.getPassword();
        if (p1.equals(MD5.getMD5(oldPassword))) {
            if (newPassword.equals(okPassword)) {
                QueryWrapper<User> ew = new QueryWrapper<>();
                ew.eq("userid", user.getUserid());
                user.setPassword(MD5.getMD5(okPassword));
                if (userService.update(user, ew)) {
                    jsonObject.put("code", "0");
                    jsonObject.put("msg", "密码修改成功！");
                } else {
                    jsonObject.put("code", "201");
                    jsonObject.put("msg", "密码修改失败！");
                }
            } else {
                jsonObject.put("code", "202");
                jsonObject.put("msg", "新密码和确认密码不一致");
            }
        } else {
            jsonObject.put("code", "203");
            jsonObject.put("msg", "原密码的录入错误");
        }
        return jsonObject;
    }


    @PostMapping("/getMessage")
    public String getMessage() {
        return "你已通过验证,ok----------------";
    }


}

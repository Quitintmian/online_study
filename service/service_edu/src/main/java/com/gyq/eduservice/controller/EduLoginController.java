package com.gyq.eduservice.controller;

import com.gyq.commonutils.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin // 解决跨域
public class EduLoginController {
    // login 返回token
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin"); // 返回token，假设为admin
    }

    // info 返回roles、name、avatar
    @GetMapping("info")
    public R info(){
        Map<String,Object> datamap = new HashMap<>();
        datamap.put("roles","[admin]");
        datamap.put("name","admin001");
        datamap.put("avatar","https://ts3.cn.mm.bing.net/th/id/OIP-C.jDcv2JKzvTces6Hfi9f8iAHaFj?w=236&h=180&c=7&r=0&o=5&dpr=1.5&pid=1.7");
        return R.ok().data(datamap);
    }
}

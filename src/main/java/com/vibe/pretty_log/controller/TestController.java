package com.vibe.pretty_log.controller;

import com.vibe.pretty_log.dto.HelloRequestDto;
import com.vibe.pretty_log.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: vibe
 * @date: 2021-12-03 15:12
 **/
@Api(description = "API测试接口")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "输出用户信息",notes = "前端接收用户基本信息后输出")
    @PostMapping("/hello")
    public String hello(@RequestBody HelloRequestDto helloRequestDto){
        return testService.hello(helloRequestDto);
    }

}

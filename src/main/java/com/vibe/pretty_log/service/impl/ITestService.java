package com.vibe.pretty_log.service.impl;

import com.vibe.pretty_log.dto.HelloRequestDto;
import com.vibe.pretty_log.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @description: 测试业务接口实现类
 * @author: vibe
 * @date: 2021-12-03 15:13
 **/
@Service
public class ITestService implements TestService {

    @Override
    public String hello(HelloRequestDto helloRequestDto) {
        return " 你好 " + helloRequestDto.getName() + " 您的年龄是： " + helloRequestDto.getAge();
    }

}


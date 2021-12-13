package com.vibe.pretty_log.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @description: 日志切面处理
 * @author: vibe
 * @date: 2021-12-03 15:06
 **/

@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 进入方法时间戳
     */
    private Long startTime;
    /**
     * 方法结束时间戳(计时)
     */
    private Long endTime;

    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(* com.vibe.pretty_log.controller.*.*(..))")
    public void logPointcut() {}

    /**
     * 前置通知：
     * 1. 在执行目标方法之前执行，比如请求接口之前的登录验证;
     * 2. 在前置通知中设置请求日志信息，如开始时间，请求参数，注解内容等
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("logPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //打印请求的内容
        startTime = System.currentTimeMillis();
        log.info("请求开始时间：{}", LocalDateTime.now());
        log.info("请求信息: {}",new RequestLog(request.getRequestURL().toString(),
                request.getRemoteAddr(),
                request.getMethod(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                joinPoint.getArgs()));
    }

    /**
     * 返回通知：
     * 1. 在目标方法正常结束之后执行
     * 2. 在返回通知中补充请求日志信息，如返回时间，方法耗时，返回值，并且保存日志信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "logPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        log.info("请求结束时间：{}", LocalDateTime.now());
        log.info("请求耗时：{} ms", (endTime - startTime));
        //处理完请求，返回内容
        log.info("请求返回 : {}", String.valueOf(ret));
    }

    /**
     * 异常通知：
     * 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     * 2. 在异常通知中设置异常信息，并将其保存
     *
     * @param throwable
     */
//    @AfterThrowing(value = "logPointcut()", throwing = "throwable")
//    public void doAfterThrowing(Throwable throwable) {
//        // 保存异常日志记录
//        log.error("发生异常时间：{}", LocalDateTime.now());
//        log.error("抛出异常：{}", throwable.getClass()+ " " +throwable.getMessage());
//    }

    private class RequestLog {
        private String url;
        private String ip;
        private String type;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String type,String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.type = type;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", type='" + type + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

    }

}

package com.slzh.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ParamsInspectionAspect {

    @Pointcut("execution(public * com.slzh.web.controller.*.*(..))")
    public void controllerPoint() {
    }

    @Before("controllerPoint()")
    public void check(JoinPoint joinPoint) {
        // 获取controller参数列表
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            for (Object obj : args) {
                if (obj instanceof BindingResult) {
                    BindingResult errors = (BindingResult) obj;
                    if(errors.hasErrors()) {
                        errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
                    }
                }
            }
        }

    }
}

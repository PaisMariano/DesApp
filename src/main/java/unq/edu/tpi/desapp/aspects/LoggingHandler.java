package unq.edu.tpi.desapp.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Component
@Order(0)
public class LoggingHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /// CUSTOM  POINTCUT////
    @Pointcut("execution(* unq.edu.tpi.desapp.webservices.*.*(..))")
    public void methodsStarterServicePointcut() {
    }

    @Before("methodsStarterServicePointcut()")
    public void beforeMethods(JoinPoint joinPoint) throws Throwable {
        logger.info("CLASS NAME : " + joinPoint.getSignature().getDeclaringTypeName() +
                " - METHOD : " + joinPoint.getSignature().getName() +
                " - ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    //@After("methodsStarterServicePointcut()")
}

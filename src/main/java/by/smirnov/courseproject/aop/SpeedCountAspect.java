package by.smirnov.courseproject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Component
@Aspect
public class SpeedCountAspect {
    @Pointcut("execution(* by.smirnov.courseproject.repository.guitar.JdbcTmpltGuitarRepo.*(..))")
    public void aroundGuitarRepoPointcut() {
    }

    @Around("aroundGuitarRepoPointcut()")
    public Object guitarMethodSpeedController(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println(joinPoint.toShortString());

        StopWatch sw = new StopWatch();
        sw.start();

        Object proceed = joinPoint.proceed();

        sw.stop();
        System.out.println(sw.prettyPrint());

        return proceed;
    }

    @Pointcut("execution(* by.smirnov.courseproject.repository.user.JdbcTemplUserRepository.*(..))")
    public void aroundUserRepoPointcut() {
    }

    @Around("aroundUserRepoPointcut()")
    public Object userMethodSpeedController(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        System.out.println("Время выполнения метода = " + joinPoint.toShortString() + " " + (endTime - startTime) + " ms.");

        return proceed;
    }
}

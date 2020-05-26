package org.wy.sso.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Aspect
@Component(value = "LogUtil")
public class LogUtil {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public void info(String str) {
        logger.info(str);
    }

    public void warn(String str) {
        logger.warn(str);
    }

    public void debug(String str) {
        logger.debug(str);
    }

    public void error(String str) {
        logger.error(str);
    }


    // 匹配操作数据库的接口
    @Pointcut("execution(* org.wy.sso.dao.PreparedStatementFacade.*(..))")
    private void psfAPIPoint() {
    }

    @Around("psfAPIPoint()")
    public Object aroundWork(ProceedingJoinPoint pjp) {
        Object ret = null;
        Object[] args = pjp.getArgs();  // 获取切入点方法参数
        logger.info("PreparedStatementFacade开始调用");
        try {
            ret = pjp.proceed(args);    // 执行切入点方法
            logger.info("PreparedStatementFacade完毕");
        } catch (Throwable throwable) {
            logger.error("PreparedStatementFacade调用异常：" + throwable.toString());
            throwable.printStackTrace();
        } finally {
            logger.info("PreparedStatementFacade调用最终");
        }
        return ret;
    }
}

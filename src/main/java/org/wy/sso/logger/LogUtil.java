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
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        String logInfo = integrateLogInfo(stack, 1, str);
        logger.info(logInfo);
    }

    public void warn(String str) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        String logInfo = integrateLogInfo(stack, 1, str);
        logger.warn(logInfo);
    }

    public void debug(String str) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        String logInfo = integrateLogInfo(stack, 1, str);
        logger.debug(logInfo);
    }

    public void error(String str) {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        String logInfo = integrateLogInfo(stack, 1, str);
        logger.error(logInfo);
    }

    /**
     * 整合日志信息
     *
     * @param stack      函数的调用栈桢
     * @param stackLevel 栈桢层数
     * @param logInfo    日志信息
     * @return 返回一个指定栈桢层数的函数名以及行号拼接的日志信息
     */
    private String integrateLogInfo(StackTraceElement stack[], int stackLevel, String logInfo) {
        String ClassName = stack[stackLevel].getClassName();
        String MethodName = stack[stackLevel].getMethodName();
        String FileName = stack[stackLevel].getFileName();
        int LineNumber = stack[stackLevel].getLineNumber();
        return ClassName + "." + MethodName + "(" + FileName + ":" + LineNumber + ") - " + logInfo;
    }


    // 匹配操作数据库的接口
    @Pointcut("execution(* org.wy.sso.dao.PreparedStatementFacade.*(..))")
    private void psfAPIPoint() {
    }

    @Around("psfAPIPoint()")
    public Object aroundWork(ProceedingJoinPoint pjp) {
        Object ret = null;
        Object[] args = pjp.getArgs();  // 获取切入点方法参数
        try {
            ret = pjp.proceed(args);    // 执行切入点方法
        } catch (Throwable throwable) {
            errorAOP("数据库操作失败，请检查参数是否合格：" + throwable.toString());
            throwable.printStackTrace();
        } finally {
        }
        return ret;
    }

    /**
     * 专门为AOP重载一个error日志信息接口，用于追溯AOP切点的上一个函数栈桢
     * @param str
     */
    public void errorAOP(String str) {
        String logInfo = null;
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        for(int i = 0; i < stack.length; ++i){
            String ClassName = stack[i].getClassName();
            if(ClassName.contains("PreparedStatementFacade")){
                String MethodName = stack[i+1].getMethodName();
                String FileName = stack[i+1].getFileName();
                int LineNumber = stack[i+1].getLineNumber();
                ClassName = stack[i+1].getClassName();
                logInfo = ClassName + "." + MethodName + "(" + FileName + ":" + LineNumber + ") - " + str;
                break;
            }
        }
        logger.error(logInfo);
    }
}

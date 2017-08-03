package com.jmb.springfactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;

@Aspect
public class LoggerAop {
  
  private static final String LOG_PARAM = "with args: %s";
  private static final String LOG_EXECUTING_METHOD = "Executing method %s";
  private static final String LOG_FINALIZING_METHOD = "Finalizing method %s";
  private Logger monitorAopLog = Logger.getLogger(getClass());

  @Pointcut("execution(* com.jmb.springfactory.*.*(..))")
  public void allMethods() {}
  
  @Before("allMethods()")
  public void logBeforeMethodEntries(JoinPoint jointPoint) {
    this.logMethod(jointPoint, LOG_EXECUTING_METHOD);
  }
  
  @After("allMethods()")
  public void logAfterMethodEntries(JoinPoint jointPoint) {
    this.logMethod(jointPoint, LOG_FINALIZING_METHOD);
  }
  
  private void logMethod(JoinPoint joinPoint, String logMethod) {
    final String methodName = joinPoint.getSignature().getName(); 
    final Object[] params = joinPoint.getArgs();
    
    monitorAopLog.debug(String.format(logMethod, methodName));
    for(Object param : params) {
      monitorAopLog.debug(String.format(LOG_PARAM, param.toString()));
    }
  }
}

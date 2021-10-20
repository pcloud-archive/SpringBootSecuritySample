package org.pcloud.jwtsecurity.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class LoggerAspect {

    @Pointcut("execution(* org.pcloud.jwtsecurity..*Api.*(..))")
    public void loggerPointCut() {

    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result = proceedingJoinPoint.proceed();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); // request 정보를 가져온다.

            Object caller = request.getAttribute("caller");



            String apiName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            log.info("caller: {}  api: {}  method: {}", caller, apiName, methodName);
            Map<String, Object> params = new HashMap<>();

            try {
                params.put("api", apiName);
                params.put("method", methodName);
                params.put("params", request);
                params.put("log_time", new Date());
                params.put("request_uri", request.getRequestURI());
                params.put("http_method", request.getMethod());
            } catch (Exception e) {
                log.error("LoggerAspect error", e);
            }
            log.info("params : {}", params); // param에 담긴 정보들을 한번에 로깅한다.

            return result;
        }catch(Throwable e) {
            throw e;
        }
    }

//    private static JSONObject getParams(HttpServletRequest request) {
//        JSONObject jsonObject = new JSONObject();
//        Enumeration<String> params = request.getParameterNames();
//        while (params.hasMoreElements()) {
//            String param = params.nextElement();
//            String replaceParam = param.replaceAll("\\.", "-");
//            jsonObject.put(replaceParam, request.getParameter(param));
//        }
//        return jsonObject;
//    }
}

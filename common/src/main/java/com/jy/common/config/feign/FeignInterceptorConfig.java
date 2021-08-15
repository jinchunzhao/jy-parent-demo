//package com.jy.common.config.feign;
//
//import java.util.Enumeration;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import feign.RequestInterceptor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class FeignInterceptorConfig {
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return template -> {
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//                    .getRequestAttributes();
//            if (attributes != null) {
//                HttpServletRequest request = attributes.getRequest();
//                Enumeration<String> headerNames = request.getHeaderNames();
//                if (headerNames != null) {
//                    JSONObject jsonObject = new JSONObject();
//                    while (headerNames.hasMoreElements()) {
//                        String headerName = headerNames.nextElement();
//                        String headerValue = request.getHeader(headerName);
//                        jsonObject.put(headerName, headerValue);
//                        template.header(headerName, headerValue);
//                    }
//                    log.info("Feign调用转发的消息头：{}", JSONUtil.toJsonStr(jsonObject));
//                }
//            }
//        };
//    }
//}

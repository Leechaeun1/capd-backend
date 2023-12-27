/*
package com.diary.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/user/upload")
public class OptionsFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        if(response instanceof HttpServletResponse){
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            //Cors관련 헤더추가
            httpResponse.addHeader("Access-Control-Allow-Origin", "*"); // 클라이언트 도메인 설정
            httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.addHeader("Access-Control-Allow-Headers", "*");
            httpResponse.addHeader("Access-Control-Allow-Credentials", "true"); // 필요한 경우에만 추가
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
*/

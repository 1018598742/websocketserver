package com.fta.websocketserver.filter;

import com.fta.websocketserver.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 过滤器
 */
@WebFilter(urlPatterns = "/*")
public class ControllerFileter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(ControllerFileter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("初始化过滤器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String uri = httpServletRequest.getRequestURI();
        logger.info("uri={}",uri);
//        filterChain.doFilter(new TokenRequestWrapper((HttpServletRequest) servletRequest), servletResponse);

        Map<String,Object> paramter = new HashMap<>(16);

        //这里获取请求头信息，并填入参数表
        String userId = httpServletRequest.getHeader("sid");
        paramter.put("userId", userId);
        UserRequestWrapper wrapper = new UserRequestWrapper(httpServletRequest, paramter);
        filterChain.doFilter(wrapper, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("销毁过滤器");
    }
}

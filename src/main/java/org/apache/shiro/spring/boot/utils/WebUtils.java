package org.apache.shiro.spring.boot.utils;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Local WebUtils replacement for Shiro compatibility.
 * Uses javax.servlet to match Shiro's API.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
public final class WebUtils {

    private WebUtils() {
    }

    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    public static void issueRedirect(ServletRequest request, ServletResponse response, String url) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(url);
    }

    public static String getRemoteAddr(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String addr = httpRequest.getHeader("X-Forwarded-For");
        if (addr == null || addr.isEmpty()) {
            addr = httpRequest.getHeader("X-Real-IP");
        }
        if (addr == null || addr.isEmpty()) {
            addr = httpRequest.getRemoteAddr();
        }
        return addr;
    }

}

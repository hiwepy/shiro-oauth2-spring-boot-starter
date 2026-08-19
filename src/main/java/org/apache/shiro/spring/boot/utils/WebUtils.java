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
 * @since 1.0.0
 */
public final class WebUtils {

    private WebUtils() {
    }

    /**
     * to HTTP.
     *
     * @param request the request
     * @return the result
     */
    public static HttpServletRequest toHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    /**
     * to HTTP.
     *
     * @param response the response
     * @return the result
     */
    public static HttpServletResponse toHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

    /**
     * issue Redirect.
     *
     * @param request the request
     * @param response the response
     * @param url the url
     * @throws IOException if an error occurs
     */
    public static void issueRedirect(ServletRequest request, ServletResponse response, String url) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendRedirect(url);
    }

    /**
     * get Remote Addr.
     *
     * @param request the request
     * @return the result
     */
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

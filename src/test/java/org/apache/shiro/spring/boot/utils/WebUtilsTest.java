package org.apache.shiro.spring.boot.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

class WebUtilsTest {
    @Test
    void toHttpShouldCastServletRequest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        assertThat(WebUtils.toHttp(request)).isSameAs(request);
    }
    @Test
    void toHttpShouldCastServletResponse() {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        assertThat(WebUtils.toHttp(response)).isSameAs(response);
    }
    @Test
    void getRemoteAddrShouldUseXForwardedFor() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Forwarded-For")).thenReturn("1.2.3.4");
        assertThat(WebUtils.getRemoteAddr(request)).isEqualTo("1.2.3.4");
    }
    @Test
    void getRemoteAddrShouldFallbackToXRealIP() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        Mockito.when(request.getHeader("X-Real-IP")).thenReturn("5.6.7.8");
        assertThat(WebUtils.getRemoteAddr(request)).isEqualTo("5.6.7.8");
    }
    @Test
    void getRemoteAddrShouldFallbackToRemoteAddr() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        Mockito.when(request.getHeader("X-Real-IP")).thenReturn(null);
        Mockito.when(request.getRemoteAddr()).thenReturn("9.10.11.12");
        assertThat(WebUtils.getRemoteAddr(request)).isEqualTo("9.10.11.12");
    }
    @Test
    void issueRedirectShouldSendRedirect() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        WebUtils.issueRedirect(request, response, "http://example.com");
        Mockito.verify(response).sendRedirect("http://example.com");
    }
    @Test
    void getRemoteAddrShouldHandleEmptyXForwardedFor() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Forwarded-For")).thenReturn("");
        Mockito.when(request.getHeader("X-Real-IP")).thenReturn(null);
        Mockito.when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        assertThat(WebUtils.getRemoteAddr(request)).isEqualTo("127.0.0.1");
    }
}

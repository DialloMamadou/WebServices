package fr.univ.orleans.wsi.wsmotus.Filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    private static final String SERVER_TOKEN = "http://localhost:8000/token";
    private static final String TOKEN_LOG = SERVER_TOKEN+"/login";
    private static final String TOKEN_CHK = SERVER_TOKEN+"/checkToken";
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN = "token";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOCATION = "location";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {

        String token = httpServletRequest.getHeader("token");
        if(token != null && token.length()>0) {
            System.out.println("ok " + token);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.put(AUTHORIZATION, Arrays.asList(token));
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

            ResponseEntity<String> resultat = null;
            try {
                resultat = restTemplate.exchange(TOKEN_CHK, HttpMethod.GET, httpEntity, String.class);
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                httpServletResponse.setHeader(LOCATION,TOKEN_LOG);
                return false;
            }
            return true;
        }else{
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            httpServletResponse.setHeader(LOCATION,TOKEN_LOG);
        }
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

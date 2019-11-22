package fr.miage.orleans.applisondages.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import services.Constantes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    private static final String SERTOK="http://localhost:8000";
    private static final String TOKLOG=SERTOK+"/login";
    private static final String TOKCHK=SERTOK+"/checkToken";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //test présence token
        String token = httpServletRequest.getHeader(Constantes.TOKEN);
        if(token != null && token.length()>0) {
            RestTemplate restTemplate1 = new RestTemplate();
            HttpHeaders httpHeaders2 = new HttpHeaders();
            httpHeaders2.put(Constantes.AUTHORIZATION, Arrays.asList(token));
            // body
            MultiValueMap<String, String> map2 = new LinkedMultiValueMap<String, String>();
            // headers + body
            HttpEntity<MultiValueMap<String, String>> httpEntity2 = new HttpEntity<MultiValueMap<String, String>>(map2, httpHeaders2);
            //REST call for location
            ResponseEntity<String> resultat2 = null;
            try {
                resultat2 = restTemplate1.exchange(TOKCHK, HttpMethod.GET, httpEntity2, String.class);
            } catch (HttpClientErrorException e) {
                //token absent ou invalide
                e.printStackTrace();
                //reponse statut NOT ACCEPTABLE
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                //indiquer URI serveur token
                httpServletResponse.setHeader(Constantes.LOCATION,TOKLOG);
                return false;
            }
            return true;
        }else{
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            //indiquer URI serveur token
            httpServletResponse.setHeader(Constantes.LOCATION,TOKLOG);
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

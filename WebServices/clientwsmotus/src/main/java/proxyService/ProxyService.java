package proxyService;

import modele.Exceptions.NoServerFoundException;
import org.omg.CORBA.UnknownUserException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class ProxyService {


    private final static String pseudo ="mcd";
    private final static String url ="/http://localhost:8080.motus";
    public static void goToconnexion(String login) throws NoServerFoundException, UnknownUserException {
        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
// body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.put("login", Arrays.asList(login));
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map , httpHeaders);
// Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login

        try {
            ResponseEntity<String> resultat = restTemplate.postForEntity(url, httpEntity, String.class);
            String contenu = resultat.getBody();

        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                throw new NoServerFoundException();
            }
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                throw new UnknownUserException();
            }
        }
    }

    /*public static String  goToMenu(){

    }*/
}

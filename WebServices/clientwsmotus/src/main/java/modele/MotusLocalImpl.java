package modele;

import com.fasterxml.jackson.databind.ObjectMapper;
import modele.Exceptions.NonConnectExcption;
import modele.Exceptions.PseudoPris;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotusLocalImpl implements MotusLocal {
    private static final String URI_SERVICE = "/http://localhost:8080/motus";
    private static final String JOUEUR = "/joueur";
    private static final String PARTIE = "/partie";
    private static final String DICTIONNAIRES = "/dictionnaires";

    private String pseudo;



    @Override
    public void connexion1(String pseudo) throws PseudoPris{
        RestTemplate restTemplate = new RestTemplate();
        Login login = new Login();
        login.setPseudo(pseudo);
        HttpEntity<Login> httpEntity = new HttpEntity<>(login);
        String url = URI_SERVICE+JOUEUR;//+"/"+pseudo;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            this.pseudo = pseudo;

        }catch (HttpStatusCodeException e ){
            if (e.getStatusCode() == HttpStatus.CONFLICT){
                String responseString = e.getResponseBodyAsString();
                System.out.println("responseString="+responseString);
                Pattern pattern = Pattern.compile("\"message\":\"([^\"]*)\"");
                Matcher matcher = pattern.matcher(e.getResponseBodyAsString());
                String message;
                if(matcher.find()){
                    message =matcher.group();
                }else {
                    message="Raison non identifiée";
                }
                throw  new PseudoPris(message);
            }

            throw new UnknownError();

        }
    }
@Override
    public void connexion(String pseudo) throws PseudoPris{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requete = "pseudo="+pseudo;
        HttpEntity<String> httpEntity= new HttpEntity<>(requete,httpHeaders);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(URI_SERVICE+JOUEUR, HttpMethod.POST,httpEntity,String.class);

        }catch (HttpStatusCodeException e ){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                throw  new PseudoPris("Pseudo déja pris, merci de bien vouloir changer de pseudo ");
            }
            else {
                throw new UnknownError();
            }
        }
    }

    @Override
    public void deconnexion(String psudo) throws NonConnectExcption {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        String url = URI_SERVICE+JOUEUR+"/"+pseudo;
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE,httpEntity,String.class);

        }catch (HttpStatusCodeException e ){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                throw  new NonConnectExcption();
            }
            else {
                throw new UnknownError();
            }
        }
    }

    @Override
    public void nouvellePartie(String psudo, String dicoName)  throws NonConnectExcption{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requet = "dico"+dicoName;
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_SERVICE+PARTIE+"/"+pseudo,httpHeaders,String.class);

        }catch (HttpStatusCodeException e ){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                throw  new NonConnectExcption();
            }
            else {
                System.err.println(e);
                throw new UnknownError();
            }
        }
    }

    @Override
    public void jouer(String pseudo) throws NonConnectExcption {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requet = "partie"+PARTIE;
        HttpEntity<String> httpEntity = new HttpEntity<>(requet,httpHeaders);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_SERVICE+PARTIE+"/"+pseudo,httpHeaders,String.class);

        }catch (HttpStatusCodeException e ){
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                throw  new NonConnectExcption();
            }
            else {
                System.err.println(e);
                throw new UnknownError();
            }
        }
    }

    @Override
    public Collection<String> getListeDicos() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String > httpEntity = new HttpEntity<>("");
        ResponseEntity<String> responseEntity = restTemplate.exchange(URI_SERVICE+DICTIONNAIRES,HttpMethod.GET,httpEntity,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Collection<String> liste = objectMapper.readValue(
                    responseEntity.getBody(),objectMapper.getTypeFactory().constructCollectionType(Collection.class, String.class));
            return liste;

        }catch (IOException e){

                throw new UnknownError();

        }
    }


    @Override
    public List<String> getTentative(String pseudo) throws NonConnectExcption {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String > httpEntity = new HttpEntity<>("");
        ResponseEntity<String> responseEntity = restTemplate.exchange(URI_SERVICE+PARTIE+"/"+pseudo,HttpMethod.GET,httpEntity,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            List<String> liste = objectMapper.readValue(
                    responseEntity.getBody(),objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
            return liste;

        }catch (IOException e){
            e.printStackTrace();
            throw new UnknownError();

        }
    }


}

package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import service.exeption.MotInexistantException;
import service.exeption.NbEssaisMaxAtteintException;
import service.exeption.PseudoDejaPrisException;
import service.exeption.PseudoNonConnecteException;

import java.io.IOException;
import java.util.List;

public class MotusService {

    /*RestTemplate restTemplate;

    public MotusService(){
        this.restTemplate = new RestTemplate();
    }*/
    public static String login(String url, String login) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("pseudo", login);
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login

        try {
            ResponseEntity<String> resultat = restTemplate.postForEntity(url, httpEntity, String.class);
            return String.valueOf(resultat);
            //return "Connexion Reussi";
        }
        catch (HttpClientErrorException e) {
           if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()){
               throw new PseudoDejaPrisException("Pseudo déjà pris");
           }
        }
        catch (ResourceAccessException e){
            throw new Exception("Erreur de connexion au serveur");
        }
        return null;
    }

    public static String deconnexion(String url, String login){
        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("pseudo", login);
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login

        try {
            ResponseEntity<String> resultat = restTemplate.exchange(url, HttpMethod.DELETE ,httpEntity, String.class);
            return "Decoonexion Reussi";
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()){
                return "Pseudo non connecté";
            }
        }
        catch (ResourceAccessException e){
            return "Erreur de connexion au serveur";
        }
        return null;
    }

    public static List<String> listeDesDicos(String url){
        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        // body
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(null, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login
        ResponseEntity<String>  resultat = null;
        try {
            resultat = restTemplate.exchange(url, HttpMethod.GET ,httpEntity, String.class);
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()){
                e.printStackTrace();
            }
            return null;
        }
        catch (ResourceAccessException e){
            System.out.println("Erreur de connexion au serveur");
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String > liste = objectMapper.readValue(resultat.getBody(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class,String.class));
            return liste;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> listeDesEssais(String url, String pseudo) throws Exception {
        System.out.println("pseudo="+pseudo+ " "+url);
        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("pseudo", pseudo);
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login
        ResponseEntity<String>  resultat=null;
        // body
        // headers + body
        /*HttpEntity<MultiValueMap<String,String>> httpEntity =
                new HttpEntity<MultiValueMap<String,String>>(null, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login
        ResponseEntity<String>  resultat = null;*/


        try {
            resultat = restTemplate.exchange(url, HttpMethod.GET ,httpEntity, String.class);
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.METHOD_NOT_ALLOWED.value())
                throw new Exception(e.getMessage()+"\nErreur!!! Connecter-Vous"); //Pas très précis
        }
        catch (HttpServerErrorException e){
            if (e.getStatusCode().value() == HttpStatus.INTERNAL_SERVER_ERROR.value())
                throw new Exception("Erreur!!! Creer une partie et Jouer au moins une fois");
        }
        catch (ResourceAccessException e){
            throw new Exception("Erreur de connexion au serveur");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String > liste = objectMapper.readValue(resultat.getBody(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class,String.class));
            return liste;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String nouvellePartie(String url, String pseudo, String dicoName){
        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("pseudo", pseudo);
        map.add("dicoName", dicoName);
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login
        ResponseEntity<String>  resultat=null;
        try {
            resultat = restTemplate.postForEntity(url, httpEntity, String.class);
            return "Partie initialisée";
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()){
                return "Pseudo non connecté";
            }
            return e.getMessage(); //"probleme";
        }
        catch (HttpServerErrorException e){
            if (e.getStatusCode().value() == HttpStatus.INTERNAL_SERVER_ERROR.value())
                return "Erreur Dico invalide";
            return null;
        }
        catch (ResourceAccessException e){
            return "Erreur de connexion au serveur";
        }

    }

    public static String jouer(String url, String pseudo, String mot) throws NbEssaisMaxAtteintException,
            MotInexistantException, PseudoNonConnecteException {

        RestTemplate restTemplate = new RestTemplate();
        // headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // body
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("pseudo", pseudo);
        map.add("mot", mot);
        // headers + body
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(map, httpHeaders);
        // Appel rest pour récupérer un objet String si tout va bien. La requête Post nécessite ici un paramềtre login

        try {
            ResponseEntity<String>  resultat = restTemplate.exchange(url,HttpMethod.PUT, httpEntity, String.class);
            return resultat.getBody();
        }
        catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()){
                throw new PseudoNonConnecteException("Pseudo non connecté");
            }
            if (e.getStatusCode().value() == HttpStatus.CONFLICT.value()){
                throw new NbEssaisMaxAtteintException("Nombre max d'essais atteint");
            }
            if (e.getStatusCode().value() == HttpStatus.NOT_FOUND.value()){
                throw new MotInexistantException("Le mot n'existe pas dans le dictionnaire");
            }
            return null;
        }
        catch (ResourceAccessException e){
            return "Erreur de connexion au serveur";
        }
    }

}

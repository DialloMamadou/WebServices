package fr.univ.orleans.wsi.wsmotus.Controllers;

import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import facade.FacadeMotus;
import facade.FacadeMotusStatic;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/motus")
public class MotusController {

    private final static FacadeMotus facadeMotus = new FacadeMotusStatic();

    @RequestMapping(value = "/joueur", method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<String> connexion(@RequestParam String pseudo){
        try{
            facadeMotus.connexion(pseudo);
            return ResponseEntity.created(new URI("/motus/partie/")).body("Connexion reussie");

        }catch (PseudoDejaPrisException | URISyntaxException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pseudo déjà pris ");
        }
    }

    @RequestMapping(value = "/partie", method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> creationPartie(@RequestParam String pseudo, @RequestParam String dicoName){
        try {
            facadeMotus.nouvellePartie(pseudo, dicoName);
            return ResponseEntity.created(new URI("/motus/partie")).body("Partie bien créée");//build();
        } catch (PseudoNonConnecteException | URISyntaxException e) {
            e.fillInStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problème de de commencer une nouvelle partie !");
        }
    }

    @RequestMapping(value = "/partie", method = RequestMethod.PUT,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> jouer(@RequestParam String pseudo, @RequestParam String mot) {
        try {
            String res = facadeMotus.jouer(pseudo, mot);
            return ResponseEntity.ok().body(res);
            //return new ResponseEntity(res,new HttpHeaders(), HttpStatus.CREATED);
        } catch (MotInexistantException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le mot n'existe pas dans le dico !");
        }catch (MaxNbCoupsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombe de coût atteint !");
        }catch (PseudoNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problème de de commencer une nouvelle partie !");
        }

    }

    @RequestMapping(value = "/listDico", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Collection<String>> getListeDico() {
            Collection<String> listeDico = facadeMotus.getListeDicos();
             return ResponseEntity.ok(listeDico);
    }


    @RequestMapping(value = "/listEssai", method = RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<String>> getListeEssai(@RequestParam String pseudo){
        List<String> listeEssai = null;

        try {
            listeEssai = facadeMotus.getPartie(pseudo).getEssais();
            return ResponseEntity.ok(listeEssai);
        } catch (PseudoNonConnecteException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//pseudo joueur1  dicoName  dicosimple7lettres mot deviner pseudo joueur1
    //A guide to the 2.1. Resrivin
    @RequestMapping(value = "/joueur", method = RequestMethod.DELETE)
    public ResponseEntity<String> deconnexion(@RequestParam String pseudo){
        try{
            facadeMotus.deconnexion(pseudo);
            return new ResponseEntity("Déconnexion réussie !",new HttpHeaders(), HttpStatus.CREATED);
        } catch (PseudoNonConnecteException e) {
            //return new ResponseEntity("Pseudo erroné",new HttpHeaders(), HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problème de déconnexion !");
        }
    }
}

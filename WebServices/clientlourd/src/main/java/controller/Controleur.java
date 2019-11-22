package controller;

import service.MotusService;
import service.exeption.MotInexistantException;
import service.exeption.NbEssaisMaxAtteintException;
import service.exeption.PseudoDejaPrisException;
import service.exeption.PseudoNonConnecteException;
import view.*;

import java.util.List;

public class Controleur {
    Menu menu;
    Connexion connexion;
    Deconnexion deconnexion;
    NouvellePartie nouvellePartie;
    Jouer jouer;
    ListeEssai listeEssai;
    private String login = " ";
    private static final String url = "http://localhost:8080/motus/";

    public Controleur(){
        menu = new Menu(this);
        connexion = new Connexion(this);
        deconnexion = new Deconnexion(this);
        nouvellePartie = new NouvellePartie(this);
        jouer = new Jouer(this);
        listeEssai = new ListeEssai(this);
        menu.afficher();
    }

    public void goToMenu() {
        menu.afficher();
    }

    public void goToConnexion(){
        connexion.afficher();
    }

    public void goToDeconnexion(){
        deconnexion.afficher();
    }

    public void goToCreerPartie(){
        nouvellePartie.afficher();
    }

    public void goToJouer(){
        jouer.afficher();
    }

    public void goToListeEssai(){
        listeEssai.afficher();
    }

    public void seConnecter(String login) {
        String resultat = null;
        try {
            resultat = MotusService.login(url +"joueur", login);
            if (resultat != null){
                connexion.message(resultat);
                this.login = login;
            }
        } catch (PseudoDejaPrisException e) {
            connexion.message(e.getMessage());
        } catch (Exception e) {
            connexion.message(e.getMessage());
        }
        goToMenu();
    }

    public List<String> listeDico() {
        List<String> resultat = MotusService.listeDesDicos(url +"listDico");
        if (resultat != null){
            return resultat;
        }

        return null;
    }

    public void seDeconnecter() {
        String resultat = MotusService.deconnexion(url +"joueur", login);
        if (resultat != null){
            Deconnexion.message(resultat);
            this.login = "";
        }
        goToMenu();
    }

    public void nouvellePartie(String dicoName){
        String resultat = MotusService.nouvellePartie(url +"partie", login, dicoName);
        if (resultat != null){
            nouvellePartie.message(resultat);
        }
        goToJouer();
    }

    public void jouer(String mot) {
        String resultat = "";
        String reussi = "XXXXXXX";
        try {
            resultat = MotusService.jouer(url +"partie", login, mot);
            System.out.println(resultat);
            System.out.println(url +"partie");
            if (resultat != null){
                jouer.message(resultat);
                if (resultat.equals(reussi)){
                    jouer.message("Partie reussie le bon mot était "+mot);
                    goToMenu();
                }
            }
            goToJouer(); //On commence à joueur si on init une partie
        } catch (NbEssaisMaxAtteintException e) {
            jouer.message("Nombre max d'essai atteint");
            goToMenu();
        } catch (MotInexistantException e) {
            jouer.message(e.getMessage());
            goToJouer();
        } catch (PseudoNonConnecteException e) {
            jouer.message(e.getMessage());
            goToMenu();
        }
    }

    public List<String> listeEssai() {
        List<String> resultat = null;
        try {
            resultat = MotusService.listeDesEssais(url +"listEssai", login);
            System.out.print("essai");

            if (resultat != null){
                System.out.println("list non vide");
                return resultat;
            }
        } catch (Exception e) {
            System.out.println("list vide");
            listeEssai.message(e.getMessage());
        }
        return null;
    }
}
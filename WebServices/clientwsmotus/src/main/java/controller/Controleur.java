package controller;

import modele.MotusLocalImpl;
import views.*;

public class Controleur {
    private MotusLocalImpl motusLocalImpl;
    private String pseudo;
    private String url;
    private Menu menu;
    private Connexion connexion;
    private Deconnexion deconnexion;
    private JouerPartie jouerPartie;
    private NouvellePartie nouvellePartie;

    public Controleur() {
        menu = new Menu(this);
        connexion = new Connexion(this);
        deconnexion = new Deconnexion(this);
        nouvellePartie = new NouvellePartie(this);
        jouerPartie = new JouerPartie(this);
        goToConnexion();

    }
    public void goToMenu(){
        menu.afficher();
    }
    public void goToConnexion(){
        connexion.afficher();
    }
    public void goToNouvellePartie(){
        nouvellePartie.afficher();
    }
    public void goToJouerPartie(){
        jouerPartie.afficher();
    }
    public void goToDeconnexion(){
        deconnexion.afficher();
    }

}

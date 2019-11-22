package views;

import controller.Controleur;

import java.util.Scanner;

public class Connexion implements View{
    private String pseudo;
    private Controleur controleur;

    public Connexion(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void afficher() {
        System.out.println("#########################################");
        System.out.println(" (1) Connexin");
        System.out.println(" (2) Creer une nouvelle part");
        System.out.println(" (3) Choisir un dictionnaire");
        System.out.println(" (4) Jouer la partie");
        System.out.println(" (4) Deconnexion");
        System.out.println(" Saisir 1, 2, 3 ou 4");
        System.out.println("#########################################");
        //Scanner scanner = new Scanner(System.in);

    }
}



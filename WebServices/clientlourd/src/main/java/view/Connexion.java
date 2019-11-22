package view;

import controller.Controleur;

import java.util.Scanner;

public class Connexion implements View {
    Controleur controleur;

    public Connexion(Controleur c) {
        this.controleur = c;
    }

    public void afficher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------");
        System.out.println(" login ?");
        String login = scanner.next();
        controleur.seConnecter(login);
    }

    public void message(String s) {
        System.out.println("---------------------------------------------");
        System.out.println(s);
        System.out.println("---------------------------------------------");
    }
}

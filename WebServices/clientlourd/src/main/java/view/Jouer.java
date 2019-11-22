package view;

import controller.Controleur;

import java.util.Scanner;

public class Jouer implements View {
    private Controleur controleur;

    public Jouer(Controleur c){
        this.controleur = c;
    }
    public void afficher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------");
        System.out.println(" Déviner le mot ou Tapez 0 revenir au menu");
        String mot = scanner.next();
        if (mot.equals("0"))
            controleur.goToMenu();
        else
            controleur.jouer(mot);
    }

    public void message(String s) {
        System.out.println("---------------------------------------------");
        System.out.println(s);
        System.out.println("---------------------------------------------");
    }
}

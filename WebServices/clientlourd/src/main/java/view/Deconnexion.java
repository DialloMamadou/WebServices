package view;

import controller.Controleur;

import java.util.Scanner;

public class Deconnexion implements View {

    Controleur controleur;

    public Deconnexion(Controleur c){
        this.controleur = c;
    }

    public static void message(String s) {
        System.out.println("---------------------------------------------");
        System.out.println(s);
        System.out.println("---------------------------------------------");
    }

    public void afficher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------");
        System.out.println(" Deconnexion en cours ...");
        controleur.seDeconnecter();
    }
}

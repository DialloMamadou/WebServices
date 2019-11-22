package view;

import controller.Controleur;

import java.util.List;
import java.util.Scanner;

public class NouvellePartie implements View {
    Controleur controleur;

    public NouvellePartie(Controleur c){
        this.controleur = c;
    }


    public void afficher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------");
        System.out.println(" Choisir un dico (1 ou 2) ?");
        List<String> dicos = controleur.listeDico();
        int i=1;
        for (String d : dicos){
            System.out.println("("+i+") "+d);
            i++;
        }
        int choix;
        do {
            choix = scanner.nextInt();

        }
        while (choix <1 || choix >2);
        controleur.nouvellePartie(dicos.get(choix-1));
    }

    public void message(String s) {
        System.out.println("---------------------------------------------");
        System.out.println(s);
        System.out.println("---------------------------------------------");
    }

}

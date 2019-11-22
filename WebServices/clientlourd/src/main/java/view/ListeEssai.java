package view;

import controller.Controleur;

import java.util.List;
import java.util.Scanner;

public class ListeEssai implements View {
    Controleur controleur;

    public ListeEssai(Controleur c){
        this.controleur = c;
    }

    public void afficher() {
        System.out.println("---------------------------------------------");
        System.out.println(" Liste des essais réaliséseee");
        List<String> listeEssai = controleur.listeEssai();
        /*for(String essai:listeEssai){
            System.out.print(essai);
        }*/
        System.out.println();

        controleur.goToMenu();
    }

    public void message(String s) {
        System.out.println("---------------------------------------------");
        System.out.println(s+"prob");
        System.out.println("---------------------------------------------");
    }
}

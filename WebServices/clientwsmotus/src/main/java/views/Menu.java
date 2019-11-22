package views;

import controller.Controleur;

import java.util.Scanner;

public class Menu implements View{
    private Controleur controleur;

    public Menu(Controleur controleur){
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

        Scanner scanner = new Scanner(System.in);
        /*int choix = -1;
        do {
            choix = scanner.nextInt();

        }
        while (choix < 1 || choix > 4);

        switch (choix) {
            case 1: {
                controleur.goToConnexion();
                break;
            }

            case 2: {
                controleur.goToNouvellePartie();
                break;
            }

            case 3: {
                controleur.goToJouerPartie();
                break;
            }

            case 4: {
                controleur.goToDeconnexion();
                break;
            }

        }
    }*/
    }

}

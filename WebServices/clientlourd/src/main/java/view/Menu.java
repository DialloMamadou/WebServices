package view;

import controller.Controleur;

import java.util.Scanner;

public class Menu implements View {
    private Controleur controleur;

    public Menu(Controleur controleur) {
        this.controleur = controleur;
    }

    public void afficher() {
        System.out.println("---------------------------------------------");
        System.out.println(" (1) Se connecter");
        System.out.println(" (2) Creer une partie");
        System.out.println(" (3) Jouer au motus");
        System.out.println(" (4) Voir la liste des essais");
        System.out.println(" (5) Deconnexion");
        System.out.println(" Saisir 1, 2, 3, 4 ou 5");
        System.out.println("---------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        int choix;
        do {
            choix = scanner.nextInt();

        }
        while (choix <1 || choix >5);

        switch (choix) {
            case 1: {
                controleur.goToConnexion();
                break;
            }

            case 2: {
                controleur.goToCreerPartie();
                break;
            }

            case 3: {
                controleur.goToJouer();
                break;
            }

            case 4: {
                controleur.goToListeEssai();
                break;
            }

            case 5: {
                controleur.goToDeconnexion();
                break;
            }

        }
    }
}

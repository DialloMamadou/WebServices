package views;

import controller.Controleur;

public class JouerPartie implements View{
    private Controleur controleur;

    public JouerPartie(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void afficher() {

        System.out.println("Jouer la partie");
    }
}

package views;

import controller.Controleur;

public class Deconnexion implements View{
    private Controleur controleur;

    public Deconnexion(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void afficher() {
        System.out.println("Deconnexion");
        controleur.goToMenu();
    }
}

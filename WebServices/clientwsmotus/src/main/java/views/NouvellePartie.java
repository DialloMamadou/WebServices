package views;

import controller.Controleur;

public class NouvellePartie implements View{
    private Controleur controleur;

    public NouvellePartie(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void afficher() {
        System.out.println(" (2) Creer une nouvelle part");
    }
}

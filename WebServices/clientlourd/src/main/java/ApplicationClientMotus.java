import controller.Controleur;
import service.MotusService;

public class ApplicationClientMotus {

    public static void main(String[] args) {
       //System.out.println(MotusService.login("http://localhost:8080/motus/joueur/", "alpha"));
       //System.out.println(MotusService.creerPartie("http://localhost:8080/motus/partie/", "alpha",
       //        "dicosimple7lettres"));
       //System.out.println(MotusService.jouer("http://localhost:8080/motus/jouer/", "alpha",
       //         "tomates"));

       // System.out.println(MotusService.listeDesEssais("http://localhost:8080/motus/partie/", "alpha"));

        Controleur controleur = new  Controleur();

    }
}
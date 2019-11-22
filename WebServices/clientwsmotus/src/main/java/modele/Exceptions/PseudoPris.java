package modele.Exceptions;

public class PseudoPris extends Exception {
    public PseudoPris(String message) {
        System.out.println(message);//="Pseudo déja pris, merci de bien vouloir changer de pseudo ";
    }
}

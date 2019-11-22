package modele;

import modele.Exceptions.NonConnectExcption;
import modele.Exceptions.PseudoPris;

import java.util.Collection;
import java.util.List;

public interface MotusLocal {
    public void connexion1(String pseudo)  throws PseudoPris, NonConnectExcption;
    public void connexion(String pseudo) throws PseudoPris, NonConnectExcption;
    public void deconnexion(String psudo) throws NonConnectExcption;
    public void nouvellePartie(String psudo, String dicoName) throws NonConnectExcption;
    public void jouer(String pseudo) throws NonConnectExcption;
    public Collection<String> getListeDicos();
    public List<String> getTentative(String pseudo) throws NonConnectExcption;


}

package service;

import exceptions.NomIntrouvableException;
import modele.Personne;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;
import java.util.HashMap;

@WebService(name = "AnnuaireWS")
public class Annuaire {
    private static HashMap<String, Personne> annuaire;
    static {
        annuaire = new HashMap<String,Personne>();
        annuaire.put("moal", new Personne("moal","frederic","0238000000"));
        annuaire.put("exbrayat", new Personne("exbrayat","matthieu","0238000000"));
    };

    @WebMethod(operationName = "SearchTeleByNon")
    public String searchTelephone(@WebParam(name = "nom") String nom) throws NomIntrouvableException{

        Personne p =  annuaire.get(nom);
        if (p==null) {
            throw new NomIntrouvableException();
        }
        return p.getTelephone();
    }

    @WebMethod(operationName = "AjoutPersonne")
    public void addPersonne(@WebParam(name = "personne") Personne p) {
        annuaire.put(p.getNom(), p);
    }

    @WebMethod(operationName = "AjoutPersonneListe")
    public void addPersonneList(@WebParam(name = "nom") String nom, @WebParam(name = "prenom") String prenom,
                                @WebParam(name = "telephone") String tel) {
        Personne p = new Personne(nom,prenom,tel);
        annuaire.put(p.getNom(), p);
    }
    @WebMethod(operationName = "ListeNoms")
    public Collection<String> getAllNom() {
        return annuaire.keySet();
    }
}

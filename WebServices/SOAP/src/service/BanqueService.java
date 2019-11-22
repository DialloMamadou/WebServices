package service;

import metier.Compte;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//Web service
//wsimport -Xnocompile http://localhost:8585/BanqueWS?wsdl
/*http://localhost:8585/BanqueWS?wsdl
    *wsdl:c4est un fichier xml qui permet de faire la desc de l'GUI du web service
    *il est genere par le serveur et il fourni aux clients a la demande
    *quand un client fait appel a un ws c'est la premiere des choses dont-il aura besoin
    *pour tester les methodes du ws on aura besoin d'un client
    * Voici un outil(SoapUI-5.4.0/bin/soapui.sh) permettant de les tester
*/
//le consommateur final n'a pas besoin de savoir ce qui est dans le wsdl
//Couche service qui devrai faire appel a la couche metier
@WebService(name = "BanqueWS")//Le web service qui sera apelle dans le navigateur
public class BanqueService {
    @WebMethod(operationName = "ConversionEuroToCFA")
    public double conversion(@WebParam(name = "montant")double mt){
        //Normalement fait par la couche metier
        return mt*655;
    }
    @WebMethod
    public Compte getCompte(@WebParam(name = "code") Long code){
        return new Compte(code,Math.random()*10000, new Date());
    }
    @WebMethod
    public List<Compte> getComptes(){
        List<Compte> cptes = new ArrayList<>();
        cptes.add(new Compte(1L,Math.random()*10000, new Date()));
        cptes.add(new Compte(2L,Math.random()*10000, new Date()));
        return cptes;
    }
}

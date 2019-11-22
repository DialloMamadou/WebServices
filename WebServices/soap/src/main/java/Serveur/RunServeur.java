package Serveur;

import service.Annuaire;

import javax.xml.ws.Endpoint;

public class RunServeur {
    public static void main(String[] arg){
        String url ="http://localhost:9191/annuaire";

        Endpoint endpoint  = Endpoint.publish(url, new Annuaire());
        System.out.println(url);
    }
}

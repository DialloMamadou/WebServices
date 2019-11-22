package metier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "compte")//Pour le message xml qu'on va genere represent un elt xml qui porte le non compte
@XmlAccessorType(XmlAccessType.FIELD)//Utilisation de  @XmlTransient sur les attributs sinon pas besoin
public class Compte  implements Serializable{
    private Long code;
    private  double solde;

    //Pour ne pas envoyer la date de creation aux clients
    @XmlTransient//A utiliser sur les methodes getters:
    private Date dateCreation;

    public Compte() {
    }

    public Compte(long code, double solde, Date dateCreation) {
        this.code = code;
        this.solde = solde;
        this.dateCreation = dateCreation;
    }

    public long getCode() {
        return code;
    }

    public double getSolde() {
        return solde;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}

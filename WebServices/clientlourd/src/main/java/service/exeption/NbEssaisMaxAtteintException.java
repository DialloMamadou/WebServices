package service.exeption;

public class NbEssaisMaxAtteintException extends Exception {
    private String s;

    public NbEssaisMaxAtteintException(String s1){
        super(s1);
    }

}

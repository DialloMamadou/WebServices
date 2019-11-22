package service.exeption;

public class PseudoNonConnecteException extends Exception {
    private String s;

    public PseudoNonConnecteException(String s1){
        super(s1);
    }

}

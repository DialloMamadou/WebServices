package service.exeption;

public class ErreurServeurException extends Exception {
    private String s;

    public ErreurServeurException(String s1){
        super(s1);
    }

}

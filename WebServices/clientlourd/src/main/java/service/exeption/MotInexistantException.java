package service.exeption;

public class MotInexistantException extends Exception {
    private String s;

    public MotInexistantException(String s1){
        super(s1);
    }

}

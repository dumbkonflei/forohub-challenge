package spring.forohub_challenge.infra.errors;

public class ValidarIntegridad extends RuntimeException{
    public ValidarIntegridad(String s){
        super(s);
    }
}

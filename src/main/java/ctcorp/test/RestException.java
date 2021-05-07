package ctcorp.test;


public class RestException extends Exception{

    private static final long serialVersionUID = 1L;

    public RestException(){
        super();
    }

    public RestException(String msg){
        super(msg);
    }

    public RestException(String msg, Exception e){
        super(msg, e);
    }
    
}

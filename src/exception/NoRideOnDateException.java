package exception;

public class NoRideOnDateException extends Exception {
    public NoRideOnDateException(String name){
        super(name);
    }
    public NoRideOnDateException(){
        super();
    }

}

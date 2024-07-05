package project.tracking_paket.exeption;

public class ElementExeption extends RuntimeException {
    private String message;
    
    public ElementExeption() {
        super();
    }
    
    public ElementExeption(final String message, final Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ElementExeption(final String message) {
        super(message);
        this.message = message;
    }

    public ElementExeption(final Throwable cause) {
        super(cause);
    }
    
}

package domain.exception;

/**
 * Base checked exception untuk seluruh kegagalan bisnis di layer domain.
 */
public class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }
}

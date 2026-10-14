package domain.exception;

/**
 * Basis semua kegagalan bisnis di layer domain.
 * Dibuat sebagai checked exception agar pemanggil wajib menanganinya.
 */
public class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }
}

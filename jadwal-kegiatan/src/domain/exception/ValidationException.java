package domain.exception;

/**
 * Dilempar ketika aturan validasi domain gagal (input kosong, format salah, dsb).
 */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}

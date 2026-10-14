package domain.exception;

/** Dilempar ketika aturan validasi bisnis gagal (mis. nama atau tujuan kunjungan kosong). */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}

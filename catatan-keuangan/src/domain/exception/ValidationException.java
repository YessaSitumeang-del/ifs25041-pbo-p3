package domain.exception;

/** Dilempar ketika aturan validasi bisnis gagal (mis. jumlah <= 0, keterangan kosong). */
public class ValidationException extends DomainException {
    public ValidationException(String message) {
        super(message);
    }
}

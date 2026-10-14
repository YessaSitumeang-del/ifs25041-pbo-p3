package domain.exception;

/** Dilempar ketika entity dengan ID tertentu tidak ditemukan. */
public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}

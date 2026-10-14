package domain.exception;

/**
 * Dilempar ketika entity dengan ID tertentu tidak ditemukan.
 */
public class EntityNotFoundException extends DomainException {
    private final Object id;

    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " dengan ID " + id + " tidak ditemukan.");
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}

package adapter.repository;

import domain.repository.IRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Basis penyimpanan in-memory generik. Subclass hanya perlu menjelaskan cara membaca ID,
 * memberi ID pada entity, dan membuat ID baru.
 */
public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {
    private final Map<ID, T> storage = new LinkedHashMap<>();

    /** @return ID entity, atau null jika entity belum pernah disimpan */
    protected abstract ID getId(T entity);

    /** @return entity dengan ID yang diberikan (boleh salinan baru jika field id final) */
    protected abstract T assignId(T entity, ID id);

    /** @return ID unik berikutnya */
    protected abstract ID generateId();

    @Override
    public T save(T entity) {
        T toStore = entity;
        ID id = getId(entity);
        if (id == null) {
            id = generateId();
            toStore = assignId(entity, id);
        }
        storage.put(id, toStore);
        return toStore;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    /** Salinan defensif: perubahan pada list hasil tidak memengaruhi penyimpanan internal. */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }
}

package adapter.repository;

import domain.repository.IRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Basis penyimpanan in-memory yang generik.
 * Subclass hanya perlu menjelaskan cara membaca ID, menempelkan ID baru,
 * dan membuat ID unik.
 *
 * @param <T>  tipe entity
 * @param <ID> tipe identitas entity
 */
public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {

    private final Map<ID, T> storage = new LinkedHashMap<>();

    /** Mengambil ID dari entity; null jika entity belum pernah disimpan. */
    protected abstract ID extractId(T entity);

    /** Mengembalikan entity (salinan) yang sama dengan ID yang diberikan. */
    protected abstract T assignId(T entity, ID id);

    /** Membuat ID unik berikutnya. */
    protected abstract ID generateId();

    @Override
    public T save(T entity) {
        ID id = extractId(entity);
        T toStore = entity;
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

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }
}

package domain.repository;

import java.util.List;
import java.util.Optional;

/**
 * Port repository generik yang dapat dipakai ulang oleh seluruh entity.
 *
 * @param <T>  tipe entity
 * @param <ID> tipe identitas entity
 */
public interface IRepository<T, ID> {

    /** Menyimpan entity. Entity baru (tanpa ID) akan diberi ID unik oleh repository. */
    T save(T entity);

    Optional<T> findById(ID id);

    /** Mengembalikan salinan daftar, bukan referensi internal repository. */
    List<T> findAll();

    /** @return true jika entity ada dan berhasil dihapus */
    boolean deleteById(ID id);
}

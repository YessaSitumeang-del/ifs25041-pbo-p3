package domain.repository;

import java.util.List;
import java.util.Optional;

/**
 * Port repository generik yang dapat dipakai ulang oleh semua entity.
 *
 * @param <T>  tipe entity
 * @param <ID> tipe identitas entity
 */
public interface IRepository<T, ID> {

    /** Menyimpan entity. Jika belum punya ID, repository membuat ID unik. Mengembalikan entity tersimpan. */
    T save(T entity);

    Optional<T> findById(ID id);

    /** Mengembalikan salinan seluruh data (bukan referensi list internal). */
    List<T> findAll();

    /** @return true jika data ditemukan dan berhasil dihapus */
    boolean deleteById(ID id);
}

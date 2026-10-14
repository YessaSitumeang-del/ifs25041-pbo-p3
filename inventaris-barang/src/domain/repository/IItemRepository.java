package domain.repository;

import domain.entity.Item;

import java.util.List;

/**
 * Port repository khusus barang.
 */
public interface IItemRepository extends IRepository<Item, Integer> {

    /** Mencari barang yang namanya mengandung kata kunci (tanpa membedakan huruf besar/kecil). */
    List<Item> findByNameContaining(String keyword);
}

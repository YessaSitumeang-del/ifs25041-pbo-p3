package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IItemRepository;

import java.util.List;

/**
 * Logika bisnis inventaris barang. Tanpa I/O; kegagalan dilaporkan
 * lewat checked domain exception.
 */
public class ItemUseCase {
    private final IItemRepository repository;

    public ItemUseCase(IItemRepository repository) {
        this.repository = repository;
    }

    public Item addItem(String name, int quantity, String category) throws ValidationException {
        return repository.save(new Item(name, quantity, category));
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    /**
     * Mengubah stok barang. newQuantity null berarti stok tidak diubah.
     */
    public Item changeStock(int id, Integer newQuantity)
            throws EntityNotFoundException, ValidationException {
        Item item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Barang", id));
        if (newQuantity == null) {
            return item;
        }
        Item updated = item.copy();
        updated.changeQuantity(newQuantity);
        return repository.save(updated);
    }

    public List<Item> searchByName(String keyword) throws ValidationException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Kata kunci tidak boleh kosong!");
        }
        return repository.findByNameContaining(keyword.trim());
    }

    public List<Item> getSortedItems(SortOption option) {
        List<Item> items = repository.findAll(); // salinan, aman untuk diurutkan
        items.sort(option.getComparator());
        return items;
    }

    public void deleteItem(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Barang", id);
        }
    }
}

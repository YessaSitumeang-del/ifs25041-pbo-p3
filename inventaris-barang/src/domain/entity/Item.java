package domain.entity;

import domain.exception.ValidationException;

/**
 * Entity barang inventaris. ID bersifat final: null berarti belum disimpan,
 * ID unik diberikan oleh repository saat save.
 */
public final class Item {
    private final Integer id;
    private String name;
    private int quantity;
    private String category;

    public Item(String name, int quantity, String category) throws ValidationException {
        this(null, name, quantity, category);
    }

    public Item(Integer id, String name, int quantity, String category) throws ValidationException {
        this.id = id;
        changeName(name);
        changeQuantity(quantity);
        changeCategory(category);
    }

    private Item(Item source, Integer id) {
        this.id = id;
        this.name = source.name;
        this.quantity = source.quantity;
        this.category = source.category;
    }

    /** Salinan entity ini dengan ID yang berbeda. */
    public Item withId(Integer newId) {
        return new Item(this, newId);
    }

    public Item copy() {
        return new Item(this, id);
    }

    public void changeName(String newName) throws ValidationException {
        if (newName == null || newName.trim().isEmpty()) {
            throw new ValidationException("Nama barang tidak boleh kosong!");
        }
        this.name = newName.trim();
    }

    public void changeQuantity(int newQuantity) throws ValidationException {
        if (newQuantity <= 0) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
        this.quantity = newQuantity;
    }

    public void changeCategory(String newCategory) throws ValidationException {
        if (newCategory == null || newCategory.trim().isEmpty()) {
            throw new ValidationException("Kategori barang tidak boleh kosong!");
        }
        this.category = newCategory.trim();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
}

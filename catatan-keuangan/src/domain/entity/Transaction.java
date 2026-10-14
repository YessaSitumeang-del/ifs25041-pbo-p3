package domain.entity;

/**
 * Entity transaksi keuangan.
 * ID bersifat final; nilai null berarti transaksi belum disimpan ke repository.
 * Perubahan data dilakukan lewat method bernama jelas, bukan setter generik.
 */
public class Transaction {
    private final Integer id;
    private String description;
    private long amount;
    private final TransactionType type;

    /** Membuat transaksi baru yang belum memiliki ID (ID dibuat oleh repository saat save). */
    public Transaction(String description, long amount, TransactionType type) {
        this(null, description, amount, type);
    }

    public Transaction(Integer id, String description, long amount, TransactionType type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void changeAmount(long newAmount) {
        this.amount = newAmount;
    }

    /** Mengembalikan salinan transaksi ini dengan ID yang diberikan (karena field id final). */
    public Transaction withId(Integer newId) {
        return new Transaction(newId, description, amount, type);
    }
}

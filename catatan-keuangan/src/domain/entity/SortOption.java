package domain.entity;

import java.util.Comparator;

/** Opsi pengurutan transaksi. Setiap opsi membawa Comparator-nya sendiri. */
public enum SortOption {
    AMOUNT_ASC(Comparator.comparingLong(Transaction::getAmount)),
    AMOUNT_DESC(Comparator.comparingLong(Transaction::getAmount).reversed()),
    INCOME_FIRST(Comparator.comparing(Transaction::getType)),
    EXPENSE_FIRST(Comparator.comparing(Transaction::getType).reversed());

    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transaction> getComparator() {
        return comparator;
    }
}

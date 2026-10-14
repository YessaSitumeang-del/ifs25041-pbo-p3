package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/** Logika bisnis catatan keuangan. Tidak ada I/O; kegagalan dilempar sebagai domain exception. */
public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction addIncome(String description, long amount) throws ValidationException {
        return addTransaction(description, amount, TransactionType.INCOME);
    }

    public Transaction addExpense(String description, long amount) throws ValidationException {
        return addTransaction(description, amount, TransactionType.EXPENSE);
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    /** Mencari transaksi berdasarkan keterangan (mengandung kata kunci, tidak peka huruf besar/kecil). */
    public List<Transaction> search(String keyword) throws ValidationException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Kata kunci tidak boleh kosong!");
        }
        String lowered = keyword.trim().toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(t -> t.getDescription().toLowerCase(Locale.ROOT).contains(lowered))
                .collect(Collectors.toList());
    }

    public List<Transaction> sort(SortOption option) {
        List<Transaction> transactions = repository.findAll();
        transactions.sort(option.getComparator());
        return transactions;
    }

    /** Saldo = total pemasukan - total pengeluaran. */
    public long getBalance() {
        long balance = 0;
        for (Transaction t : repository.findAll()) {
            if (t.getType() == TransactionType.INCOME) {
                balance += t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }
        return balance;
    }

    public void deleteTransaction(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus transaksi dengan ID: " + id + ".");
        }
    }

    private Transaction addTransaction(String description, long amount, TransactionType type)
            throws ValidationException {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("Keterangan tidak boleh kosong!");
        }
        if (amount <= 0) {
            throw new ValidationException("Jumlah tidak valid!");
        }
        return repository.save(new Transaction(description.trim(), amount, type));
    }
}

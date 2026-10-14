package adapter.presenter;

import domain.entity.Transaction;
import domain.entity.TransactionType;

import java.util.List;

/** Memformat seluruh output catatan keuangan menjadi teks. Tidak melakukan I/O. */
public class FinancePresenter {

    /** Daftar transaksi beserta saldo. */
    public String presentTransactions(List<Transaction> transactions, long balance) {
        StringBuilder sb = new StringBuilder("Daftar Transaksi:\n");
        appendTransactions(sb, transactions);
        sb.append("Saldo: Rp ").append(balance);
        return sb.toString();
    }

    public String presentSortedTransactions(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder("Daftar Transaksi (Terurut):\n");
        appendTransactions(sb, transactions);
        return trimTrailingNewline(sb);
    }

    public String presentSearchResult(String keyword, List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder("Hasil Pencarian: \"").append(keyword).append("\"\n");
        if (transactions.isEmpty()) {
            sb.append("- Transaksi tidak ditemukan!");
            return sb.toString();
        }
        for (Transaction t : transactions) {
            sb.append(presentTransaction(t)).append("\n");
        }
        return trimTrailingNewline(sb);
    }

    public String presentBalance(long balance) {
        return "Saldo saat ini: Rp " + balance;
    }

    public String presentAdded(Transaction transaction) {
        return "Berhasil menambah transaksi: " + presentTransaction(transaction);
    }

    public String presentDeleted() {
        return "Berhasil menghapus transaksi.";
    }

    public String presentError(String message) {
        return "[!] " + message;
    }

    public String presentTransaction(Transaction t) {
        return "[" + t.getId() + "] " + t.getDescription()
                + " | Rp " + t.getAmount()
                + " | [" + typeLabel(t.getType()) + "]";
    }

    private void appendTransactions(StringBuilder sb, List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            sb.append("- Belum ada transaksi!\n");
            return;
        }
        for (Transaction t : transactions) {
            sb.append(presentTransaction(t)).append("\n");
        }
    }

    private String typeLabel(TransactionType type) {
        return type == TransactionType.INCOME ? "Pemasukan" : "Pengeluaran";
    }

    private String trimTrailingNewline(StringBuilder sb) {
        int length = sb.length();
        if (length > 0 && sb.charAt(length - 1) == '\n') {
            sb.setLength(length - 1);
        }
        return sb.toString();
    }
}

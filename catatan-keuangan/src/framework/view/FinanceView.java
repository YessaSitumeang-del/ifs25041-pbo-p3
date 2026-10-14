package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

import java.util.List;

/** UI konsol catatan keuangan: menampilkan menu, membaca input, dan menangani domain exception. */
public class FinanceView {
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            System.out.println(presenter.presentTransactions(useCase.getAll(), useCase.getBalance()));
            printMenu();
            String choice = InputUtil.readLine("Pilih : ");
            if (InputUtil.isCancel(choice)) {
                return;
            }
            System.out.println();
            handleChoice(choice);
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                addTransaction(TransactionType.INCOME);
                break;
            case "2":
                addTransaction(TransactionType.EXPENSE);
                break;
            case "3":
                searchTransactions();
                break;
            case "4":
                sortTransactions();
                break;
            case "5":
                System.out.println(presenter.presentBalance(useCase.getBalance()));
                break;
            case "6":
                deleteTransaction();
                break;
            default:
                System.out.println(presenter.presentError("Pilihan tidak dimengerti."));
        }
    }

    private void addTransaction(TransactionType type) {
        System.out.println(type == TransactionType.INCOME ? "[Tambah Pemasukan]" : "[Tambah Pengeluaran]");
        String description = InputUtil.readLine("Keterangan (x Jika Batal) : ");
        if (InputUtil.isCancel(description)) {
            return;
        }
        String rawAmount = InputUtil.readLine("Jumlah : ");
        try {
            long amount = parseNumber(rawAmount, "Jumlah tidak valid!");
            Transaction transaction = (type == TransactionType.INCOME)
                    ? useCase.addIncome(description, amount)
                    : useCase.addExpense(description, amount);
            System.out.println(presenter.presentAdded(transaction));
        } catch (ValidationException e) {
            System.out.println(presenter.presentError(e.getMessage()));
        }
    }

    private void searchTransactions() {
        System.out.println("[Cari Transaksi]");
        String keyword = InputUtil.readLine("Kata Kunci (x Jika Batal) : ");
        if (InputUtil.isCancel(keyword)) {
            return;
        }
        try {
            List<Transaction> result = useCase.search(keyword);
            System.out.println(presenter.presentSearchResult(keyword, result));
        } catch (ValidationException e) {
            System.out.println(presenter.presentError(e.getMessage()));
        }
    }

    private void sortTransactions() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");
        String choice = InputUtil.readLine("Pilih : ");
        System.out.println();
        if (InputUtil.isCancel(choice)) {
            return;
        }
        SortOption option;
        switch (choice) {
            case "1":
                option = SortOption.AMOUNT_ASC;
                break;
            case "2":
                option = SortOption.AMOUNT_DESC;
                break;
            case "3":
                option = SortOption.INCOME_FIRST;
                break;
            case "4":
                option = SortOption.EXPENSE_FIRST;
                break;
            default:
                System.out.println(presenter.presentError("Pilihan tidak valid!"));
                return;
        }
        System.out.println(presenter.presentSortedTransactions(useCase.sort(option)));
    }

    private void deleteTransaction() {
        System.out.println("[Hapus Transaksi]");
        String rawId = InputUtil.readLine("ID Transaksi (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = (int) parseNumber(rawId, "ID tidak valid!");
            useCase.deleteTransaction(id);
            System.out.println(presenter.presentDeleted());
        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println(presenter.presentError(e.getMessage()));
        }
    }

    /** Mengubah teks menjadi angka; teks non-numerik dilaporkan sebagai ValidationException. */
    private long parseNumber(String raw, String errorMessage) throws ValidationException {
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException e) {
            throw new ValidationException(errorMessage);
        }
    }
}

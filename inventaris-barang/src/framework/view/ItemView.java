package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ItemUseCase;

import java.util.List;

/**
 * UI konsol inventaris barang. Memanggil use case di dalam try-catch
 * dan menampilkan hasilnya lewat presenter.
 */
public class ItemView {
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase useCase, ItemPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        boolean firstScreen = true;
        while (running) {
            if (!firstScreen) {
                System.out.println();
            }
            firstScreen = false;

            System.out.println(presenter.formatItemList(useCase.getAllItems()));
            System.out.println(presenter.formatMenu());
            String choice = InputUtil.readChoice("Pilih : ");

            switch (choice.toLowerCase()) {
                case "1":
                    addItem();
                    break;
                case "2":
                    changeStock();
                    break;
                case "3":
                    searchItems();
                    break;
                case "4":
                    sortItems();
                    break;
                case "5":
                    deleteItem();
                    break;
                case "x":
                    running = false;
                    break;
                default:
                    System.out.println(presenter.formatInvalidMenu());
            }
        }
    }

    private void addItem() {
        System.out.println(presenter.formatTitle("Menambah Barang"));
        try {
            String name = InputUtil.readLine("Nama (x Jika Batal) : ");
            if (InputUtil.isCancel(name)) {
                return;
            }
            int quantity = parseQuantity(InputUtil.readLine("Jumlah : "));
            String category = InputUtil.readLine("Kategori (x Jika Batal) : ");
            if (InputUtil.isCancel(category)) {
                return;
            }
            Item item = useCase.addItem(name, quantity, category);
            System.out.println(presenter.formatAddSuccess(item));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void changeStock() {
        System.out.println(presenter.formatTitle("Mengubah Stok"));
        String rawId = InputUtil.readLine("ID Barang yang diubah (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        Integer id = null;
        try {
            id = parseId(rawId);
            String rawQuantity = InputUtil.readLine("Jumlah Baru (Kosongkan jika tidak ingin mengubah) : ");
            Integer quantity = rawQuantity.isEmpty() ? null : parseQuantity(rawQuantity);
            useCase.changeStock(id, quantity);
            System.out.println(presenter.formatUpdateSuccess());
        } catch (EntityNotFoundException e) {
            System.out.println(presenter.formatUpdateFailed(e.getId()));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void searchItems() {
        System.out.println(presenter.formatTitle("Mencari Barang"));
        String keyword = InputUtil.readLine("Kata Kunci (x Jika Batal) : ");
        if (InputUtil.isCancel(keyword)) {
            return;
        }
        try {
            List<Item> result = useCase.searchByName(keyword);
            System.out.println(presenter.formatSearchResult(keyword, result));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void sortItems() {
        System.out.println(presenter.formatTitle("Mengurutkan Barang"));
        System.out.println(presenter.formatSortMenu());
        String choice = InputUtil.readChoice("Pilih : ");
        if (InputUtil.isCancel(choice)) {
            return;
        }
        SortOption option = toSortOption(choice);
        if (option == null) {
            System.out.println(presenter.formatInvalidSort());
            return;
        }
        System.out.println(presenter.formatSortedList(useCase.getSortedItems(option)));
    }

    private void deleteItem() {
        System.out.println(presenter.formatTitle("Menghapus Barang"));
        String rawId = InputUtil.readLine("[ID Barang] yang dihapus (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = parseId(rawId);
            useCase.deleteItem(id);
            System.out.println(presenter.formatDeleteSuccess());
        } catch (EntityNotFoundException e) {
            System.out.println(presenter.formatDeleteFailed(e.getId()));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private SortOption toSortOption(String choice) {
        switch (choice) {
            case "1":
                return SortOption.NAME_ASC;
            case "2":
                return SortOption.NAME_DESC;
            case "3":
                return SortOption.QUANTITY_ASC;
            case "4":
                return SortOption.QUANTITY_DESC;
            default:
                return null;
        }
    }

    private int parseId(String raw) throws ValidationException {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new ValidationException("ID tidak valid!");
        }
    }

    /** Jumlah stok harus numerik dan lebih dari 0. */
    private int parseQuantity(String raw) throws ValidationException {
        try {
            int quantity = Integer.parseInt(raw);
            if (quantity <= 0) {
                throw new ValidationException("Jumlah stok tidak valid!");
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new ValidationException("Jumlah stok tidak valid!");
        }
    }
}

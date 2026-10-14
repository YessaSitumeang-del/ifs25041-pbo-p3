package adapter.presenter;

import domain.entity.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Memformat seluruh output ke layar. Entity tidak mengetahui format tampilan.
 */
public class ItemPresenter {
    private static final String NL = System.lineSeparator();

    public String formatItem(Item item) {
        return "[" + item.getId() + "] " + item.getName()
                + " | Stok: " + item.getQuantity()
                + " | Kategori: " + item.getCategory();
    }

    public String formatItemList(List<Item> items) {
        return "Daftar Barang:" + NL + formatBody(items, "- Data barang belum tersedia!");
    }

    public String formatSortedList(List<Item> items) {
        return "Daftar Barang (Terurut):" + NL + formatBody(items, "- Data barang belum tersedia!");
    }

    public String formatSearchResult(String keyword, List<Item> items) {
        return "Hasil Pencarian: \"" + keyword + "\"" + NL
                + formatBody(items, "- Barang tidak ditemukan!");
    }

    public String formatMenu() {
        return String.join(NL,
                "Menu:",
                "1. Tambah",
                "2. Ubah Stok",
                "3. Cari",
                "4. Urutkan",
                "5. Hapus",
                "x. Keluar");
    }

    public String formatSortMenu() {
        return String.join(NL,
                "Pilihan Pengurutan:",
                "1. Nama (A-Z)",
                "2. Nama (Z-A)",
                "3. Jumlah (Terkecil -> Terbesar)",
                "4. Jumlah (Terbesar -> Terkecil)",
                "x. Batal");
    }

    public String formatTitle(String title) {
        return "[" + title + "]";
    }

    public String formatAddSuccess(Item item) {
        return "Berhasil menambah barang: " + formatItem(item);
    }

    public String formatUpdateSuccess() {
        return "Berhasil mengubah stok barang.";
    }

    public String formatDeleteSuccess() {
        return "Berhasil menghapus barang.";
    }

    public String formatUpdateFailed(Object id) {
        return formatError("Gagal mengubah stok barang dengan ID: " + id + ".");
    }

    public String formatDeleteFailed(Object id) {
        return formatError("Gagal menghapus barang dengan ID: " + id + ".");
    }

    public String formatInvalidMenu() {
        return formatError("Pilihan tidak dimengerti.");
    }

    public String formatInvalidSort() {
        return formatError("Pilihan tidak valid!");
    }

    public String formatError(String message) {
        return "[!] " + message;
    }

    private String formatBody(List<Item> items, String emptyMessage) {
        if (items.isEmpty()) {
            return emptyMessage;
        }
        List<String> lines = new ArrayList<>();
        for (Item item : items) {
            lines.add(formatItem(item));
        }
        return String.join(NL, lines);
    }
}

package adapter.presenter;

import domain.entity.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Memformat seluruh output ke layar. Entity tidak mengetahui format tampilan.
 */
public class ContactPresenter {
    private static final String NL = System.lineSeparator();

    public String formatContact(Contact contact) {
        return "[" + contact.getId() + "] " + contact.getName()
                + " | Telp: " + contact.getPhone()
                + " | Email: " + contact.getEmail();
    }

    public String formatContactList(List<Contact> contacts) {
        return "Daftar Kontak:" + NL + formatBody(contacts, "- Data kontak belum tersedia!");
    }

    public String formatSortedList(List<Contact> contacts) {
        return "Daftar Kontak (Terurut):" + NL + formatBody(contacts, "- Data kontak belum tersedia!");
    }

    public String formatSearchResult(String keyword, List<Contact> contacts) {
        return "Hasil Pencarian: \"" + keyword + "\"" + NL
                + formatBody(contacts, "- Kontak tidak ditemukan!");
    }

    public String formatMenu() {
        return String.join(NL,
                "Menu:",
                "1. Tambah",
                "2. Ubah",
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
                "x. Batal");
    }

    public String formatTitle(String title) {
        return "[" + title + "]";
    }

    public String formatAddSuccess(Contact contact) {
        return "Berhasil menambah kontak: " + formatContact(contact);
    }

    public String formatUpdateSuccess() {
        return "Berhasil mengubah kontak.";
    }

    public String formatDeleteSuccess() {
        return "Berhasil menghapus kontak.";
    }

    public String formatUpdateFailed(Object id) {
        return formatError("Gagal mengubah kontak dengan ID: " + id + ".");
    }

    public String formatDeleteFailed(Object id) {
        return formatError("Gagal menghapus kontak dengan ID: " + id + ".");
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

    private String formatBody(List<Contact> contacts, String emptyMessage) {
        if (contacts.isEmpty()) {
            return emptyMessage;
        }
        List<String> lines = new ArrayList<>();
        for (Contact contact : contacts) {
            lines.add(formatContact(contact));
        }
        return String.join(NL, lines);
    }
}

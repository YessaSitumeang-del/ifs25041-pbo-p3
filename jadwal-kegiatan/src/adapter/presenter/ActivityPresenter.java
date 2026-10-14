package adapter.presenter;

import domain.entity.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Memformat seluruh output ke layar. Entity tidak mengetahui format tampilan.
 */
public class ActivityPresenter {
    private static final String NL = System.lineSeparator();

    public String formatActivity(Activity activity) {
        return "[" + activity.getId() + "] " + activity.getTitle()
                + " | Hari: " + activity.getDay().getLabel()
                + " | Pukul: " + activity.getTime();
    }

    public String formatActivityList(List<Activity> activities) {
        return "Daftar Kegiatan:" + NL + formatBody(activities, "- Data kegiatan belum tersedia!");
    }

    public String formatSortedList(List<Activity> activities) {
        return "Daftar Kegiatan (Terurut):" + NL + formatBody(activities, "- Data kegiatan belum tersedia!");
    }

    public String formatSearchResult(String keyword, List<Activity> activities) {
        return "Hasil Pencarian: \"" + keyword + "\"" + NL
                + formatBody(activities, "- Kegiatan tidak ditemukan!");
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
                "1. Hari (Senin -> Minggu)",
                "2. Waktu (Awal -> Akhir)",
                "3. Judul (A-Z)",
                "4. Judul (Z-A)",
                "x. Batal");
    }

    public String formatTitle(String title) {
        return "[" + title + "]";
    }

    public String formatAddSuccess(Activity activity) {
        return "Berhasil menambah kegiatan: " + formatActivity(activity);
    }

    public String formatUpdateSuccess() {
        return "Berhasil mengubah kegiatan.";
    }

    public String formatDeleteSuccess() {
        return "Berhasil menghapus kegiatan.";
    }

    public String formatUpdateFailed(Object id) {
        return formatError("Gagal mengubah kegiatan dengan ID: " + id + ".");
    }

    public String formatDeleteFailed(Object id) {
        return formatError("Gagal menghapus kegiatan dengan ID: " + id + ".");
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

    private String formatBody(List<Activity> activities, String emptyMessage) {
        if (activities.isEmpty()) {
            return emptyMessage;
        }
        List<String> lines = new ArrayList<>();
        for (Activity activity : activities) {
            lines.add(formatActivity(activity));
        }
        return String.join(NL, lines);
    }
}

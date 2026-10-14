package adapter.presenter;

import domain.entity.Guest;

import java.util.List;

/** Memformat seluruh output buku tamu menjadi teks. Tidak melakukan I/O. */
public class GuestPresenter {

    public String presentGuests(List<Guest> guests) {
        StringBuilder sb = new StringBuilder("Daftar Tamu:\n");
        if (guests.isEmpty()) {
            sb.append("- Data tamu belum tersedia!");
            return sb.toString();
        }
        return sb.append(joinGuests(guests)).toString();
    }

    public String presentSearchResult(String keyword, List<Guest> guests) {
        StringBuilder sb = new StringBuilder("Hasil Pencarian: \"").append(keyword).append("\"\n");
        if (guests.isEmpty()) {
            sb.append("- Tamu tidak ditemukan!");
            return sb.toString();
        }
        return sb.append(joinGuests(guests)).toString();
    }

    public String presentRegistered(Guest guest) {
        return "Berhasil mendaftarkan tamu: " + presentGuest(guest);
    }

    public String presentDeleted() {
        return "Berhasil menghapus tamu.";
    }

    public String presentError(String message) {
        return "[!] " + message;
    }

    public String presentGuest(Guest guest) {
        return "[" + guest.getId() + "] " + guest.getName() + " | Tujuan: " + guest.getPurpose();
    }

    private String joinGuests(List<Guest> guests) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < guests.size(); i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append(presentGuest(guests.get(i)));
        }
        return sb.toString();
    }
}

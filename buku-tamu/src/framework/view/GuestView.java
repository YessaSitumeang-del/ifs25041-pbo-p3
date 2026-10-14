package framework.view;

import adapter.presenter.GuestPresenter;
import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.GuestUseCase;

import java.util.List;

/** UI konsol buku tamu: menampilkan menu, membaca input, dan menangani domain exception. */
public class GuestView {
    private final GuestUseCase useCase;
    private final GuestPresenter presenter;

    public GuestView(GuestUseCase useCase, GuestPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            System.out.println(presenter.presentGuests(useCase.getAll()));
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
        System.out.println("1. Daftarkan");
        System.out.println("2. Cari");
        System.out.println("3. Hapus");
        System.out.println("x. Keluar");
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                registerGuest();
                break;
            case "2":
                searchGuests();
                break;
            case "3":
                deleteGuest();
                break;
            default:
                System.out.println(presenter.presentError("Pilihan tidak dimengerti."));
        }
    }

    private void registerGuest() {
        System.out.println("[Mendaftarkan Tamu]");
        String name = InputUtil.readLine("Nama (x Jika Batal) : ");
        if (InputUtil.isCancel(name)) {
            return;
        }
        String purpose = InputUtil.readLine("Tujuan Kunjungan (x Jika Batal) : ");
        if (InputUtil.isCancel(purpose)) {
            return;
        }
        try {
            Guest guest = useCase.register(name, purpose);
            System.out.println(presenter.presentRegistered(guest));
        } catch (ValidationException e) {
            System.out.println(presenter.presentError(e.getMessage()));
        }
    }

    private void searchGuests() {
        System.out.println("[Mencari Tamu]");
        String keyword = InputUtil.readLine("Nama (x Jika Batal) : ");
        if (InputUtil.isCancel(keyword)) {
            return;
        }
        try {
            List<Guest> result = useCase.search(keyword);
            System.out.println(presenter.presentSearchResult(keyword, result));
        } catch (ValidationException e) {
            System.out.println(presenter.presentError(e.getMessage()));
        }
    }

    private void deleteGuest() {
        System.out.println("[Menghapus Tamu]");
        String rawId = InputUtil.readLine("[ID Tamu] yang dihapus (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = parseId(rawId);
            useCase.delete(id);
            System.out.println(presenter.presentDeleted());
        } catch (ValidationException | EntityNotFoundException e) {
            System.out.println(presenter.presentError(e.getMessage()));
        }
    }

    /** Mengubah teks menjadi ID; teks non-numerik dilaporkan sebagai ValidationException. */
    private int parseId(String raw) throws ValidationException {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new ValidationException("ID tidak valid!");
        }
    }
}

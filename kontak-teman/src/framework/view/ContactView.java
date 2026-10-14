package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ContactUseCase;

import java.util.List;

/**
 * UI konsol kontak teman. Memanggil use case di dalam try-catch
 * dan menampilkan hasilnya lewat presenter.
 */
public class ContactView {
    private static final String KEEP_HINT = " (Kosongkan jika tidak ingin mengubah) : ";

    private final ContactUseCase useCase;
    private final ContactPresenter presenter;

    public ContactView(ContactUseCase useCase, ContactPresenter presenter) {
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

            System.out.println(presenter.formatContactList(useCase.getAllContacts()));
            System.out.println(presenter.formatMenu());
            String choice = InputUtil.readChoice("Pilih : ");

            switch (choice.toLowerCase()) {
                case "1":
                    addContact();
                    break;
                case "2":
                    updateContact();
                    break;
                case "3":
                    searchContacts();
                    break;
                case "4":
                    sortContacts();
                    break;
                case "5":
                    deleteContact();
                    break;
                case "x":
                    running = false;
                    break;
                default:
                    System.out.println(presenter.formatInvalidMenu());
            }
        }
    }

    private void addContact() {
        System.out.println(presenter.formatTitle("Menambah Kontak"));
        String name = InputUtil.readLine("Nama (x Jika Batal) : ");
        if (InputUtil.isCancel(name)) {
            return;
        }
        String phone = InputUtil.readLine("Telepon : ");
        String email = InputUtil.readLine("Email : ");
        try {
            Contact contact = useCase.addContact(name, phone, email);
            System.out.println(presenter.formatAddSuccess(contact));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void updateContact() {
        System.out.println(presenter.formatTitle("Mengubah Kontak"));
        String rawId = InputUtil.readLine("ID Kontak yang diubah (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = parseId(rawId);
            String name = InputUtil.readLine("Nama Baru" + KEEP_HINT);
            String phone = InputUtil.readLine("Telepon Baru" + KEEP_HINT);
            String email = InputUtil.readLine("Email Baru" + KEEP_HINT);
            useCase.updateContact(id, name, phone, email);
            System.out.println(presenter.formatUpdateSuccess());
        } catch (EntityNotFoundException e) {
            System.out.println(presenter.formatUpdateFailed(e.getId()));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void searchContacts() {
        System.out.println(presenter.formatTitle("Mencari Kontak"));
        String keyword = InputUtil.readLine("Kata Kunci (x Jika Batal) : ");
        if (InputUtil.isCancel(keyword)) {
            return;
        }
        try {
            List<Contact> result = useCase.searchByName(keyword);
            System.out.println(presenter.formatSearchResult(keyword, result));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void sortContacts() {
        System.out.println(presenter.formatTitle("Mengurutkan Kontak"));
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
        System.out.println(presenter.formatSortedList(useCase.getSortedContacts(option)));
    }

    private void deleteContact() {
        System.out.println(presenter.formatTitle("Menghapus Kontak"));
        String rawId = InputUtil.readLine("[ID Kontak] yang dihapus (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = parseId(rawId);
            useCase.deleteContact(id);
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
}

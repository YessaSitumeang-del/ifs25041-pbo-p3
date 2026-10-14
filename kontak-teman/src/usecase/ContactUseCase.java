package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;

import java.util.List;

/**
 * Logika bisnis kontak teman. Tanpa I/O; kegagalan dilaporkan
 * lewat checked domain exception.
 */
public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public Contact addContact(String name, String phone, String email) throws ValidationException {
        return repository.save(new Contact(name, phone, email));
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    /**
     * Update parsial: parameter kosong/null berarti field tidak diubah.
     * Perubahan diterapkan pada salinan sehingga data asli tetap utuh bila ada validasi yang gagal.
     */
    public Contact updateContact(int id, String name, String phone, String email)
            throws EntityNotFoundException, ValidationException {
        Contact existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kontak", id));
        Contact updated = existing.copy();
        if (hasText(name)) {
            updated.changeName(name);
        }
        if (hasText(phone)) {
            updated.changePhone(phone);
        }
        if (hasText(email)) {
            updated.changeEmail(email);
        }
        return repository.save(updated);
    }

    public List<Contact> searchByName(String keyword) throws ValidationException {
        if (!hasText(keyword)) {
            throw new ValidationException("Kata kunci tidak boleh kosong!");
        }
        return repository.findByNameContaining(keyword.trim());
    }

    public List<Contact> getSortedContacts(SortOption option) {
        List<Contact> contacts = repository.findAll(); // salinan, aman untuk diurutkan
        contacts.sort(option.getComparator());
        return contacts;
    }

    public void deleteContact(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Kontak", id);
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementasi in-memory dari port IContactRepository.
 */
public class ContactRepository extends InMemoryRepository<Contact, Integer> implements IContactRepository {
    private int lastId = 0;

    @Override
    protected Integer extractId(Contact entity) {
        return entity.getId();
    }

    @Override
    protected Contact assignId(Contact entity, Integer id) {
        return entity.withId(id);
    }

    @Override
    protected Integer generateId() {
        return ++lastId;
    }

    @Override
    public List<Contact> findByNameContaining(String keyword) {
        String needle = keyword.toLowerCase(Locale.ROOT);
        List<Contact> result = new ArrayList<>();
        for (Contact contact : findAll()) {
            if (contact.getName().toLowerCase(Locale.ROOT).contains(needle)) {
                result.add(contact);
            }
        }
        return result;
    }
}

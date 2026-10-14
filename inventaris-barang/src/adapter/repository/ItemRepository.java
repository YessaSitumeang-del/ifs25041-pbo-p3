package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementasi in-memory dari port IItemRepository.
 */
public class ItemRepository extends InMemoryRepository<Item, Integer> implements IItemRepository {
    private int lastId = 0;

    @Override
    protected Integer extractId(Item entity) {
        return entity.getId();
    }

    @Override
    protected Item assignId(Item entity, Integer id) {
        return entity.withId(id);
    }

    @Override
    protected Integer generateId() {
        return ++lastId;
    }

    @Override
    public List<Item> findByNameContaining(String keyword) {
        String needle = keyword.toLowerCase(Locale.ROOT);
        List<Item> result = new ArrayList<>();
        for (Item item : findAll()) {
            if (item.getName().toLowerCase(Locale.ROOT).contains(needle)) {
                result.add(item);
            }
        }
        return result;
    }
}

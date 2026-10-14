package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

/** Implementasi in-memory dari IGuestRepository. */
public class GuestRepository extends InMemoryRepository<Guest, Integer> implements IGuestRepository {
    private int lastId = 0;

    @Override
    protected Integer getId(Guest entity) {
        return entity.getId();
    }

    @Override
    protected Guest assignId(Guest entity, Integer id) {
        return entity.withId(id);
    }

    @Override
    protected Integer generateId() {
        return ++lastId;
    }
}

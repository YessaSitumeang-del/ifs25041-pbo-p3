package adapter.repository;

import domain.entity.Activity;
import domain.repository.IActivityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Implementasi in-memory dari port IActivityRepository.
 */
public class ActivityRepository extends InMemoryRepository<Activity, Integer> implements IActivityRepository {
    private int lastId = 0;

    @Override
    protected Integer extractId(Activity entity) {
        return entity.getId();
    }

    @Override
    protected Activity assignId(Activity entity, Integer id) {
        return entity.withId(id);
    }

    @Override
    protected Integer generateId() {
        return ++lastId;
    }

    @Override
    public List<Activity> findByTitleContaining(String keyword) {
        String needle = keyword.toLowerCase(Locale.ROOT);
        List<Activity> result = new ArrayList<>();
        for (Activity activity : findAll()) {
            if (activity.getTitle().toLowerCase(Locale.ROOT).contains(needle)) {
                result.add(activity);
            }
        }
        return result;
    }
}

package usecase;

import domain.entity.Activity;
import domain.entity.Day;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IActivityRepository;

import java.util.List;

/**
 * Logika bisnis jadwal kegiatan. Tanpa I/O; kegagalan dilaporkan
 * lewat checked domain exception.
 */
public class ActivityUseCase {
    private final IActivityRepository repository;

    public ActivityUseCase(IActivityRepository repository) {
        this.repository = repository;
    }

    public Activity addActivity(String title, String day, String time) throws ValidationException {
        return repository.save(new Activity(title, Day.fromLabel(day), time));
    }

    public List<Activity> getAllActivities() {
        return repository.findAll();
    }

    /**
     * Update parsial: parameter kosong/null berarti field tidak diubah.
     * Perubahan diterapkan pada salinan sehingga data asli tetap utuh bila ada validasi yang gagal.
     */
    public Activity updateActivity(int id, String title, String day, String time)
            throws EntityNotFoundException, ValidationException {
        Activity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kegiatan", id));
        Activity updated = existing.copy();
        if (hasText(title)) {
            updated.changeTitle(title);
        }
        if (hasText(day)) {
            updated.changeDay(Day.fromLabel(day));
        }
        if (hasText(time)) {
            updated.changeTime(time);
        }
        return repository.save(updated);
    }

    public List<Activity> searchByTitle(String keyword) throws ValidationException {
        if (!hasText(keyword)) {
            throw new ValidationException("Kata kunci tidak boleh kosong!");
        }
        return repository.findByTitleContaining(keyword.trim());
    }

    public List<Activity> getSortedActivities(SortOption option) {
        List<Activity> activities = repository.findAll(); // salinan, aman untuk diurutkan
        activities.sort(option.getComparator());
        return activities;
    }

    public void deleteActivity(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Kegiatan", id);
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

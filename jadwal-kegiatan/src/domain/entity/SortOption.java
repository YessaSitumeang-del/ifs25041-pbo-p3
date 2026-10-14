package domain.entity;

import java.util.Comparator;

/**
 * Pilihan pengurutan kegiatan. Setiap opsi membawa Comparator-nya sendiri.
 * Pengurutan hari memakai waktu sebagai pembeda, dan sebaliknya.
 */
public enum SortOption {
    DAY(Comparator.comparing(Activity::getDay).thenComparing(Activity::getTime)),
    TIME(Comparator.comparing(Activity::getTime).thenComparing(Activity::getDay)),
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> getComparator() {
        return comparator;
    }
}

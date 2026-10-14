package domain.entity;

import domain.exception.ValidationException;

import java.util.regex.Pattern;

/**
 * Entity kegiatan harian. ID bersifat final: null berarti belum disimpan,
 * ID unik diberikan oleh repository saat save.
 */
public final class Activity {
    private static final Pattern TIME_PATTERN = Pattern.compile("([01]\\d|2[0-3]):[0-5]\\d");

    private final Integer id;
    private String title;
    private Day day;
    private String time;

    public Activity(String title, Day day, String time) throws ValidationException {
        this(null, title, day, time);
    }

    public Activity(Integer id, String title, Day day, String time) throws ValidationException {
        this.id = id;
        changeTitle(title);
        changeDay(day);
        changeTime(time);
    }

    private Activity(Activity source, Integer id) {
        this.id = id;
        this.title = source.title;
        this.day = source.day;
        this.time = source.time;
    }

    /** Salinan entity ini dengan ID yang berbeda. */
    public Activity withId(Integer newId) {
        return new Activity(this, newId);
    }

    public Activity copy() {
        return new Activity(this, id);
    }

    public void changeTitle(String newTitle) throws ValidationException {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new ValidationException("Judul kegiatan tidak boleh kosong!");
        }
        this.title = newTitle.trim();
    }

    public void changeDay(Day newDay) throws ValidationException {
        if (newDay == null) {
            throw new ValidationException("Hari tidak valid! Gunakan Senin sampai Minggu.");
        }
        this.day = newDay;
    }

    /** Waktu harus berformat HH:mm (00:00 - 23:59). */
    public void changeTime(String newTime) throws ValidationException {
        if (newTime == null || !TIME_PATTERN.matcher(newTime.trim()).matches()) {
            throw new ValidationException("Waktu tidak valid! Gunakan format HH:mm.");
        }
        this.time = newTime.trim();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Day getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}

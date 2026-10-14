package domain.entity;

import domain.exception.ValidationException;

/**
 * Hari dalam seminggu, berurutan Senin sampai Minggu (urutan enum = urutan pengurutan).
 */
public enum Day {
    SENIN("Senin"),
    SELASA("Selasa"),
    RABU("Rabu"),
    KAMIS("Kamis"),
    JUMAT("Jumat"),
    SABTU("Sabtu"),
    MINGGU("Minggu");

    private final String label;

    Day(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /** Mengubah teks (tanpa membedakan huruf besar/kecil) menjadi Day. */
    public static Day fromLabel(String text) throws ValidationException {
        if (text != null) {
            String trimmed = text.trim();
            for (Day day : values()) {
                if (day.label.equalsIgnoreCase(trimmed)) {
                    return day;
                }
            }
        }
        throw new ValidationException("Hari tidak valid! Gunakan Senin sampai Minggu.");
    }
}

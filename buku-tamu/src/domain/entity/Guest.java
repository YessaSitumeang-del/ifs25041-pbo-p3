package domain.entity;

/**
 * Entity tamu pada buku tamu.
 * ID bersifat final; nilai null berarti tamu belum disimpan ke repository.
 * Perubahan data dilakukan lewat method bernama jelas, bukan setter generik.
 */
public class Guest {
    private final Integer id;
    private String name;
    private String purpose;

    /** Membuat tamu baru yang belum memiliki ID (ID dibuat oleh repository saat save). */
    public Guest(String name, String purpose) {
        this(null, name, purpose);
    }

    public Guest(Integer id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changePurpose(String newPurpose) {
        this.purpose = newPurpose;
    }

    /** Mengembalikan salinan tamu ini dengan ID yang diberikan (karena field id final). */
    public Guest withId(Integer newId) {
        return new Guest(newId, name, purpose);
    }
}

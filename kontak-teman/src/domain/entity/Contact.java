package domain.entity;

import domain.exception.ValidationException;

/**
 * Entity kontak teman. ID bersifat final: null berarti belum disimpan,
 * ID unik diberikan oleh repository saat save.
 */
public final class Contact {
    private final Integer id;
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) throws ValidationException {
        this(null, name, phone, email);
    }

    public Contact(Integer id, String name, String phone, String email) throws ValidationException {
        this.id = id;
        changeName(name);
        changePhone(phone);
        changeEmail(email);
    }

    private Contact(Contact source, Integer id) {
        this.id = id;
        this.name = source.name;
        this.phone = source.phone;
        this.email = source.email;
    }

    /** Salinan entity ini dengan ID yang berbeda. */
    public Contact withId(Integer newId) {
        return new Contact(this, newId);
    }

    public Contact copy() {
        return new Contact(this, id);
    }

    public void changeName(String newName) throws ValidationException {
        if (isBlank(newName)) {
            throw new ValidationException("Nama kontak tidak boleh kosong!");
        }
        this.name = newName.trim();
    }

    public void changePhone(String newPhone) throws ValidationException {
        if (isBlank(newPhone)) {
            throw new ValidationException("Nomor telepon tidak boleh kosong!");
        }
        this.phone = newPhone.trim();
    }

    public void changeEmail(String newEmail) throws ValidationException {
        if (isBlank(newEmail)) {
            throw new ValidationException("Email tidak boleh kosong!");
        }
        this.email = newEmail.trim();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

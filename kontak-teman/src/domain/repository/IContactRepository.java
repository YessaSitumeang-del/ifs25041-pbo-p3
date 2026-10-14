package domain.repository;

import domain.entity.Contact;

import java.util.List;

/**
 * Port repository khusus kontak.
 */
public interface IContactRepository extends IRepository<Contact, Integer> {

    /** Mencari kontak yang namanya mengandung kata kunci (tanpa membedakan huruf besar/kecil). */
    List<Contact> findByNameContaining(String keyword);
}

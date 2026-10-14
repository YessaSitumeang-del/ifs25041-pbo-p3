package usecase;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IGuestRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/** Logika bisnis buku tamu. Tidak ada I/O; kegagalan dilempar sebagai domain exception. */
public class GuestUseCase {
    private final IGuestRepository repository;

    public GuestUseCase(IGuestRepository repository) {
        this.repository = repository;
    }

    public Guest register(String name, String purpose) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nama tidak boleh kosong!");
        }
        if (purpose == null || purpose.trim().isEmpty()) {
            throw new ValidationException("Tujuan kunjungan tidak boleh kosong!");
        }
        return repository.save(new Guest(name.trim(), purpose.trim()));
    }

    public List<Guest> getAll() {
        return repository.findAll();
    }

    /** Mencari tamu berdasarkan nama (mengandung kata kunci, tidak peka huruf besar/kecil). */
    public List<Guest> search(String keyword) throws ValidationException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Kata kunci tidak boleh kosong!");
        }
        String lowered = keyword.trim().toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(g -> g.getName().toLowerCase(Locale.ROOT).contains(lowered))
                .collect(Collectors.toList());
    }

    public void delete(int id) throws EntityNotFoundException {
        if (!repository.deleteById(id)) {
            throw new EntityNotFoundException("Gagal menghapus tamu dengan ID: " + id + ".");
        }
    }
}

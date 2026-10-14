package domain.repository;

import domain.entity.Activity;

import java.util.List;

/**
 * Port repository khusus kegiatan.
 */
public interface IActivityRepository extends IRepository<Activity, Integer> {

    /** Mencari kegiatan yang judulnya mengandung kata kunci (tanpa membedakan huruf besar/kecil). */
    List<Activity> findByTitleContaining(String keyword);
}

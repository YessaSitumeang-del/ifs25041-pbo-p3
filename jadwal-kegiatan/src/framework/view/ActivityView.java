package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

import java.util.List;

/**
 * UI konsol jadwal kegiatan. Memanggil use case di dalam try-catch
 * dan menampilkan hasilnya lewat presenter.
 */
public class ActivityView {
    private static final String KEEP_HINT = " (Kosongkan jika tidak ingin mengubah) : ";

    private final ActivityUseCase useCase;
    private final ActivityPresenter presenter;

    public ActivityView(ActivityUseCase useCase, ActivityPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        boolean firstScreen = true;
        while (running) {
            if (!firstScreen) {
                System.out.println();
            }
            firstScreen = false;

            System.out.println(presenter.formatActivityList(useCase.getAllActivities()));
            System.out.println(presenter.formatMenu());
            String choice = InputUtil.readChoice("Pilih : ");

            switch (choice.toLowerCase()) {
                case "1":
                    addActivity();
                    break;
                case "2":
                    updateActivity();
                    break;
                case "3":
                    searchActivities();
                    break;
                case "4":
                    sortActivities();
                    break;
                case "5":
                    deleteActivity();
                    break;
                case "x":
                    running = false;
                    break;
                default:
                    System.out.println(presenter.formatInvalidMenu());
            }
        }
    }

    private void addActivity() {
        System.out.println(presenter.formatTitle("Menambah Kegiatan"));
        String title = InputUtil.readLine("Judul (x Jika Batal) : ");
        if (InputUtil.isCancel(title)) {
            return;
        }
        String day = InputUtil.readLine("Hari (x Jika Batal) : ");
        if (InputUtil.isCancel(day)) {
            return;
        }
        String time = InputUtil.readLine("Waktu (x Jika Batal) : ");
        if (InputUtil.isCancel(time)) {
            return;
        }
        try {
            Activity activity = useCase.addActivity(title, day, time);
            System.out.println(presenter.formatAddSuccess(activity));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void updateActivity() {
        System.out.println(presenter.formatTitle("Mengubah Kegiatan"));
        String rawId = InputUtil.readLine("ID Kegiatan yang diubah (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = parseId(rawId);
            String title = InputUtil.readLine("Judul Baru" + KEEP_HINT);
            String day = InputUtil.readLine("Hari Baru" + KEEP_HINT);
            String time = InputUtil.readLine("Waktu Baru" + KEEP_HINT);
            useCase.updateActivity(id, title, day, time);
            System.out.println(presenter.formatUpdateSuccess());
        } catch (EntityNotFoundException e) {
            System.out.println(presenter.formatUpdateFailed(e.getId()));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void searchActivities() {
        System.out.println(presenter.formatTitle("Mencari Kegiatan"));
        String keyword = InputUtil.readLine("Kata Kunci (x Jika Batal) : ");
        if (InputUtil.isCancel(keyword)) {
            return;
        }
        try {
            List<Activity> result = useCase.searchByTitle(keyword);
            System.out.println(presenter.formatSearchResult(keyword, result));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private void sortActivities() {
        System.out.println(presenter.formatTitle("Mengurutkan Kegiatan"));
        System.out.println(presenter.formatSortMenu());
        String choice = InputUtil.readChoice("Pilih : ");
        if (InputUtil.isCancel(choice)) {
            return;
        }
        SortOption option = toSortOption(choice);
        if (option == null) {
            System.out.println(presenter.formatInvalidSort());
            return;
        }
        System.out.println(presenter.formatSortedList(useCase.getSortedActivities(option)));
    }

    private void deleteActivity() {
        System.out.println(presenter.formatTitle("Menghapus Kegiatan"));
        String rawId = InputUtil.readLine("[ID Kegiatan] yang dihapus (x Jika Batal) : ");
        if (InputUtil.isCancel(rawId)) {
            return;
        }
        try {
            int id = parseId(rawId);
            useCase.deleteActivity(id);
            System.out.println(presenter.formatDeleteSuccess());
        } catch (EntityNotFoundException e) {
            System.out.println(presenter.formatDeleteFailed(e.getId()));
        } catch (ValidationException e) {
            System.out.println(presenter.formatError(e.getMessage()));
        }
    }

    private SortOption toSortOption(String choice) {
        switch (choice) {
            case "1":
                return SortOption.DAY;
            case "2":
                return SortOption.TIME;
            case "3":
                return SortOption.TITLE_ASC;
            case "4":
                return SortOption.TITLE_DESC;
            default:
                return null;
        }
    }

    private int parseId(String raw) throws ValidationException {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new ValidationException("ID tidak valid!");
        }
    }
}

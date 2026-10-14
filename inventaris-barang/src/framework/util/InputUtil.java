package framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utilitas pembacaan input keyboard.
 */
public final class InputUtil {

    public static final String CANCEL = "x";

    private static final BufferedReader READER =
            new BufferedReader(new InputStreamReader(System.in));

    private InputUtil() {
    }

    /** Menampilkan prompt (tanpa baris baru) lalu membaca satu baris yang sudah di-trim. */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        try {
            String line = READER.readLine();
            return line == null ? CANCEL : line.trim(); // EOF dianggap batal/keluar
        } catch (IOException e) {
            return CANCEL;
        }
    }

    /**
     * Membaca pilihan menu. Setelah pilihan dibaca, baris baru dicetak sebagai
     * pengganti tombol Enter agar output tetap rapi saat input dialirkan (piped).
     */
    public static String readChoice(String prompt) {
        String choice = readLine(prompt);
        System.out.println();
        return choice;
    }

    public static boolean isCancel(String input) {
        return CANCEL.equalsIgnoreCase(input);
    }
}

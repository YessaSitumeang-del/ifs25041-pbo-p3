package framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Utilitas pembacaan input keyboard. */
public final class InputUtil {
    private static final String CANCEL = "x";
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private InputUtil() {
    }

    /**
     * Menampilkan prompt lalu membaca satu baris (sudah di-trim).
     * Jika input habis (EOF) atau gagal dibaca, dikembalikan "x" agar aplikasi berhenti dengan rapi.
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            String line = READER.readLine();
            return line == null ? CANCEL : line.trim();
        } catch (IOException e) {
            return CANCEL;
        }
    }

    /** @return true jika input adalah perintah batal/keluar ("x", tidak peka huruf besar/kecil) */
    public static boolean isCancel(String input) {
        return CANCEL.equalsIgnoreCase(input);
    }
}

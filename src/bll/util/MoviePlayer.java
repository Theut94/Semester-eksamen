package bll.util;
import java.io.IOException;

public class MoviePlayer {
    /**
     * Opens the selected movie in Windows Media Player
     * @param filePath - the path of the movie to be played
     */
    public static void playMovie (String filePath)
    {
        String command = "C:\\Program Files\\Windows Media Player\\wmplayer.exe";
        String arg = filePath;
        ProcessBuilder builder = new ProcessBuilder(command, arg);
        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

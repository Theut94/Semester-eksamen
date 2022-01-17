package bll.util;
import java.io.IOException;

public class MoviePlayer {
    public static void main() throws IOException {
        String command = "C:\\Program Files\\Windows Media Player\\wmplayer.exe";
        String arg = "C:\\Users\\n0m1z\\Documents\\GitHub\\Semester-eksamen\\Movies\\Congratulations.mp4";
        //Building a process
        ProcessBuilder builder = new ProcessBuilder(command, arg);
        System.out.println("Executing the external program . . . . . . . .");
        //Starting the process
        builder.start();
    }
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

import java.io.File;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class Ls {
    public static void main(String[] args) {
        if (args.length > 6) throw new IllegalArgumentException("Wrong input");
        boolean l = false;
        boolean h = false;
        boolean r = false;
        boolean o = false;

        File directory = new File(args[args.length - 1]);
        boolean dir = directory.isDirectory();

        for (String arg : args) { // Переопределение флагов
            if (arg.equals("-l")) l = true;
            if (arg.equals("-h")) h = true;
            if (arg.equals("-r")) r = true;
            if (arg.equals("-o")) {
                o = true;
                String outPutFile = args[args.length - 2];
            }
        }
    }

    private String getAccess(File file, boolean h) {
        String access = "";
        if (h) {
            if (file.canRead()) {
                access += 1;
            } else {
                access += 0;
            }
            if (file.canWrite()) {
                access += 1;
            } else {
                access += 0;
            }
            if (file.canExecute()) {
                access += 1;
            } else {
                access += 0;
            }
        }
        else {
            if (file.canRead()) {
                access += 'r';
            } else {
                access += '-';
            }
            if (file.canWrite()) {
                access += 'w';
            } else {
                access += '-';
            }
            if (file.canExecute()) {
                access += 'x';
            } else {
                access += '-';
            }
        }
        return access;
    }

    private String getTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");
        return sdf.format(file.lastModified());
    }
    private String getLength(File file, boolean h) {
        String result = "";
        if (h) {
            result += file.length() + "B ";
        } else {
            if (file.length() < 1024) {
                result += file.length() + "B ";
            }
            if (file.length() >= 1024 && file.length() < 1048576) {
                result += (file.length() / 1024) + "KB ";
            }
            if (file.length() >= 1048576 && file.length() < 1073741824) {
                result += (file.length() / 1048576) + "MB ";
            }
            if (file.length() >= 1073741824) {
                result += (file.length() / 1073741824) + "GB ";
            }
        }
        return result;
    }

    private String getInfo(File file, boolean h, boolean d, boolean r) {
         String result = "";
            if (d) {
                result += file.getName() + " directory";
            }
            else if (r) {
                result += file.length() + getTime(file) + getAccess(file, h) + file.getName() + " file";
            } else {
                result += file.getName() + " file" + getAccess(file, h) + getTime(file) + file.length();
            }
        return result;
    }

    private static File putToFile(File file, String directory) {
        File newFile = new File(directory, "newFile.txt");

    }

}














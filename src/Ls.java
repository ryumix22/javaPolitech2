import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Ls {
    public static void main(String[] args) {
        if (args.length > 6) throw new IllegalArgumentException("Wrong input");
        boolean l = false;
        boolean h = false;
        boolean r = false;
        boolean o = false;

        File directory = new File(args[args.length - 1]);
        boolean dir = directory.isDirectory();

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-l")) l = true;
            if (args[i].equals("-h")) h = true;
            if (args[i].equals("-r")) r = true;
            if (args[i].equals("-o")) {
                o = true;
                int position = i;
            }
        }

        if (o) {
            String outPutFile = args[args.length - 2];
            if (!outPutFile.equals("-o")) {
                try (BufferedWriter result = new BufferedWriter(new FileWriter(
                        "C:\\Users\\Елена\\IdeaProjects\\javaPolitech2\\textFile\\" + outPutFile
                                + ".txt"))) {
                    ArrayList<String> text = getInfo(directory, l, h, dir, r);
                    result.write(makeString(text));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                throw new IllegalArgumentException("Wrong input");
            }
            } else {
                ArrayList<String> kek = getInfo(directory, l, h, dir, r);
                System.out.println(kek);
            }
    }

    private static ArrayList getFile(File file, boolean dir) {
        ArrayList<File> lisOfFiles = new ArrayList<>();
        if (dir) {
            for (File subFile : file.listFiles()) {
                lisOfFiles.add(subFile);
            }
        } else {
            lisOfFiles.add(file);
        }
        return lisOfFiles;
    }

    private static String getAccess(File file, boolean h) {
        String access = "";
        if (!h) {
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
        } else {
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

    private static String getTime(File file) {
        SimpleDateFormat time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");
        return time.format(file.lastModified());
    }

    private static String getLength(File file, boolean h) {
        String result = "";
        if (!h) {
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

    private static ArrayList<String> getInfo(File file, boolean l, boolean h, boolean dir, boolean r) {
        String result;
        ArrayList<File> listOfFiles = getFile(file, dir);
        ArrayList<String> end = new ArrayList<>();
        if (l) {
            for (File subFile : listOfFiles) {
                if (subFile.isDirectory()) {
                    result =(subFile.getName() + " directory" );
                    end.add(result);
                } else if (r) {
                    result = (getLength(subFile, h) + " " + getTime(subFile) +
                            " " + getAccess(subFile, h) + " " + subFile.getName()  +
                            " file");
                    end.add(result);
                } else {
                    result = (subFile.getName() + " file " + getAccess(subFile, h) + " " +
                            getTime(subFile) + " " +
                            getLength(subFile, h));
                    end.add(result);
                }
            }
        } else {
            for (File subFile : listOfFiles) {
                if (subFile.isDirectory()) {
                    result =(subFile.getName() + " directory" );
                    end.add(result);
                } else {
                    result = (subFile.getName() + " file" );
                    end.add(result);
                }
            }
        }
        return end;
    }
    private static String makeString(ArrayList<String> text) {
        String result = "";
        for (String subString : text) {
            result += subString + "\r\n";
        }
        return result;
    }
}














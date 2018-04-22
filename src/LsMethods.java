import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

 class LsMethods {

    private Flag lFlag = new Flag(false );
    private Flag hFlag = new Flag(false);
    private Flag rFlag = new Flag(false);
    private Flag oFlag = new Flag("", false);

    void mainFunction(String[] args) {
        if (args.length > 6) throw new IllegalArgumentException("Wrong input");

        File directory = new File(args[args.length - 1]);
        boolean dir = directory.isDirectory();

        checkFlags(args);

        if (oFlag.bool) {
            String outPutFile = oFlag.directory;
            if (!outPutFile.equals("-o")) {
                try (BufferedWriter result = new BufferedWriter(new FileWriter( outPutFile + ".txt"))) {
                    ArrayList<String> text = getInfo(directory, lFlag.bool, hFlag.bool, dir, rFlag.bool);
                    result.write(makeSting(text));
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                throw new IllegalArgumentException("Wrong input");
            }
        } else {
            ArrayList<String> result = getInfo(directory, lFlag.bool, hFlag.bool, dir, rFlag.bool);
            System.out.print(result);
        }
    }

    private void checkFlags(String[] args) {
        for (String arg : args) {

            if (arg.equals("-l")) lFlag.bool = true;
            if (arg.equals("-h")) hFlag.bool = true;
            if (arg.equals("-r")) rFlag.bool = true;
            if (arg.equals("-o")) {
                oFlag.bool = true;
                oFlag.directory = args[args.length - 2];
            }
        }
    }

    private ArrayList getFile(File file, boolean dir) {
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

    private String getAccess(File file, boolean h) {
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


    private String getTime(File file) {
        SimpleDateFormat time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss ");
        return time.format(file.lastModified());
    }

    private String getLength(File file, boolean h) {
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

    private ArrayList<String> getInfo(File file, boolean l, boolean h, boolean dir, boolean r) {
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

    private String makeSting(List<String> text) {
        String result = "";
        for (String subString : text) {
            result += subString + "\r\n";
        }
        return result;
    }

    private class Flag {
        private String directory;
        private Boolean bool;

        Flag(String directory, Boolean bool) {
            this.directory = directory;
            this.bool = bool;
        }

        Flag(Boolean bool) {
            this.bool = bool;
        }
    }
}

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;




public class Tests {

    @Test
    void testOutput() throws Exception {
        String[] command = "-l -r -h -o newFile testDirectory".split(" ");
        Ls.main(command);
        String expectString = new String(Files.readAllBytes(Paths.get("expectedText/outputFile.txt")));
        String outputString = new String(Files.readAllBytes(Paths.get("newFile.txt")));
        Assert.assertEquals(expectString, outputString);
    }

    @Test
    void testLong() throws Exception {
        String[] command = "-l -r -h testDirectory".split(" ");
        PrintStream o = new PrintStream(new File("outputFile.txt"));
        System.setOut(o);
        Ls.main(command);
        String actualString = new String(Files.readAllBytes(Paths.get("outputFile.txt")));
        String expectString = new String(Files.readAllBytes(Paths.get("expectedText/expectedString.txt")));

        Assert.assertEquals(expectString, actualString);
    }

    @Test
    void simpleTest() throws Exception {
        String[] command = "testDirectory".split(" ");
        PrintStream o = new PrintStream(new File("simpleOutputFile.txt"));
        System.setOut(o);
        Ls.main(command);
        String actualString = new String(Files.readAllBytes(Paths.get("simpleOutputFile.txt")));
        String expectString = new String(Files.readAllBytes(Paths.get("expectedText/expectedSimpleString.txt")));

        Assert.assertEquals(expectString, actualString);
    }
}

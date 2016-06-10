/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtercdnafile;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rutger
 */
public class FileWriterTest {

    public FileWriterTest() {
    }

    /**
     * Test of writeLine method, of class FileWriter.
     */
    @Test
    public void testWriteLine() throws Exception {
        System.out.println("writeLine");
        String current = new java.io.File(".").getCanonicalPath();
        String newPath = current + "\\test\\filtercdnafile\\testFiles\\testfile2.txt";
        FileWriter instance = new FileWriter();
        instance.OpenFile(newPath);
        String newline = "zeehond";
        instance.writeLine(newline);
        instance.CloseFile();
        Charset charset = Charset.forName("US-ASCII");
        String result = "";
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(newPath), charset)) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (count == 0) {
                    result = line;
                    count += 1;
                }
            }
            String expectedResult = "zeehond";
            assertEquals(expectedResult, result);
            Files.delete(Paths.get(newPath));
        }
    }
    
}

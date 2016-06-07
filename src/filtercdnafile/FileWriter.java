/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtercdnafile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Rutger
 */
public class FileWriter {
    /**
     * instance of BufferedWriter.
     */
    BufferedWriter bw;
    /**
     *
     * @param line
     * @throws java.io.IOException
     */
    public void writeLine(String line) throws IOException {
        bw.write(line + "\n");

    }

    /**
     * opens a file with buffered reader with the given path.
     *
     * @param newFilePath path for the new file.
     */
    public void OpenFile(String newFilePath) {
        File snpInRgeneFile = new File(newFilePath);

        try {
            if (!snpInRgeneFile.exists()) {
                snpInRgeneFile.createNewFile();
            }
            java.io.FileWriter fw = new java.io.FileWriter(snpInRgeneFile.getAbsoluteFile());
            bw = new BufferedWriter(fw);
        } catch (IOException e) {
        }
    }

    /**
     * closes the File.
     *
     * @throws IOException
     */
    public void CloseFile() throws IOException {
        bw.close();
    }
}

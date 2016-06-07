/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtercdnafile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rutger
 */
public class CreateTranscriptList {
    /**
     * a new arraylist to store the transcript id's in.
     */
    private List transcriptList = new ArrayList();
    /**
     * main of this class gives the path to the other definitions.
     * @param proteinFile 
     */
    public CreateTranscriptList(Path proteinFile) {
        readFile(proteinFile);
    }
    /**
     * reads the file and creates a list with the transcript ids.
     */
    private void readFile(Path proteinFile) {
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(proteinFile, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splittedLine = line.split("\t");

                Pattern re = Pattern.compile("(?<=transcript:)ENSRNOT\\d+");
                Matcher m = re.matcher(splittedLine[0]);
                m.find();
                transcriptList.add(m.group());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    /**
     * returns the transcriptList
     * @return 
     */
    public List getTranscriptList() {
        return transcriptList;
    }
}

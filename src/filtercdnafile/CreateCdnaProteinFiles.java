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
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Rutger
 */
public class CreateCdnaProteinFiles {
    /**
     * path to the protein file.
     */
    private Path proteinFile;
    /**
     * path to the new proteinfile.
     */
    private String newFile;
    /**
     * path to the transcript file.
     */
    private Path transcriptFile;
    /**
     * path for the new transcript file.
     */
    private String newTranscriptFile;
    /**
     * the id of the current protein fasta entry
     */
    private String ID = "";
    /**
     * the sequence of the current protein fasta entry
     */
    private String sequence = "";
    /**
     * the id of the current transcript entry
     */
    private String transcriptID = "";
    /**
     * the sequence of the current transcript fasta entry.
     */
    private String transcriptSequence = "";
    /**
     * a hashmap containing a proteins 
     */
    private HashMap proteinList;
    private List transcriptList;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, IOException {
        CreateCdnaProteinFiles filterCDNA = new CreateCdnaProteinFiles();
        filterCDNA.run(args);
    }
    /**
     * the run method, runs all the classes in the project.
     * @param args
     * @throws ParseException
     * @throws IOException 
     */
    public void run(String[] args) throws ParseException, IOException {
        ParseCLI parse = new ParseCLI(args);
        //Setting all the paths.
        proteinFile = parse.getProtPath();
        newFile = parse.getNewFilePath();
        transcriptFile = parse.getTransPath();
        newTranscriptFile = parse.getNewTranscriptFilePath();
        //Running the readProteinFasta method.
        readProteinFasta();
        //Creating the transcript list.
        CreateTranscriptList createTranscriptList = new CreateTranscriptList(Paths.get(newFile));
        transcriptList = createTranscriptList.getTranscriptList();
        //Running the readCDNAFasta method.
        readCDNAFasta();
    }
    /**
     * reads the protein file and writes the lines with the same id as in the proteinList in to
     * a new file.
     * @throws ParseException
     * @throws IOException 
     */
    private void readProteinFasta() throws ParseException, IOException {
        Pattern re = Pattern.compile("(?<=>)ENSRNOP\\d+");
        FileWriter writer = new FileWriter();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(proteinFile, charset)) {
            String line;
            writer.OpenFile(newFile);
            while ((line = reader.readLine()) != null) {
                if (line.contains(">")) {
                    if (ID.isEmpty() == false & sequence.isEmpty() == false) {
                        //Skips the line if it is a  mitochondiral protein. 
                        if (!ID.contains("RGSC3.4:MT")) {
                          writer.writeLine(ID + "\t" + sequence);
                        }
                    }
                    ID = line;
                    sequence = "";

                } else {
                    sequence += line.trim();
                }
            }
            writer.writeLine(ID + "\t" + sequence);
            writer.CloseFile();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
    /**
     * reads the transcript file and writes the lines with the same id as in the transcriptList in to
     * a new file.
     * @throws ParseException
     * @throws IOException 
     */
    private void readCDNAFasta() throws ParseException, IOException {
        Pattern re = Pattern.compile("(?<=>)ENSRNOT\\d+");
        FileWriter writer = new FileWriter();
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(transcriptFile, charset)) {
            String line;
            writer.OpenFile(newTranscriptFile);
            while ((line = reader.readLine()) != null) {
                if (line.contains(">")) {
                    if (transcriptID.isEmpty() == false & transcriptSequence.isEmpty() == false) {
                        Matcher m = re.matcher(transcriptID);
                        m.find();
                        //if the transcript id is in the transcript list
                        //the line is added to a new file.
                        if (transcriptList.contains(m.group())) {
                            writer.writeLine(transcriptID + "\t" + transcriptSequence);
                        }
                    }
                    transcriptID = line;
                    transcriptSequence = "";

                } else {
                    transcriptSequence += line.trim();
                }
            }
            // information of the last line is checked.
            if (transcriptList.contains(transcriptID)) {
                writer.writeLine(transcriptID + "\t" + transcriptSequence);
            }
            writer.CloseFile();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}

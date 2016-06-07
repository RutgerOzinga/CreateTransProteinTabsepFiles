/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtercdnafile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 *
 * @author rutgero
 */
public final class ParseCLI {

    /**
     * stores the path to a snp directory.
     */
    private Path protPath;
    /**
     * stores the path to a snp directory.
     */
    private Path transPath;
    /**
     * stores the path to the new file.
     */
    private String newFilePath;
    /**
     * stores the path to the new file.
     */
    private String newTranscriptFilePath;

    private Pattern re = Pattern.compile("(?<=\\\\|/)\\w+\\.txt");

    /**
     * Rutger Ozinga. ParseCLI Parses the commandline input and is able to
     * return the wanted option and value.
     *
     * @param args are commandline arguments.
     * @throws org.apache.commons.cli.ParseException an exception
     */
    public ParseCLI(final String[] args)
            throws org.apache.commons.cli.ParseException {
        HelpFormatter helpForm = new HelpFormatter();
        Options cliOpt = new Options();
        cliOpt.addOption("h", "help", false, "Displays help");
        cliOpt.addOption("p", true, "Expects a path to a protein fasta file.");
        cliOpt.addOption("t", true, "Expects a path to a transcript fasta file.");
        cliOpt.addOption("nt", true, "Expects a path to place the new tab separated transcript file at.");
        cliOpt.addOption("o", true, "Expects a path to place the new tab separated protein file at");
        if (args.length == 0) {
            helpForm.printHelp("Please enter all the "
                    + "options below. ",
                    cliOpt);
            System.exit(0);
        } else {
            BasicParser parser = new BasicParser();
            CommandLine cliParser = parser.parse(cliOpt, args);
            if (cliParser.getOptions().length < 4) {
                System.out.println("Error : "
                        + "Please enter all options in"
                        + " order for this program to work"
                        + "!\n");
                helpForm.printHelp("Please enter all of the  "
                        + "option ", cliOpt);
                System.exit(0);
            } else {
                if (cliParser.hasOption("h") && cliParser.hasOption(
                        "help")) {
                    helpForm.printHelp("Command Line Help:\n", cliOpt);
                    System.exit(0);
                } else {
                    String snpFileString = cliParser.getOptionValue("p");
                    Path snpPath = Paths.get(snpFileString);
                    if (Files.exists(snpPath)) {
                        setProtPath(snpPath);
                    } else {
                        System.out.println("The entered Path does"
                                + " not exits");
                        helpForm.printHelp("Please enter -p followed by a valid"
                                + " path ", cliOpt);
                        System.exit(0);
                    }
                    String transcriptFileString = cliParser.getOptionValue("t");
                    Path transcriptPath = Paths.get(transcriptFileString);
                    if (Files.exists(transcriptPath)) {
                        setTransPath(transcriptPath);
                    } else {
                        System.out.println("The entered Path does"
                                + " not exits");
                        helpForm.printHelp("Please enter -t followed by a valid"
                                + " path ", cliOpt);
                        System.exit(0);
                    }
                    String newFileString = cliParser.getOptionValue("o");
                    Matcher match = re.matcher(newFileString);
                    String editedFileString = match.replaceAll("");
                    Path newPath = Paths.get(editedFileString);
                    if (Files.exists(newPath)) {
                        setNewFilePath(newFileString);
                    } else {
                        System.out.println("The entered Path does"
                                + " not exits");
                        helpForm.printHelp("Please enter -o followed by a valid"
                                + " path ", cliOpt);
                        System.exit(0);
                    }
                    String newTranscriptFileString = cliParser.getOptionValue("nt");
                    Matcher match2 = re.matcher(newTranscriptFileString);
                    String editedTranscriptFileString = match2.replaceAll("");
                    Path newTranscriptPath = Paths.get(editedTranscriptFileString);
                    if (Files.exists(newTranscriptPath)) {
                        setNewTranscriptFilePath(newTranscriptFileString);
                    } else {
                        System.out.println("The entered Path does"
                                + " not exits");
                        helpForm.printHelp("Please enter -nt followed by a valid"
                                + " path ", cliOpt);
                        System.exit(0);
                    }
                }
            }
        }
    }

    /**
     * gets the protein file path.
     *
     * @return the protein file path
     */
    public Path getProtPath() {
        return protPath;
    }

    /**
     * sets the protein file path.
     *
     * @param newProtPath the file path
     */
    public void setProtPath(final Path newProtPath) {
        this.protPath = newProtPath;
    }
    /**
     * gets the transcriptfile path.
     *
     * @return the transcript file path
     */
    public Path getTransPath() {
        return transPath;
    }

    /**
     * sets the transcript file path.
     *
     * @param newTransPath the file path
     */
    public void setTransPath(final Path newTransPath) {
        this.transPath = newTransPath;
    }

    /**
     * gets the path to the new file.
     *
     * @return newFilePath
     */
    public String getNewFilePath() {
        return newFilePath;
    }

    /**
     * sets the path to the new file.
     *
     * @param fileTranscriptPath the path to the new file.
     */
    public void setNewTranscriptFilePath(final String fileTranscriptPath) {
        this.newTranscriptFilePath = fileTranscriptPath;
    }

    /**
     * gets the path to the new file.
     *
     * @return newTranscriptFilePath
     */
    public String getNewTranscriptFilePath() {
        return newTranscriptFilePath;
    }

    /**
     * sets the path to the new file.
     *
     * @param filePath the path to the new file.
     */
    public void setNewFilePath(final String filePath) {
        this.newFilePath = filePath;
    }
}

/**
 * Created by nick on 12/14/15.
 */

package annotationManager;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class for saving annotations and read errors for image paths.
 */
public class AnnotationRecorder {

    /**
     * A copy of the annotations to save.
     */
    private HashMap<String, HashSet<String>> annotations;

    /**
     * A copy of the read errors to save.
     */
    private HashMap<String, Boolean> readErrors;

    /**
     * Construct an AnnotationRecorder object.
     *
     * @param annotations   The annotations to record.
     * @param readErrors    The read errors to record.
     */
    public AnnotationRecorder(HashMap<String, HashSet<String>> annotations,
                              HashMap<String, Boolean> readErrors) {
        this.annotations = annotations;
        this.readErrors = readErrors;
    }

    /**
     * Save annotations to a csv file.
     *
     * @param outputFile    The filename to write annotations to.
     */
    public void saveToCsv(String outputFile) {

        // before we open the file check to see if it already exists
        boolean alreadyExists = new File(outputFile).exists();

        if (!alreadyExists) {
            writeToCsv(outputFile);
        } else {
            appendToCsv(outputFile);
        }
    }

    /**
     * Generate the union of image file paths in annotations and read errors.
     *
     * @return Set of all image paths.
     */
    private HashSet<String> generateAllPaths() {
        HashSet<String> imagePaths = new HashSet<>();

        for (Map.Entry<String, HashSet<String>> entry : annotations.entrySet()) {
            imagePaths.add(entry.getKey());
        }

        imagePaths.addAll(readErrors.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));

        return imagePaths;
    }

    /**
     * Write a new file for the annotations.
     *
     * @param outputFile    The name of the file to write output to.
     */
    private void writeToCsv(String outputFile) {

        HashSet<String> allPaths = generateAllPaths();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, false), ',');

            for (String path : allPaths) {

                HashSet<String> labels = new HashSet<>();

                if (annotations.get(path) != null) {
                    labels.addAll(annotations.get(path));
                }

                if (readErrors.get(path) == true) {
                    labels.add("ERROR");
                }

                //if we have any information about a given path, write it.
                if (!labels.isEmpty()) {
                    csvOutput.write(path);
                    for (String label : labels) {
                        csvOutput.write(label);
                    }
                    csvOutput.endRecord();
                }
            }

            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append to an existing file for the annotations.
     *
     * @param outputFile    The name of the file to append output to.
     */
    private void appendToCsv(String outputFile) {

        HashSet<String> allPaths = generateAllPaths();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
        } catch (IOException e) {

        }
    }
}

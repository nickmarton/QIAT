/**
 * Created by nick on 12/11/15.
 */

package fileHandler;

import java.io.File;
import java.util.ArrayList;
import java.io.FileFilter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.istack.internal.NotNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * A class designed to scan a directory and extract files within it.
 */
public abstract class FileGrabber {

    /**
     * The root directory which will be recursively scanned for files
     */
    @NotNull
    private final String directory;

    /**
     * The pattern to filter files; should match list of file extensions.
     * If null, all files will be returned.
     */
    private String regex;

    /**
     * Create a default fileHandler.FileGrabber with root directory directory.
     *
     * @param directory The root directory to scan.
     */
    public FileGrabber(String directory) {
        this.directory = directory;
        this.regex = null;
    }

    /**
     * Create a regex fileHandler.FileGrabber with root directory directory.
     *
     * @param directory The root directory to scan.
     * @param regex     The regular expression pattern to use for filtering
     *                  files.
     */
    public FileGrabber(String directory, String regex) {
        this.directory = directory;
        this.regex = regex;
    }

    /**
     * Create a fileHandler.FileGrabber with root directory directory and use regex
     * generated from list of file extensions.
     *
     * @param directory      The root directory to scan.
     * @param fileExtensions A list of file extensions to match
     */
    public FileGrabber(String directory, HashSet<String> fileExtensions) {
        this.directory = directory;

        HashSet<String> parsed = new HashSet<>();
        for (String extension : fileExtensions) {
            //drop '.' if it's at the start of extension
            parsed.add(extension.startsWith(".") ? extension.substring(1) : extension);
        }

        String regex = generateRegex(parsed);
        this.regex = regex;
    }

    /**
     * @return this fileHandler.FileGrabber's directory.
     */
    public String getDirectory() {
        return this.directory;
    }

    /**
     * @return this fileHandler.FileGrabber's directory.
     */
    public String getRegex() {
        return this.regex;
    }

    /**
     * Scan the directory and return path + name of all files matching
     * regex.
     *
     * @return ArrayList of paths + names matching fileHandler.FileGrabber's pattern.
     */
    public ArrayList<String> getFileNames(){
        ArrayList<String> filenames = new ArrayList<>();

        File directory = new File(this.directory);
        List<File> files;
        if (this.regex != null) {
            files = (List<File>) FileUtils.listFiles(
                    directory,
                    //new RegexFileFilter(this.regex),
                    new RegexFileFilter(".*"),
                    TrueFileFilter.INSTANCE);
        }
        else {
            files = (List<File>) FileUtils.listFiles(
                    directory,
                    new RegexFileFilter(".*"),
                    TrueFileFilter.INSTANCE);
        }

        filenames.addAll(files.stream().map(file -> file.getAbsoluteFile().toString()).collect(Collectors.toList()));

        return filenames;
    }

    public String getExtension(String filePath){
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i+1);
        }
        return extension;
    }

    /**
     * Set regex member to new string.
     *
     * @param regex The string to replace this FileGrabber's regex member.
     */
    protected void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     *
     * @param fileExtensions The new set of file extensions to be converted to
     *                       a regex.
     */
    protected void setRegex(HashSet<String> fileExtensions) {
        this.regex = generateRegex(fileExtensions);
    }

    /**
     * Generate a regex from an ArrayList of file extensions.
     */
    private String generateRegex(HashSet<String> fileExtensions) {
        StringBuilder regex = new StringBuilder();

        regex.append("(");
        String generalName = "[a-zA-Z0-9_]*";
        for (String extension : fileExtensions) {
            regex.append(generalName);
            regex.append(extension);
            regex.append("|");
        }
        regex.deleteCharAt(regex.length() - 1);
        regex.append(")");

        return regex.toString();
    }

}
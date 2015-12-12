/**
 * Created by nick on 12/11/15.
 */

package fileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A class to scan a directory and extract image files within it.
 */
public class ImageFileGrabber extends FileGrabber {

    /**
     * Construct an ImageFileGrabber object with default extensions.
     *
     * @param directory         The root directory to scan.
     */
    public ImageFileGrabber(String directory) {
        super(directory);
        this.setRegex(getDefaultExtensions());
    }

    /**
     * Construct an ImageFileGrabber object.
     *
     * @param directory         The root directory to scan.
     * @param imageExtensions   A set of image filename extensions.
     */
    public ImageFileGrabber(String directory, HashSet<String> imageExtensions) {
        super(directory, imageExtensions);
    }

    /**
     * Try to read ImageFileTypes.txt and set default extensions.
     *
     * @return defaultExtensions The set of default Image file types supported.
     */
    private HashSet<String> getDefaultExtensions() {
        HashSet<String> defaultExtensions = new HashSet<>();

        try {
            File file = new File("src/fileHandler/ImageFileTypes.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = br.readLine()) != null) {
                defaultExtensions.add(line);
            }
        }
        catch (IOException e) {
            //resorting to basic types
            String[] defaultTypes = {".jpg", ".jpeg", ".png", ".tif"};
            for (String type : defaultTypes) {
                    defaultExtensions.add(type);
            }
        }

        return defaultExtensions;
    }

    public static void main(String[] args) {
        HashSet<String> a = new HashSet<>();

        ImageFileGrabber ifg;
        ifg = new ImageFileGrabber("TestImages");
        ArrayList<String> files = ifg.getFileNames();
        System.out.println(files.get(0));
    }
}

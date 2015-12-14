/**
 * Created by nick on 12/14/15.
 */

package annotationManager;

import java.util.HashMap;
import java.util.HashSet;

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
    public AnnotationRecorder(HashMap<String,HashSet<String>> annotations,
                              HashMap<String, Boolean> readErrors) {

    }

}

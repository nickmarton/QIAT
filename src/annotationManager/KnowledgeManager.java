/**
 * Created by nick on 12/13/15.
 */

package annotationManager;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A class for handling the annotation knowledge.
 */
public class KnowledgeManager {

    /**
     * A HashMap for variable length annotations, i.e. a set of labels per image.
     */
    private HashMap<String, HashSet<String>> annotations = new HashMap<>();

    /**
     * If we are currently taking 1D or ND annotations.
     */
    private boolean variableLength;

    /**
     * Default constructor; initialized to 1D.
     */
    public KnowledgeManager() {
        variableLength = false;
    }

    /**
     * Constructor with option for annotation type.
     *
     * @param variableLength The flag for whether or not to use 1D or ND.
     */
    public KnowledgeManager(boolean variableLength) {
        this.variableLength = true;
    }

    /**
     * Add an annotation to the key provided by imagePath.
     *
     * @param imagePath The key for the annotations HashMap.
     * @param label     The label to add to the image.
     */
    public void addAnnotation(String imagePath, String label) {
        if (annotations.get(imagePath) == null) {
            HashSet<String> emptyLabels = new HashSet();
            annotations.put(imagePath, emptyLabels);
        }
        annotations.get(imagePath).add(label);
    }

    /**
     * Drop all knowledge about image with path imagePath.
     *
     * @param imagePath The identifier of the image whose knowledge will be
     *                  dropped.
     */
    public void dropAnnotations(String imagePath) {
        annotations.remove(imagePath);
    }

    /**
     * Clear all annotations.
     */
    public void clearAnnotations() {
        annotations.clear();
    }

    /**
     * Determine if Knowledge Manager is currently taking 1D or ND annotations.
     *
     * @return variableLength.
     */
    public boolean isVariableLength() {
        return variableLength;
    }

    /**
     * Set variableLength member.
     *
     * @param variableLength The boolean for whether or not to treat as 1D or ND.
     */
    public void setVariableLength(boolean variableLength) {
        this.variableLength = variableLength;
    }

    /**
     * Return annotations member.
     *
     * @return annotations HashMap.
     */
    public HashMap<String, HashSet<String>> getAnnotations() {
        return annotations;
    }
}

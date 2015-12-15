/**
 * Created by nick on 12/12/15.
 */

package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import annotationManager.AnnotationRecorder;
import com.csvreader.CsvWriter;

import annotationManager.AnnotationManager;


/**
 * A class for displaying statistics for the ongoing annotation.
 */
public class StatPanel extends JPanel {

    /**
     * The dimension of the panel.
     */
    private Dimension dimension;

    /**
     * The KnowledgeManager to use for annotation settings adjustments.
     */
    private AnnotationManager annotationManager;

    /**
     * The ImagePanel to use for IOException error logging.
     */
    private ImagePanel imagePanel;

    /**
     * The KeyBindingField to use for settings.
     */
    private KeyBindingPanel keyBindingPanel;

    /**
     * Construct a StatPanel object.
     *
     * @param statPanelDimension The space allotted to the Panel.
     */
    public StatPanel(Dimension statPanelDimension,
                     AnnotationManager knowledgeManager,
                     ImagePanel imagePanel,
                     KeyBindingPanel keyBindingPanel) {

        dimension = statPanelDimension;
        this.annotationManager = knowledgeManager;
        this.imagePanel = imagePanel;
        this.keyBindingPanel = keyBindingPanel;

        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        setBorder(loweredbevel);

        setPreferredSize(statPanelDimension);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        addSettingsColumn(constraints);

    }

    /**
     * Add the multi-label checkbox.
     *
     * @param constraints The GridBagLAyout constraints to use.
     */
    private void addMultiLabelCheckBox(GridBagConstraints constraints, int row) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        JCheckBox labelCheckBox = new JCheckBox("Multi-label");
        labelCheckBox.addActionListener(e -> {
            if (annotationManager.isVariableLength()) {
                annotationManager.setVariableLength(false);
            } else {
                annotationManager.setVariableLength(true);
            }
        });
        add(labelCheckBox, constraints);
    }

    /**
     * Add the save button.
     *
     * @param constraints The GridBagLayout constraints to use.
     * @param row         The row to place the save button on.
     */
    private void addSaveButton(GridBagConstraints constraints, int row) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton saveButton = new JButton("   SAVE   ");
        saveButton.addActionListener(e -> {
            doSave();
        });

        savePanel.add(saveButton);

        add(savePanel, constraints);
    }

    /**
     * Add the clear button.
     *
     * @param constraints The GridBagLayout constraints to use.
     * @param row         The row to place the save button on.
     */
    private void addClearButton(GridBagConstraints constraints, int row) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton clearButton = new JButton("  CLEAR  ");
        clearButton.addActionListener(e -> {
            doClear();
        });

        clearPanel.add(clearButton);

        add(clearPanel, constraints);
    }

    /**
     * Add multi-label checkbox, save, and clear button, then pad the
     * remainder of the column.
     *
     * @param constraints The GridBagLayout constraints to use.
     */
    private void addSettingsColumn(GridBagConstraints constraints) {
        addMultiLabelCheckBox(constraints, 0);
        addSaveButton(constraints, 1);
        addClearButton(constraints, 2);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(Box.createRigidArea(new Dimension(0, 50)), constraints);
    }

    /**
     * Perform saving action.
     */
    private void doSave() {

        AnnotationRecorder recorder = new AnnotationRecorder(
                annotationManager.getAnnotations(),
                imagePanel.getReadErrors());

        JFileChooser saveFile = new JFileChooser();
        saveFile.showSaveDialog(null);

        //Wait until a valid CSV filename is chosen before saving.
        while (true) {
            try {
                if (isValidFileName(saveFile)) {
                    recorder.saveToCsv(saveFile.getSelectedFile().getAbsolutePath());
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid csv file");
                    saveFile = new JFileChooser();
                    saveFile.showSaveDialog(null);
                    if (saveFile == null) {
                        break;
                    }
                }
            }
            //if user cancels without ever trying a file.
            catch (NullPointerException ex) {
                break;
            }
        }
    }

    /**
     * Perform clear on KnowledgeManager, ImagePanel, and KeyBindingPanel.
     */
    private void doClear() {
        annotationManager.clearAnnotations();
        imagePanel.reset();
        keyBindingPanel.removeAll();
        keyBindingPanel.initPanel();
        keyBindingPanel.clearRegisteredBindings();
        keyBindingPanel.revalidate();
    }

    /**
     * Determine if a file name is a valid csv file.
     * @param chosenfile    The fileChooser object to check.
     * @return              The boolean for whether or not valid csv.
     */
    private boolean isValidFileName(JFileChooser chosenfile) {

        String chosenFileName = chosenfile.getSelectedFile().getName();
        String chosenFilePath = chosenfile.getSelectedFile().getAbsolutePath();
        String extension = chosenFilePath.substring(chosenFilePath.length() - 4);

        if (extension.equals(".csv") && chosenFileName.length() > 4) {
            return true;
        } else {
            return false;
        }
    }
}

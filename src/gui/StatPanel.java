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
     * @param statPanelDimension    The space allotted to the Panel.
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
     * @param constraints   The GridBagLAyout constraints to use.
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
     * @param constraints   The GridBagLayout constraints to use.
     * @param row           The row to place the save button on.
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
     * @param constraints   The GridBagLayout constraints to use.
     * @param row           The row to place the save button on.
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
     * @param constraints   The GridBagLayout constraints to use.
     */
    private void addSettingsColumn(GridBagConstraints constraints) {
        addMultiLabelCheckBox(constraints, 0);
        addSaveButton(constraints, 1);
        addClearButton(constraints, 2);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(Box.createRigidArea(new Dimension(0,50)), constraints);
    }

    /**
     * Perform saving action.
     */
    private void doSave() {

        System.out.println(annotationManager.getAnnotations());
        System.out.println(imagePanel.getReadErrors());

        String outputFile = "Annotations.csv";

        // before we open the file check to see if it already exists
        boolean alreadyExists = new File(outputFile).exists();

        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            // if the file didn't already exist then we need to write out the header line
            if (!alreadyExists)
            {
                csvOutput.write("id");
                csvOutput.write("name");
                csvOutput.endRecord();
            }
            // else assume that the file already has the correct header line

            csvOutput.write("2");
            csvOutput.write("John");
            csvOutput.endRecord();

            csvOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
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
}

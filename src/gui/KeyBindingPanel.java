/**
 * Created by nick on 12/12/15.
 */

package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;

/**
 * A class for the key bindings used during annotation.
 */
public class KeyBindingPanel extends JPanel{

    public HashMap<String, JLabel> labels = new HashMap<>();

    /**
     * Next available index of GridBagLayout.
     */
    private int rowIndex = 0;

    /**
     * Construct a KeyBindingPanel object.
     */
    public KeyBindingPanel() {

        Border loweredbevel;
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        setBorder(loweredbevel);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Explicitly add labels for columns in layout
        JLabel keyLabel = new JLabel("key:");
        keyLabel.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = rowIndex + 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(keyLabel, constraints);

        JLabel labelLabel = new JLabel("label:");
        labelLabel.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 1;
        constraints.gridy = rowIndex + 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(labelLabel, constraints);
        rowIndex++;

        String [] alphanumLabels = {"1", "2", "3", "4", "5", "6", "7", "8",
                                    "9", "A", "B", "C", "D", "E", "F", "G",
                                    "H", "I", "J", "K", "L", "M", "N", "O",
                                    "P", "Q", "R", "S", "T", "U", "V", "W",
                                    "X", "Y", "Z"};

        for (String label : alphanumLabels) {
            addRow(label, constraints);
        }

    }

    /**
     * Add a new row to the key binding panel with label provided.
     *
     * @param key           The label of the key to bind.
     * @param constraints   The GridBagConstraints.
     */
    public void addRow(String key, GridBagConstraints constraints) {

        JLabel keyLabel = new JLabel(key);
        keyLabel.setPreferredSize(new Dimension(50, 20));
        keyLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel labelLabel = new JLabel();
        labelLabel.setPreferredSize(new Dimension(200, 10));
        labelLabel.setHorizontalAlignment(JLabel.CENTER);

        labels.put(key, labelLabel);

        //set position on GridBagLayout
        constraints.gridx = 0;
        constraints.gridy = rowIndex + 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(keyLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = rowIndex + 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(labelLabel, constraints);
        rowIndex++;
    }
}

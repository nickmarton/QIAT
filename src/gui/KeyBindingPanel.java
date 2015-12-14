/**
 * Created by nick on 12/12/15.
 */

package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * A class for the key bindings used during annotation.
 */
public class KeyBindingPanel extends JPanel{

    /**
     * A map of the keys to their respective JLabels.
     */
    public HashMap<String, JLabel> labels = new HashMap<>();

    /**
     * A map of key strings to their respective KeyEvent constants
     */
    private HashMap<String, Integer> keyCodes = new HashMap<>();

    /**
     * Next available index of GridBagLayout.
     */
    private int rowIndex = 0;

    /**
     * The ImagePanel to which this KeyBindingPanel corresponds to.
     */
    private ImagePanel imagePanel;

    /**
     * Should the keybindings respond to the keys being pressed.
     */
    private boolean respondToKeyBindings = false;

    /**
     * Construct a KeyBindingPanel object.
     */
    public KeyBindingPanel(ImagePanel imagePanel) {

        this.imagePanel = imagePanel;

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

        initKeyCodes();
        bindArrowKeys();
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

    /**
     * Register a new keybinding with given key.
     *
     * @param key   The key to register the binding to.
     * @param label The label used for annotation when the key is pressed.
     */
    public void addKeyBinding(String key, String label) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCodes.get(key), 0, false), key);
        getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (respondToKeyBindings) {
                    String imagePath = imagePanel.getImagePath();
                    imagePanel.nextImage();
                }
            }
        });
    }

    public void setRespondToKeyBindings(boolean respond) {
        respondToKeyBindings = respond;
    }

    /**
     *
     */
    private void bindArrowKeys() {

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "LeftArrow");
        getActionMap().put("LeftArrow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                imagePanel.prevImage();
            }
        });
    }

    /**
     * Populate HashMap of keyCodes.
     */
    private void initKeyCodes() {
        String [] alphanumLabels = {"1", "2", "3", "4", "5", "6", "7", "8",
                                    "9", "0", "A", "B", "C", "D", "E", "F",
                                    "G", "H", "I", "J", "K", "L", "M", "N",
                                    "O", "P", "Q", "R", "S", "T", "U", "V",
                                    "W", "X", "Y", "Z"};

        int [] keyCodes =  {KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3,
                            KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6,
                            KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9,
                            KeyEvent.VK_0, KeyEvent.VK_A, KeyEvent.VK_B,
                            KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E,
                            KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H,
                            KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K,
                            KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N,
                            KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q,
                            KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T,
                            KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W,
                            KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z};

        for (int i=0; i<36; i++) {
            this.keyCodes.put(alphanumLabels[i], keyCodes[i]);
        }
    }
}

/**
 * Created by nick on 12/13/15.
 */

package gui;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * A class for adding keybindings to the KeyBindingPanel.
 */
public class AdderPanel extends JPanel {

    /**
     * The field from which to extract the key in keybinding.
     */
    private JTextField keyField;

    /**
     * The label to bind to the key.
     */
    private JTextField labelField;

    /**
     * The KeyBindingPanel object to which to add to.
     */
    private KeyBindingPanel keyBindingPanel;

    /**
     * Class for adding key bindings to KeyBindingPanel.
     */
    public AdderPanel(KeyBindingPanel keyBindingPanel) {

        this.keyBindingPanel = keyBindingPanel;

        Border loweredbevel;
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        setBorder(loweredbevel);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton addButton = new JButton("Add keybinding");
        addButton.setPreferredSize(new Dimension(50, 25));
        addButton.addActionListener(e -> commitLabel());

        JLabel keyLabel = new JLabel("Bind to key:");
        keyLabel.setPreferredSize(new Dimension(100, 25));
        keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(200, 25));
        addFocusListener(keyField);

        JLabel labelLabel = new JLabel("label to add:");
        labelLabel.setPreferredSize(new Dimension(100, 25));
        labelField = new JTextField();
        labelField.setPreferredSize(new Dimension(200, 25));
        addFocusListener(labelField);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(addButton, constraints);

        add(Box.createRigidArea(new Dimension(250,0)));

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(keyLabel);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(keyField);

        add(Box.createRigidArea(new Dimension(250,0)));

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(labelLabel);

        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(labelField);

    }

    /**
     * Respond to keybinding button being pressed.
     */
    private void commitLabel() {
        if (!keyField.getText().equals("") && !labelField.getText().equals("")) {

            String key = keyField.getText();
            String label = labelField.getText();

            try {
                JLabel newLabel = keyBindingPanel.labels.get(key);
                newLabel.setText(label);
                keyBindingPanel.labels.put(key, newLabel);
                keyBindingPanel.addKeyBinding(key, label);

            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Invalid key");
            }

            keyField.setText("");
            labelField.setText("");
        }
    }

    /**
     * Add a FocusListener to the textfield so keybindings don't respond while
     * textField has focus.
     *
     * @param textField The JTextField to add the listener to.
     */
    private void addFocusListener(JTextField textField) {
        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                keyBindingPanel.setRespondToKeyBindings(false);
            }

            @Override
            public void focusLost(FocusEvent e) {
                keyBindingPanel.setRespondToKeyBindings(true);
            }
        });
    }

}

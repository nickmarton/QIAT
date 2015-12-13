/**
 * Created by nick on 12/13/15.
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.Border;

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
     * Class for adding key bindings to KeyBindingPanel.
     */
    public AdderPanel() {

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

        JLabel labelLabel = new JLabel("label to add:");
        labelLabel.setPreferredSize(new Dimension(100, 25));
        labelField = new JTextField();
        labelField.setPreferredSize(new Dimension(200, 25));

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
        add(labelLabel);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(labelField);

        add(Box.createRigidArea(new Dimension(250,0)));

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(keyLabel);

        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        add(keyField);

    }

    /**
     * Respond to keybinding button being pressed.
     */
    private void commitLabel() {
        if (!keyField.getText().equals("") && !labelField.getText().equals("")) {
            System.out.println("woooo");
            keyField.setText("");
            labelField.setText("");
        }

        //gui.BoundKeyListener listener = new gui.BoundKeyListener(key);
        //this.addKeyListener(listener);
        //setFocusable(true);
    }

    /**
     *
     */
    public class BoundKeyListener implements KeyListener {

        private String key;

        public BoundKeyListener(String key) {
            this.key = key;
        }

        @Override
        public void keyTyped(KeyEvent e) {
            System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
        }
    }
}

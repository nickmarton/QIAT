/**
 * Created by nick on 12/12/15.
 */

package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * A class for displaying statistics for the ongoing annotation.
 */
public class StatPanel extends JPanel {

    /**
     * Construct a StatPanel object.
     *
     * @param statPanelDimension
     */
    public StatPanel(Dimension statPanelDimension) {
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        setBorder(loweredbevel);

        setPreferredSize(statPanelDimension);

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Statistics:");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    }
}

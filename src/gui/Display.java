package gui;

import java.awt.*;
import javax.swing.*;

/**
 * Created by nick on 12/12/15.
 */
public class Display extends JFrame{

    private Dimension frameDimension = new Dimension(1300,850);
    private Dimension imagePanelDimension = percentsToDimension(.75, .75);
    private Dimension statPanelDimension = percentsToDimension(.25, .10);

    public Display() {
        initUI();
    }

    private Dimension percentsToDimension(double heightPercent, double widthPercent) {
        int totalHeight = this.frameDimension.height;
        int totalWidth = this.frameDimension.width;

        double scaledHeight = heightPercent * totalHeight;
        double scaledWidth = widthPercent * totalWidth;

        return new Dimension((int)scaledHeight, (int)scaledWidth);
    }

    private JPanel getImagePanel() {
        JPanel imagePanel = new ImagePanel(this.imagePanelDimension);
        imagePanel.setBackground(new Color(216, 228, 232));
        return imagePanel;
    }

    private JScrollPane getKeyBindingPanel() {
        JPanel keysPanel = new KeyBindingPanel();
        JScrollPane scrollPane = new JScrollPane(keysPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private JScrollPane getStatPanel() {
        JPanel statPanel = new StatPanel(this.statPanelDimension);
        JScrollPane scrollPane = new JScrollPane(statPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    public final void initUI() {

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");

        menubar.add(file);
        setJMenuBar(menubar);

        JScrollPane keyBindingPanel = getKeyBindingPanel();
        JPanel imagePanel = getImagePanel();
        JScrollPane statPanel = getStatPanel();

        add(keyBindingPanel, BorderLayout.WEST);
        add(imagePanel, BorderLayout.CENTER);
        add(statPanel, BorderLayout.SOUTH);

        setSize(this.frameDimension);
        setTitle("Quick Annotation Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Display ex = new Display();
                ex.setVisible(true);
            }
        });
    }

}

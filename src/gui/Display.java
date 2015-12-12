package gui;

import java.awt.*;
import javax.swing.*;

/**
 * Created by nick on 12/12/15.
 */
public class Display extends JFrame{

    private Dimension frameDimension = new Dimension(1300,850);
    private Dimension imagePanelDimension = percentsToDimension(.65, .65);
    private Dimension westPanelDimension = percentsToDimension(.15, .10);
    private Dimension southPanelDimension = percentsToDimension(.10, .15);

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
        imagePanel.setBackground(new Color(216,228,232));
        return imagePanel;
    }

    private JPanel getWestPanel() {
        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.GRAY);
        westPanel.setPreferredSize(this.westPanelDimension);
        return westPanel;
    }

    private JPanel getSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setPreferredSize(this.southPanelDimension);
        return southPanel;
    }

    public final void initUI() {

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");

        menubar.add(file);
        setJMenuBar(menubar);

        JPanel westPanel = getWestPanel();
        JPanel imagePanel = getImagePanel();
        JPanel southPanel = getSouthPanel();

        add(westPanel, BorderLayout.WEST);
        add(imagePanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);


        setSize(this.frameDimension);
        setTitle("BorderLayout");
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

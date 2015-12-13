/**
 * Created by nick on 12/12/15.
 */

package gui;

import java.awt.*;
import javax.swing.*;

/**
 * Main GUI class for annotator.
 */
public class Display extends JFrame{

    /**
     * The preferred initial size of the main frame.
     */
    private Dimension frameDimension = new Dimension(1300,850);

    /**
     * The preferred initial size of the ImagePanel.
     */
    private Dimension imagePanelDimension = percentsToDimension(.75, .75);

    /**
     * The preferred initial size of the StatPanel.
     */
    private Dimension statPanelDimension = percentsToDimension(.25, .10);

    /**
     * The KeyBindingPanel of the frame.
     */
    private KeyBindingPanel keyBindingPanel;

    /**
     * The ImagePanel of the frame.
     */
    private ImagePanel imagePanel;

    /**
     * The StatPanel of the frame.
     */
    private StatPanel statPanel;

    /**
     * The JPanel of the frame for adding labels.
     */
    private AdderPanel adderPanel;

    /**
     * Construct the GUI.
     */
    public Display() {
        initUI();
    }

    /**
     * Find dimension based off of some percentage of the frame's dimension.
     *
     * @param heightPercent The percentage of the Frame's height to use.
     * @param widthPercent  The percentage of the Frame's width to use.
     * @return              A new dimension object reflecting the percentage
     *                      asked for.
     */
    private Dimension percentsToDimension(double heightPercent, double widthPercent) {
        int totalHeight = this.frameDimension.height;
        int totalWidth = this.frameDimension.width;

        double scaledHeight = heightPercent * totalHeight;
        double scaledWidth = widthPercent * totalWidth;

        return new Dimension((int)scaledHeight, (int)scaledWidth);
    }

    /**
     * Construct an ImagePanel for the frame.
     *
     * @return A new ImagePanel object.
     */
    private ImagePanel makeImagePanel() {
        ImagePanel imagePanel = new ImagePanel(this.imagePanelDimension);
        imagePanel.setBackground(new Color(216, 228, 232));
        return imagePanel;
    }

    /**
     * Construct a KeyBindingPanel for the frame.
     *
     * @return A new KeyBindingPanel wrapped in a JScrollPane..
     */
    private JScrollPane makeKeyBindingPanel() {
        keyBindingPanel = new KeyBindingPanel();
        JScrollPane scrollPane = new JScrollPane(
                                    keyBindingPanel,
                                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    /**
     * Construct a StatPanel for the frame.
     *
     * @return A new StatPanel wrapped in a JScrollPane..
     */
    private JScrollPane makeStatPanel() {
        statPanel = new StatPanel(this.statPanelDimension);
        JScrollPane scrollPane = new JScrollPane(
                                    statPanel,
                                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    /**
     * Construct an AdderPanel for the frame.
     *
     * @return A new AdderPanel wrapped in a JScrollPane.
     */
    private JScrollPane makeAdderPanel(KeyBindingPanel keyBindingPanel) {
        adderPanel = new AdderPanel(keyBindingPanel);
        JScrollPane scrollPane = new JScrollPane(
                                    adderPanel,
                                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    /**
     * Initialize the GUI.
     */
    public final void initUI() {

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");

        menubar.add(file);
        setJMenuBar(menubar);

        JScrollPane keyBindingScroll = makeKeyBindingPanel();
        imagePanel = makeImagePanel();
        JScrollPane statScroll = makeStatPanel();
        JScrollPane adderScroll = makeAdderPanel(keyBindingPanel);

        add(keyBindingScroll, BorderLayout.WEST);
        add(imagePanel, BorderLayout.CENTER);
        add(statScroll, BorderLayout.SOUTH);
        add(adderScroll, BorderLayout.NORTH);

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

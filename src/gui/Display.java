/**
 * Created by nick on 12/12/15.
 */

package gui;

import annotationManager.KnowledgeManager;

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
     * The KnowledgeManager to use for recording annotations.
     */
    private KnowledgeManager knowledgeManager;

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
     * Initialize the GUI.
     */
    public final void initUI() {

        //setJMenuBar(makeMenuBar());

        knowledgeManager = new KnowledgeManager();

        imagePanel = makeImagePanel();
        JScrollPane keyBindingScroll = makeKeyBindingPanel(imagePanel, knowledgeManager);
        JScrollPane adderScroll = makeAdderPanel(keyBindingPanel);
        JScrollPane statScroll = makeStatPanel(knowledgeManager, imagePanel, keyBindingPanel);

        add(imagePanel, BorderLayout.CENTER);
        add(keyBindingScroll, BorderLayout.WEST);
        add(adderScroll, BorderLayout.NORTH);
        add(statScroll, BorderLayout.SOUTH);

        setSize(this.frameDimension);
        setTitle("Quick Annotation Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Construct the JMenuBar to use in display.
     * @return JMenuBar.
     */
    private JMenuBar makeMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");

        menubar.add(file);

        return menubar;
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
     * @param imagePanel
     */
    private JScrollPane makeKeyBindingPanel(ImagePanel imagePanel, KnowledgeManager knowledgeManager) {
        keyBindingPanel = new KeyBindingPanel(imagePanel, knowledgeManager);
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
    private JScrollPane makeStatPanel(KnowledgeManager knowledgeManager,
                                      ImagePanel imagePanel,
                                      KeyBindingPanel keyBindingPanel) {

        statPanel = new StatPanel(this.statPanelDimension,
                                    knowledgeManager,
                                    imagePanel,
                                    keyBindingPanel);

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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Display ex = new Display();
                ex.setVisible(true);
            }
        });
    }

}

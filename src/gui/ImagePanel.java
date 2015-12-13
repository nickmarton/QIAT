/**
 * Created by nick on 12/12/15.
 */

package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import fileHandler.ImageFileGrabber;

public class ImagePanel extends JPanel{

    /**
     * The image currently being displayed in the panel.
     */
    private BufferedImage image;

    /**
     * The total dimension available to the panel.
     */
    private Dimension dimension;

    /**
     * The ImageGrabber obejct to use.
     */
    private final ImageFileGrabber grabber = new ImageFileGrabber("./TestImages");

    /**
     * A set of file extensions to be read using jpeg codec.
     */
    private static final Set<String> JPEG_TYPES = new HashSet<>(Arrays.asList(
            new String[]{"jpg", "jpeg", ".tif"}
    ));

    /**
     * The current file index for the FileGrabber.
     */
    private int fileIndex = 2;

    /**
     * Create an ImagePanel object.
     *
     * @param imagePanelDimension The total dimension available to the panel.
     */
    public ImagePanel(Dimension imagePanelDimension){

        this.dimension = imagePanelDimension;

        int height = imagePanelDimension.height;
        int width = imagePanelDimension.width;

        ArrayList<String> filePaths = grabber.getFileNames();

        //keep trying to read a file until a readable one is found initially.
        while (true) {
            try {
                String filePath = filePaths.get(fileIndex);
                image = readImage(filePath);
                image = getScaledImage(image, height, width);
                break;
            } catch (IOException ex) {
                fileIndex++;
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("No images found.");
                throw ex;
            }
        }

    }

    /**
     * Do Affine Transform with width width and height height on an image.
     *
     * @param image         The image to be scaled.
     * @param width         The width to use in Affine Transform.
     * @param height        The height to use in Affine Transform.
     * @return              New scaled version of image.
     * @throws IOException
     */
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(
                image,
                new BufferedImage(width, height, image.getType()));
    }

    /**
     * Paint the component.
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2d.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
        g2d.drawImage(image, 0, 0, null);
    }

    /**
     * Try to perform a read from a given file path.
     *
     * @param filePath       The filepath from which to read.
     * @return               BufferedImage object if possible.
     * @throws IOException   If problem is encountered during read.
     */
    private BufferedImage readImage(String filePath) throws IOException{
        String extension = grabber.getExtension(filePath);

        if (JPEG_TYPES.contains(extension)) {
            JPEGImageDecoder jpegDecoder =  JPEGCodec.createJPEGDecoder(new FileInputStream(filePath));
            return jpegDecoder.decodeAsBufferedImage();
        }
        else {
            return ImageIO.read(new File(filePath));
        }
    }
}

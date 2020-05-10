/**
 * GifSequenceWriter.java 
 * 
 * Created by Elliot Kroo, April 25, 2009
 * Modified by Ian Martinez, May 2020
 * 
 * This work is licensed under the Creative Commons Attribution 3.0 Unported
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
 * Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 */

package giflib;

import javax.imageio.*;
import java.util.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;
import org.w3c.dom.*;
import java.awt.*;

public class GifSequenceWriter {
    protected ImageWriter gifWriter;
    protected ImageWriteParam imageWriteParam;
    protected IIOMetadata imageMetaData;

    /**
     * Creates a new GifSequenceWriter
     *
     * @param outputStream the ImageOutputStream to be written to
     * @param imageType one of the imageTypes specified in BufferedImage
     * @param timeBetweenFramesMS the time between frames in milliseconds
     * @param loopContinuously whether the GIF should loop repeatedly
     * @throws IIOException if no GIF ImageWriters are found
     *
     * @author Elliot Kroo (elliot[at]kroo[dot]net)
     */
    public GifSequenceWriter(ImageOutputStream outputStream, 
            int imageType,
            int timeBetweenFramesMS, 
            boolean loopContinuously) throws IIOException, IOException {
        
        // my method to create a writer
        gifWriter = getWriter();
        imageWriteParam = gifWriter.getDefaultWriteParam();
        var imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
        imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier, imageWriteParam);

        var metaFormatName = imageMetaData.getNativeMetadataFormatName();
        var root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);
        var gceNode = getNode(root, "GraphicControlExtension");
        gceNode.setAttribute("disposalMethod", "none");
        gceNode.setAttribute("userInputFlag", "FALSE");
        gceNode.setAttribute("transparentColorFlag", "FALSE");
        gceNode.setAttribute("delayTime", Integer.toString(timeBetweenFramesMS / 10));
        gceNode.setAttribute("transparentColorIndex", "0");

        var commentsNode = getNode(root, "CommentExtensions");
        commentsNode.setAttribute("CommentExtension", "Created by MAH");
        
        var appExtensionsNode = getNode(root, "ApplicationExtensions");
        var child = new IIOMetadataNode("ApplicationExtension");

        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");

        int loop = loopContinuously ? 0 : 1;

        child.setUserObject(new byte[]{0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF)});
        appExtensionsNode.appendChild(child);

        imageMetaData.setFromTree(metaFormatName, root);
        gifWriter.setOutput(outputStream);
        gifWriter.prepareWriteSequence(null);
    }

    public void writeToSequence(RenderedImage img) throws IOException {
        var ioImg = new IIOImage(img, null, imageMetaData);
        gifWriter.writeToSequence(ioImg, imageWriteParam);
    }

    /**
     * Close this GifSequenceWriter object. This does not close the underlying
     * stream, just finishes off the GIF.
     */
    public void close() throws IOException {
        gifWriter.endWriteSequence();
    }

    /**
     * Returns the first available GIF ImageWriter using
     * ImageIO.getImageWritersBySuffix("gif").
     *
     * @return a GIF ImageWriter object
     * @throws IIOException if no GIF image writers are returned
     */
    private static ImageWriter getWriter() throws IIOException {
        Iterator<ImageWriter> iter = ImageIO.getImageWritersBySuffix("gif");
        if (!iter.hasNext()) {
            throw new IIOException("No GIF Image Writers Exist");
        } else {
            return iter.next();
        }
    }

    /**
     * Returns an existing child node, or creates and returns a new child node
     * (if the requested node does not exist).
     *
     * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child
     * node.
     * @param nodeName the name of the child node.
     *
     * @return the child node, if found or a new node created with the given
     * name.
     */
    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)
                    == 0) {
                return ((IIOMetadataNode) rootNode.item(i));
            }
        }
        
        var node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return (node);
    }

    public static GifFrame[] readGif(String source) {
        try {
            InputStream stream = new FileInputStream(source);
            ArrayList<GifFrame> frames = new ArrayList<>(2);

            ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
            reader.setInput(ImageIO.createImageInputStream(stream));

            int lastx = 0;
            int lasty = 0;

            int width = -1;
            int height = -1;

            IIOMetadata metadata = reader.getStreamMetadata();

            Color backgroundColor = null;

            if (metadata != null) {
                var globalRoot = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());
                var globalColorTable = globalRoot.getElementsByTagName("GlobalColorTable");
                var globalScreeDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");

                if (globalScreeDescriptor != null && globalScreeDescriptor.getLength() > 0) {
                    IIOMetadataNode screenDescriptor = (IIOMetadataNode) globalScreeDescriptor.item(0);

                    if (screenDescriptor != null) {
                        width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
                        height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
                    }
                }

                if (globalColorTable != null && globalColorTable.getLength() > 0) {
                    var colorTable = (IIOMetadataNode) globalColorTable.item(0);

                    if (colorTable != null) {
                        String bgIndex = colorTable.getAttribute("backgroundColorIndex");
                        var colorEntry = (IIOMetadataNode) colorTable.getFirstChild();
                        
                        while (colorEntry != null) {
                            if (colorEntry.getAttribute("index").equals(bgIndex)) {
                                int red = Integer.parseInt(colorEntry.getAttribute("red"));
                                int green = Integer.parseInt(colorEntry.getAttribute("green"));
                                int blue = Integer.parseInt(colorEntry.getAttribute("blue"));

                                backgroundColor = new Color(red, green, blue);
                                break;
                            }

                            colorEntry = (IIOMetadataNode) colorEntry.getNextSibling();
                        }
                    }
                }
            }

            BufferedImage master = null;
            boolean hasBackround = false;

            for (int frameIndex = 0;; frameIndex++) {
                BufferedImage image;
                
                try {
                    image = reader.read(frameIndex);
                } catch (IndexOutOfBoundsException io) {
                    break;
                }

                if (width == -1 || height == -1) {
                    width = image.getWidth();
                    height = image.getHeight();
                }

                var root = (IIOMetadataNode) reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
                var gceNode = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
                var children = root.getChildNodes();
                int delay = Integer.valueOf(gceNode.getAttribute("delayTime"));
                String disposal = gceNode.getAttribute("disposalMethod");

                if (master == null) {
                    master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                    master.createGraphics().setColor(backgroundColor);
                    master.createGraphics().fillRect(0, 0, master.getWidth(), master.getHeight());

                    hasBackround = image.getWidth() == width && image.getHeight() == height;

                    master.createGraphics().drawImage(image, 0, 0, null);
                } else {
                    int x = 0;
                    int y = 0;

                    for (int nodeIndex = 0; nodeIndex < children.getLength(); nodeIndex++) {
                        Node nodeItem = children.item(nodeIndex);

                        if (nodeItem.getNodeName().equals("ImageDescriptor")) {
                            NamedNodeMap map = nodeItem.getAttributes();

                            x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
                            y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
                        }
                    }

                    if (disposal.equals("restoreToPrevious")) {
                        BufferedImage from = null;
                        for (int i = frameIndex - 1; i >= 0; i--) {
                            if (!frames.get(i).getDisposal().equals("restoreToPrevious") || frameIndex == 0) {
                                from = frames.get(i).getImage();
                                break;
                            }
                        }

                        ColorModel model = from.getColorModel();
                        boolean alpha = from.isAlphaPremultiplied();
                        WritableRaster raster = from.copyData(null);
                        master = new BufferedImage(model, raster, alpha, null);
                    } else if (disposal.equals("restoreToBackgroundColor") && backgroundColor != null) {
                        if (!hasBackround || frameIndex > 1) {
                            master.createGraphics().fillRect(lastx, lasty, frames.get(frameIndex - 1).getWidth(), frames.get(frameIndex - 1).getHeight());
                        }
                    }
                    master.createGraphics().drawImage(image, x, y, null);

                    lastx = x;
                    lasty = y;
                }

                BufferedImage copy;
                ColorModel model = master.getColorModel();
                boolean alpha = master.isAlphaPremultiplied();
                WritableRaster raster = master.copyData(null);
                copy = new BufferedImage(model, raster, alpha, null);
                frames.add(new GifFrame(copy, delay, disposal, image.getWidth(), image.getHeight()));
                master.flush();
            }
            reader.dispose();

            GifFrame[] fs = new GifFrame[frames.size()];
            for (int i = 0; i < frames.size(); i++) {
                fs[i] = frames.get(i);
            }
            return fs;
        } catch (Exception ex) {
            return null;
        }
    }

    public static BufferedImage[] getFrames(String file) {
        GifFrame[] f = readGif(file);
        BufferedImage[] bf = new BufferedImage[f.length];
        
        for (int i = 0; i < f.length; i++) {
            bf[i] = f[i].getImage();
        }

        return bf;
    }

    public static int getAverageDelay(String file) {
        GifFrame[] frames = readGif(file);
        int total = 0;
        
        for (GifFrame frame : frames) {
            total += frame.getDelay();
        }

        return (int) (total / frames.length);
    }
}

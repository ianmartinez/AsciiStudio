// 
//  GifSequenceWriter.java
//  
//  Created by Elliot Kroo on 2009-04-25.
//
// This work is licensed under the Creative Commons Attribution 3.0 Unported
// License. To view a copy of this license, visit
// http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative
// Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.

package ascii_studio;
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
   * @param loopContinuously weather the gif should loop repeatedly
   * @throws IIOException if no gif ImageWriters are found
   *
   * @author Elliot Kroo (elliot[at]kroo[dot]net)
   */
  public GifSequenceWriter(
      ImageOutputStream outputStream,
      int imageType,
      int timeBetweenFramesMS,
      boolean loopContinuously) throws IIOException, IOException {
    // my method to create a writer
    gifWriter = getWriter(); 
    imageWriteParam = gifWriter.getDefaultWriteParam();
    ImageTypeSpecifier imageTypeSpecifier =
      ImageTypeSpecifier.createFromBufferedImageType(imageType);

    imageMetaData =
      gifWriter.getDefaultImageMetadata(imageTypeSpecifier,
      imageWriteParam);

    String metaFormatName = imageMetaData.getNativeMetadataFormatName();

    IIOMetadataNode root = (IIOMetadataNode)
      imageMetaData.getAsTree(metaFormatName);

    IIOMetadataNode graphicsControlExtensionNode = getNode(
      root,
      "GraphicControlExtension");

    graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
    graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
    graphicsControlExtensionNode.setAttribute(
      "transparentColorFlag",
      "FALSE");
    graphicsControlExtensionNode.setAttribute(
      "delayTime",
      Integer.toString(timeBetweenFramesMS / 10));
    graphicsControlExtensionNode.setAttribute(
      "transparentColorIndex",
      "0");

    IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
    commentsNode.setAttribute("CommentExtension", "Created by MAH");

    IIOMetadataNode appEntensionsNode = getNode(
      root,
      "ApplicationExtensions");

    IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

    child.setAttribute("applicationID", "NETSCAPE");
    child.setAttribute("authenticationCode", "2.0");

    int loop = loopContinuously ? 0 : 1;

    child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte)
      ((loop >> 8) & 0xFF)});
    appEntensionsNode.appendChild(child);

    imageMetaData.setFromTree(metaFormatName, root);

    gifWriter.setOutput(outputStream);

    gifWriter.prepareWriteSequence(null);
  }
  
  public void writeToSequence(RenderedImage img) throws IOException {
    gifWriter.writeToSequence(
      new IIOImage(
        img,
        null,
        imageMetaData),
      imageWriteParam);
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
    if(!iter.hasNext()) {
      throw new IIOException("No GIF Image Writers Exist");
    } else {
      return iter.next();
    }
  }

  /**
   * Returns an existing child node, or creates and returns a new child node (if 
   * the requested node does not exist).
   * 
   * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child node.
   * @param nodeName the name of the child node.
   * 
   * @return the child node, if found or a new node created with the given name.
   */
  private static IIOMetadataNode getNode(
      IIOMetadataNode rootNode,
      String nodeName) {
    int nNodes = rootNode.getLength();
    for (int i = 0; i < nNodes; i++) {
      if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)
          == 0) {
        return((IIOMetadataNode) rootNode.item(i));
      }
    }
    IIOMetadataNode node = new IIOMetadataNode(nodeName);
    rootNode.appendChild(node);
    return(node);
  }
  
  /**
  public GifSequenceWriter(
       BufferedOutputStream outputStream,
       int imageType,
       int timeBetweenFramesMS,
       boolean loopContinuously) {
   
   */
  
  public static void main(String[] args) throws Exception {
    if (args.length > 1) {
      // grab the output image type from the first image in the sequence
      BufferedImage firstImage = ImageIO.read(new File(args[0]));

      // create a new BufferedOutputStream with the last argument
      ImageOutputStream output = 
        new FileImageOutputStream(new File(args[args.length - 1]));
      
      // create a gif sequence with the type of the first image, 1 second
      // between frames, which loops continuously
      GifSequenceWriter writer = 
        new GifSequenceWriter(output, firstImage.getType(), 1, false);
      
      // write out the first image to our sequence...
      writer.writeToSequence(firstImage);
      for(int i=1; i<args.length-1; i++) {
        BufferedImage nextImage = ImageIO.read(new File(args[i]));
        writer.writeToSequence(nextImage);
      }
      
      writer.close();
      output.close();
    } else {
      System.out.println(
        "Usage: java GifSequenceWriter [list of gif files] [output file]");
    }
  }
  
  
  public static ImageFrame[] readGif(String source) {
  	try{
		InputStream stream = new FileInputStream(source);
		ArrayList<ImageFrame> frames = new ArrayList<ImageFrame>(2);
		
		ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
		reader.setInput(ImageIO.createImageInputStream(stream));
		
		int lastx = 0;
		int lasty = 0;
		
		int width = -1;
		int height = -1;
		
		IIOMetadata metadata = reader.getStreamMetadata();
		
		Color backgroundColor = null;
		
		if(metadata != null) {
			IIOMetadataNode globalRoot = (IIOMetadataNode) metadata.getAsTree(metadata.getNativeMetadataFormatName());
		
			NodeList globalColorTable = globalRoot.getElementsByTagName("GlobalColorTable");
			NodeList globalScreeDescriptor = globalRoot.getElementsByTagName("LogicalScreenDescriptor");
		
			if (globalScreeDescriptor != null && globalScreeDescriptor.getLength() > 0){
				IIOMetadataNode screenDescriptor = (IIOMetadataNode) globalScreeDescriptor.item(0);
		
				if (screenDescriptor != null){
					width = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenWidth"));
					height = Integer.parseInt(screenDescriptor.getAttribute("logicalScreenHeight"));
				}
			}
		
			if (globalColorTable != null && globalColorTable.getLength() > 0){
				IIOMetadataNode colorTable = (IIOMetadataNode) globalColorTable.item(0);
		
				if (colorTable != null) {
					String bgIndex = colorTable.getAttribute("backgroundColorIndex");
		
					IIOMetadataNode colorEntry = (IIOMetadataNode) colorTable.getFirstChild();
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
			try{
				image = reader.read(frameIndex);
			}catch (IndexOutOfBoundsException io){
				break;
			}
		
			if (width == -1 || height == -1){
				width = image.getWidth();
				height = image.getHeight();
			}
		
			IIOMetadataNode root = (IIOMetadataNode) reader.getImageMetadata(frameIndex).getAsTree("javax_imageio_gif_image_1.0");
			IIOMetadataNode gce = (IIOMetadataNode) root.getElementsByTagName("GraphicControlExtension").item(0);
			NodeList children = root.getChildNodes();
		
			int delay = Integer.valueOf(gce.getAttribute("delayTime"));
		
			String disposal = gce.getAttribute("disposalMethod");
		
			if (master == null){
				master = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				master.createGraphics().setColor(backgroundColor);
				master.createGraphics().fillRect(0, 0, master.getWidth(), master.getHeight());
		
			hasBackround = image.getWidth() == width && image.getHeight() == height;
		
				master.createGraphics().drawImage(image, 0, 0, null);
			}else{
				int x = 0;
				int y = 0;
		
				for (int nodeIndex = 0; nodeIndex < children.getLength(); nodeIndex++){
					Node nodeItem = children.item(nodeIndex);
		
					if (nodeItem.getNodeName().equals("ImageDescriptor")){
						NamedNodeMap map = nodeItem.getAttributes();
		
						x = Integer.valueOf(map.getNamedItem("imageLeftPosition").getNodeValue());
						y = Integer.valueOf(map.getNamedItem("imageTopPosition").getNodeValue());
					}
				}
		
				if (disposal.equals("restoreToPrevious")){
					BufferedImage from = null;
					for (int i = frameIndex - 1; i >= 0; i--){
						if (!frames.get(i).getDisposal().equals("restoreToPrevious") || frameIndex == 0){
							from = frames.get(i).getImage();
							break;
						}
					}
		
					{
						ColorModel model = from.getColorModel();
						boolean alpha = from.isAlphaPremultiplied();
						WritableRaster raster = from.copyData(null);
						master = new BufferedImage(model, raster, alpha, null);
					}
				}else if (disposal.equals("restoreToBackgroundColor") && backgroundColor != null){
					if (!hasBackround || frameIndex > 1){
						master.createGraphics().fillRect(lastx, lasty, frames.get(frameIndex - 1).getWidth(), frames.get(frameIndex - 1).getHeight());
					}
				}
				master.createGraphics().drawImage(image, x, y, null);
		
				lastx = x;
				lasty = y;
			}
		
			{
				BufferedImage copy;
		
				{
					ColorModel model = master.getColorModel();
					boolean alpha = master.isAlphaPremultiplied();
					WritableRaster raster = master.copyData(null);
					copy = new BufferedImage(model, raster, alpha, null);
				}
				frames.add(new ImageFrame(copy, delay, disposal, image.getWidth(), image.getHeight()));
			}
		
			master.flush();
		}
		reader.dispose();
		
		ImageFrame[] fs = new ImageFrame[frames.size()];
		for(int i=0;i<frames.size();i++) 
			fs[i]=frames.get(i);
		return fs;
		
  	} catch (Exception ex) {return null;}
  }
  
  public static BufferedImage[] getFrames(String file) {
  	ImageFrame[] f = readGif(file);
  	BufferedImage[] bf = new BufferedImage[f.length];
  	for(int i = 0;i<f.length;i++)
  		bf[i] = f[i].getImage();
  		
  	return bf;
  }
  
  public static int getDelay(String file) {
  	ImageFrame[] f = readGif(file);
  	int total = 0;
  	for(int i = 0;i<f.length;i++)
  		total += f[i].getDelay();
  	
  	return (int)(total/f.length);
  }
}



class ImageFrame {
    private final int delay;
    private final BufferedImage image;
    private final String disposal;
    private final int width, height;

    public ImageFrame (BufferedImage image, int delay, String disposal, int width, int height){
        this.image = image;
        this.delay = delay;
        this.disposal = disposal;
        this.width = width;
        this.height = height;
    }

    public ImageFrame (BufferedImage image){
        this.image = image;
        this.delay = -1;
        this.disposal = null;
        this.width = -1;
        this.height = -1;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getDelay() {
        return delay;
    }

    public String getDisposal() {
        return disposal;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
            return height;
    }
}
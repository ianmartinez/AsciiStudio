package ascii_studio;

import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.UIManager.*;
import java.text.DecimalFormat;
import javax.swing.filechooser.*;
import java.awt.event.*;

public class MainWindow extends javax.swing.JFrame 
{
    ResourceLoader rs = new ResourceLoader();
    public MainWindow() 
    {
        initComponents();
        setUI(origUI);
        origTitle = this.getTitle();
        try 
        {
            this.setIconImage(rs.getResourceImage("32.png"));
        } catch (Exception ex) {printEx(ex);}
    	updateTitle();
        
        
        this.setSize(new Dimension(1024, 750));
    }
    
    public void printEx(Exception ex) 
    {
    	System.out.println("Error:");
    	System.out.println("\tMake sure you opened a file\r\n");
    	System.out.println("Stack Trace:");
    	ex.printStackTrace();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() 
    {
        tbMain = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnSaveText = new javax.swing.JButton();
        btnSaveImage = new javax.swing.JButton();
        btnSavePhraseImage = new javax.swing.JButton();
        btnSaveImageMatrix = new javax.swing.JButton();
        jSeparator0 = new javax.swing.JToolBar.Separator();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnPreview = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnSettings = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPreview = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ASCII Studio 3.0");

        tbMain.setFloatable(false);
        tbMain.setRollover(true);

        btnOpen.setIcon(rs.getResourceIcon("open.png")); 
        btnOpen.setToolTipText("Open Image");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnOpen);
        btnOpen.getAccessibleContext().setAccessibleDescription("");

        tbMain.add(jSeparator0);

        btnSaveText.setIcon(rs.getResourceIcon("save text.png")); 
        btnSaveText.setToolTipText("Save Text");
        btnSaveText.setFocusable(false);
        btnSaveText.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveText.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnSaveText);
        btnSaveText.getAccessibleContext().setAccessibleDescription("");

        btnSaveImage.setIcon(rs.getResourceIcon("save image.png")); 
        btnSaveImage.setToolTipText("Save Image");
        btnSaveImage.setFocusable(false);
        btnSaveImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnSaveImage);
        btnSaveImage.getAccessibleContext().setAccessibleDescription("");
        
        btnSavePhraseImage.setIcon(rs.getResourceIcon("phrase.png")); 
        btnSavePhraseImage.setToolTipText("Save Image Using Phrase");
        btnSavePhraseImage.setFocusable(false);
        btnSavePhraseImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSavePhraseImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnSavePhraseImage);
        btnSavePhraseImage.getAccessibleContext().setAccessibleDescription("");
        
        btnSaveImageMatrix.setIcon(rs.getResourceIcon("image matrix.png")); 
        btnSaveImageMatrix.setToolTipText("Save Image Matrix");
        btnSaveImageMatrix.setFocusable(false);
        btnSaveImageMatrix.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveImageMatrix.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        //tbMain.add(btnSaveImageMatrix);
        btnSaveImageMatrix.getAccessibleContext().setAccessibleDescription("");

        tbMain.add(jSeparator1);

        btnZoomIn.setIcon(rs.getResourceIcon("zoom in.png")); 
        btnZoomIn.setToolTipText("Zoom In");
        btnZoomIn.setFocusable(false);
        btnZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnZoomIn);

        btnZoomOut.setIcon(rs.getResourceIcon("zoom out.png")); 
        btnZoomOut.setToolTipText("Zoom Out");
        btnZoomOut.setFocusable(false);
        btnZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnZoomOut);
        tbMain.add(jSeparator3);

        btnPreview.setIcon(rs.getResourceIcon("preview.png")); 
        btnPreview.setToolTipText("Preview ASCII");
        btnPreview.setFocusable(false);
        btnPreview.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreview.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnPreview);
        btnPreview.getAccessibleContext().setAccessibleDescription("");

        tbMain.add(jSeparator2);
        
        btnSettings.setIcon(rs.getResourceIcon("settings.png")); 
        btnSettings.setFocusable(false);
        btnSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSettings.setToolTipText("Converter Palette");
        btnSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnSettings);

        btnAbout.setIcon(rs.getResourceIcon("about.png")); 
        btnAbout.setToolTipText("About");
        btnAbout.setFocusable(false);
        btnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbMain.add(btnAbout);
        btnAbout.getAccessibleContext().setAccessibleDescription("");

        txtPreview.setBorder(null);
        txtPreview.setDoubleBuffered(true);
        jScrollPane1.setViewportView(txtPreview);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbMain, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );
		
		
		this.setPreferredSize(new Dimension(890,750));
        pack();
        
    	// Add event handlers
    	btnOpen.addActionListener((ActionEvent evt) ->  btnOpenActionPerformed(evt));
    	btnSaveText.addActionListener((ActionEvent evt) -> btnSaveTextActionPerformed(evt));
    	btnSaveImage.addActionListener((ActionEvent evt) -> btnSaveImageActionPerformed(evt));
    	btnSavePhraseImage.addActionListener((ActionEvent evt) -> btnSavePhraseImageActionPerformed(evt));
    	btnSaveImageMatrix.addActionListener((ActionEvent evt) -> btnSaveImageMatrixActionPerformed(evt));
    	btnZoomOut.addActionListener((ActionEvent evt) -> btnZoomOutActionPerformed(evt));
    	btnZoomIn.addActionListener((ActionEvent evt) -> btnZoomInActionPerformed(evt));
    	btnPreview.addActionListener((ActionEvent evt) -> btnPreviewActionPerformed(evt));
    	btnSettings.addActionListener((ActionEvent evt) -> btnSettingsActionPerformed(evt));
    	btnAbout.addActionListener((ActionEvent evt) -> btnAboutActionPerformed(evt));
    }
    
    public static void main(String args[]) 
    {
    	
     	try 
     	{
            System.out.println("--->\tASCII Converter 2.0 by Ian Martinez\t<---");
            System.out.print("Available UIs:\t");
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
                System.out.print(info.getName() + "; ");
            System.out.println();

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
            {
                if (origUI.equals(info.getName())) 
                {
                    System.out.println("Using " + origUI);
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }	

            System.out.println("");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnSaveImage;
    private javax.swing.JButton btnSavePhraseImage;
    private javax.swing.JButton btnSaveImageMatrix;
    private javax.swing.JButton btnSaveText;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator0;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar tbMain;
    private javax.swing.JEditorPane txtPreview;
        
    // end netbeans code
    
    public void setUI(String name) 
    {
        try 
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
            {
                if (name.equals(info.getName())) 
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }	
        } catch (Exception ex) {printEx(ex);}
    }
	
    public ASCII a = new ASCII();
    public String sourcePath = "";
    public Font font = new Font("Consolas",Font.BOLD,12);
    public String origTitle;
    public static String origUI = "Windows";
    
    
    public void openProcess(String doc) 
    {
    	try 
    	{
            Desktop dt = Desktop.getDesktop();
            dt.open(new File(doc));
        } catch (Exception ex) {printEx(ex);}
    }
    
    public String getFileName(String path) 
    {
        int slash = path.lastIndexOf("\\");
        return path.substring(slash + 1);
    }	

    public String getEXT(String path) 
    {
        int dot = path.lastIndexOf(".");
        return path.substring(dot + 1).toLowerCase();
    }

    public String removeEXT(String path) 
    {
        if (path.equals("")) 
            return "";
        
        int dot = path.lastIndexOf(".");
        return path.substring(0,dot);
    }

    public String replaceEXT(String path, String ext) 
    {
        return removeEXT(path) + "." + ext;
    }
  	
    public void waitCur() 
    {
    	this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    
    public void defCur()
    {
    	this.setCursor(Cursor.getDefaultCursor());
    }
    
   	
    private Gif fetchGif(String path) 
    {
        return new Gif(path);
    }

    private BufferedImage fetchImage(String path) 
    {
        try 
        {
            return ImageIO.read(new File(path));   			
        } catch (Exception ex) {
            printEx(ex);
            return null;
        }
    }

    private void updateTitle() 
    {
        if (sourcePath.equals(""))
            this.setTitle(origTitle);
        else
            this.setTitle(origTitle + " - " + getFileName(sourcePath));
    }

    private void updateTitleWithFrame(String outPath, int pos, int maxPos) 
    {
        this.setTitle(origTitle + " - " + getFileName(outPath) + " - Rendering frame " + (pos+1) + " of " + maxPos);
    }


    /*	
    *	I can't save JPEGs because of a 15 year old(!!!) bug 
    *	in the JDK that throws off JPEG colors and whose fix 
    *	is over 700 lines of code that shouldn't exist
    */
	
	
    public FileNameExtensionFilter imgOpenFilter = new FileNameExtensionFilter("Image Files(*.jpeg, *.jpg, *.gif, *.png)", "jpeg", "jpg", "gif", "png");
    public FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Image Files(*.gif, *.png)", "gif", "png");
    public FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files(*.txt)", "txt");
    
    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	if (font.getSize() < Integer.MAX_VALUE) 
    	{
            font = new Font("Consolas", 0, font.getSize()+1);
            txtPreview.setFont(font);
    	}
    }
    
    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	if (font.getSize() > 1) 
    	{
            font = new Font("Consolas", 0, font.getSize()-1);
            txtPreview.setFont(font);
    	}
    }
    
    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	JFileChooser dlgOpen = new JFileChooser();
    	dlgOpen.addChoosableFileFilter(imgOpenFilter);
    	dlgOpen.setFileFilter(imgOpenFilter);
    	
    	if (dlgOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
            sourcePath = dlgOpen.getSelectedFile().getAbsolutePath();
    	
    	updateTitle();
    }
    
   private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) 
    {    	
    	try 
    	{
            if(sourcePath.equals(""))
            {
                System.out.println("Open a source image before continuing.");
                return;
            }

            waitCur();
            BufferedImage img = fetchImage(sourcePath);
            String ascii = a.getText(img);

            defCur();
            txtPreview.setText(ascii);
            txtPreview.setFont(font);
    	} catch (Exception ex) {
            defCur();
            printEx(ex);
    	}
    }
	
    private void btnSaveTextActionPerformed(java.awt.event.ActionEvent evt) 
    {
        try 
        {
            waitCur();
            JFileChooser dlgSave = new JFileChooser();
            
            dlgSave.addChoosableFileFilter(txtFilter);
            dlgSave.setFileFilter(txtFilter);
            dlgSave.setCurrentDirectory(new File(sourcePath));

        if (dlgSave.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
        {
            long startTime = System.nanoTime();
            BufferedImage img = fetchImage(sourcePath);
            String ascii = a.getText(img);
            
            PrintWriter out = new PrintWriter(dlgSave.getSelectedFile().getAbsolutePath());
            out.println(ascii);
            out.close();

            openProcess(dlgSave.getSelectedFile().getAbsolutePath());

            long endTime = System.nanoTime();
            System.out.println("Time elapsed:\t" + (((endTime - startTime)/1000000)/1000) + " seconds");
        }

        defCur();
        } catch(Exception ex) {
            defCur();
            printEx(ex);
        }	
    }
    
    private boolean savePhraseClicked =false;
    private void btnSaveImageActionPerformed(java.awt.event.ActionEvent evt)
    {
        try 
        {
            waitCur();
            if(!savePhraseClicked) a.SetUsingPhrase(false);
            JFileChooser dlgSave = new JFileChooser();
            dlgSave.addChoosableFileFilter(imgFilter);
            dlgSave.setFileFilter(imgFilter);
            dlgSave.setCurrentDirectory(new File(sourcePath));

            if (!sourcePath.equals("")) 
                dlgSave.setSelectedFile(new File(removeEXT(sourcePath) + " ASCII." + getEXT(sourcePath)));

            if (dlgSave.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
            {
                long startTime = System.nanoTime();
                String outPath = dlgSave.getSelectedFile().getAbsolutePath();
                int pixels = 0;

                // Java can't save JPEGs(without destroying them), because that would make too much sense
                if (getEXT(outPath).equals("jpg") || getEXT(outPath).equals("jpeg"))
                    outPath = replaceEXT(outPath,"png");

                if (getEXT(sourcePath).equals("gif") && getEXT(outPath).equals("gif")) 
                { 
                    Gif g = fetchGif(sourcePath);
                    pixels = g.getFrame(0).getHeight() * g.getFrame(0).getWidth() * g.getFrameCount();
                    
                    Gif g2 = new Gif(new BufferedImage[g.getFrameCount()],g.getDelay());
                    System.out.println("There are " + g.getFrameCount() + " frames");

                    for (int f=0;f<g.getFrameCount();f++) 
                    {
                        updateTitleWithFrame(outPath,f,g.getFrameCount());
                        System.out.println("Processing frame " + (f+1) + "/" + g.getFrameCount());
                        BufferedImage render = a.getFullImage(g.getFrame(f));
                        g2.setFrame(f,render);
                    }

                    System.out.println("Saving file...");
                    g2.save(outPath);
                } else {
                    BufferedImage img  = fetchImage(sourcePath);
                    pixels = img.getHeight() * img.getWidth();

                    File outFile = new File(outPath);
                    BufferedImage render = a.getFullImage(img);

                    System.out.println("Saving file...");
                    ImageIO.write(render, getEXT(outPath), outFile);
                }


                System.out.println("File saved");
                long endTime = System.nanoTime();
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###");
                
                String numberAsString = decimalFormat.format(pixels);
                System.out.println("Time elapsed: " + (((endTime - startTime)/1000000)/1000) + " seconds");
                System.out.println("Pixels processed: " + numberAsString);
                openProcess(outPath);
            }

            defCur();
            updateTitle();
        } catch(Exception ex) {
            defCur();
            printEx(ex);
        }
    }
    
    private void btnSavePhraseImageActionPerformed(java.awt.event.ActionEvent evt) 
    {
        a.SetUsingPhrase(true);
        savePhraseClicked = true;
        btnSaveImageActionPerformed(evt);
        a.SetUsingPhrase(false);
        savePhraseClicked = false;
    }
    
    private void btnSaveImageMatrixActionPerformed(java.awt.event.ActionEvent evt) 
    {
        try 
        {
            waitCur();
            JFileChooser dlgSave = new JFileChooser();
            dlgSave.addChoosableFileFilter(imgFilter);
            dlgSave.setFileFilter(imgFilter);
            dlgSave.setCurrentDirectory(new File(sourcePath));

            if (!sourcePath.equals("")) 
                dlgSave.setSelectedFile(new File(removeEXT(sourcePath) + " ASCII." + getEXT(sourcePath)));

            if (dlgSave.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
            {
                long startTime = System.nanoTime();
                String outPath = dlgSave.getSelectedFile().getAbsolutePath();
                int pixels = 0;

                // Java can't save JPEGs(without destroying them), because that would make too much sense
                if (getEXT(outPath).equals("jpg") || getEXT(outPath).equals("jpeg"))
                    outPath = replaceEXT(outPath,"png");

                if (getEXT(sourcePath).equals("gif") && getEXT(outPath).equals("gif")) 
                { 
                    Gif g = fetchGif(sourcePath);
                    pixels = g.getFrame(0).getHeight() * g.getFrame(0).getWidth() * g.getFrameCount();
                    
                    Gif g2 = new Gif(new BufferedImage[g.getFrameCount()],g.getDelay());
                    System.out.println("There are " + g.getFrameCount() + " frames");

                    for (int f=0;f<g.getFrameCount();f++) 
                    {
                        updateTitleWithFrame(outPath,f,g.getFrameCount());
                        System.out.println("Processing frame " + (f+1) + "/" + g.getFrameCount());
                        ImageMatrix mat = new ImageMatrix(g.getFrame(f));
                        BufferedImage render = mat.Render();
                        g2.setFrame(f,render);
                    }

                    System.out.println("Saving file...");
                    g2.save(outPath);
                } else {
                    BufferedImage img  = fetchImage(sourcePath);
                    pixels = img.getHeight() * img.getWidth();

                    File outFile = new File(outPath);
                    ImageMatrix mat = new ImageMatrix(img);
                    BufferedImage render = mat.Render();

                    System.out.println("Saving file...");
                    ImageIO.write(render, getEXT(outPath), outFile);
                }


                System.out.println("File saved");
                long endTime = System.nanoTime();
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###");
                
                String numberAsString = decimalFormat.format(pixels);
                System.out.println("Time elapsed: " + (((endTime - startTime)/1000000)/1000) + " seconds");
                System.out.println("Pixels processed: " + numberAsString);
                openProcess(outPath);
            }

            defCur();
            updateTitle();
        } catch(Exception ex) {
            defCur();
            printEx(ex);
        }
    }
    
    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) 
    {
        AboutDialog dlgAbout = new AboutDialog(this,origUI);
        dlgAbout.setVisible(true);
    }

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) 
    {
        SettingsDialog dlgSettings = new SettingsDialog(this,origUI);
        dlgSettings.setVisible(true);
        font = a.getFont();
    }
}
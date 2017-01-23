package ascii_studio;

import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.*;

public class AboutDialog extends javax.swing.JDialog 
{
    ResourceLoader rs = new ResourceLoader();
    public AboutDialog(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
    }
    
    public AboutDialog(MainWindow wnd, String UI) 
    {
    	super();
        initComponents();
        setUI(UI);
        this.setLocationRelativeTo(wnd);
        this.setSize(new Dimension(700, 500));
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() 
    {
    	this.setTitle("About");
	lblProgName = new javax.swing.JLabel();
        pnlIcon = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLicense = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtChangelog = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
		
    	pnlIcon = new ImagePanel(rs.getResourceImage("92.png"));
        lblProgName.setText("ASCII Studio 3.0");

        javax.swing.GroupLayout pnlIconLayout = new javax.swing.GroupLayout(pnlIcon);
        pnlIcon.setLayout(pnlIconLayout);
        pnlIconLayout.setHorizontalGroup(
            pnlIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE)
        );
        pnlIconLayout.setVerticalGroup(
            pnlIconLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE)
        );

        btnOK.setText("OK");
        btnOK.addActionListener((java.awt.event.ActionEvent evt) ->  btnOKActionPerformed(evt));

        txtLicense.setEditable(false);
        txtLicense.setText("Copyright (C) 2016-2017, Ian Martinez\nAll rights reserved.\n\nRedistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:\n\n1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.\n\n2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
        txtLicense.setCaretPosition(0);
        jScrollPane1.setViewportView(txtLicense);

        jTabbedPane1.addTab("License", jScrollPane1);

        txtChangelog.setEditable(false);
        txtChangelog.setText("Version 3.0 (1/23/17)\n   - ASCII Converter changed to ASCII Studio\n   - Better looks on high DPI displays\n   - Hue/Saturation Editor\nVersion 2.0 (10/12/16)\n   - Code cleanup\n   - Migrate from JCreator to NetBeans\n   - Fixed off by 1 bug in ASCII renderer\n   - Updated Icons\n   - Export to Image using a phrase\nVersion 1.1 (5/17/16)\n   - Redesigned UI\n   - Progress bars in console\n   - Small bug fixes\n   - Fixed bug where characters where off by 1\n   - Files now open when they're converted\nVersion 1.0 (5/15/16)\n   - Initial release");
        jScrollPane2.setViewportView(txtChangelog);

        jTabbedPane1.addTab("Changelog", jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblProgName)
                                .addGap(0, 186, Short.MAX_VALUE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOK)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProgName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOK)
                .addContainerGap())
        );

        pack();
    }

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	this.dispose();
    }
    
    public static void setUI(String name) 
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
    			
    	} catch (Exception ex) {
            ex.printStackTrace();
    	}
    }
    
    public static void main(String args[]) 
    {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AboutDialog dialog = new AboutDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    
    private javax.swing.JButton btnOK;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblProgName;
    private javax.swing.JPanel pnlIcon;
    private javax.swing.JTextPane txtChangelog;
    private javax.swing.JTextPane txtLicense;
}

class ImagePanel extends JPanel 
{
  private Image img;

  public ImagePanel(String str) 
  {
  	try 
        {
	  img = ImageIO.read(new File(str));
  	} catch (Exception e) {}
  }

  public ImagePanel(Image img) 
  {
    this.img = img;
  }

  public void paintComponent(Graphics g) 
  {
     if(img != null) g.drawImage(img, Math.max((int)this.getSize().getWidth()/2-img.getWidth(null)/2,0), Math.max((int)this.getSize().getHeight()/2-img.getHeight(null)/2,0), null);
  }
}

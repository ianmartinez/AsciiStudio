package ascii_studio;

import java.awt.*;
import javax.swing.*;
import javax.swing.UIManager.*;


public class SettingsDialog extends javax.swing.JDialog 
{
    private MainWindow parent;
    public SettingsDialog(MainWindow wnd, String UI) 
    {
        super();
        parent=wnd;
        initComponents();
        btnOK.requestFocus();
        getSettings();
        setUI(UI);
        this.setLocationRelativeTo(wnd);
    }
    
    public SettingsDialog(java.awt.Frame parent, boolean modal) 
    {
        super(parent, modal);
        initComponents();
        btnOK.requestFocus();
    }

    
    @SuppressWarnings("unchecked")
    private void initComponents() 
    {

        jLabel1 = new javax.swing.JLabel();
        btnBackColor = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        pnlBack = new javax.swing.JPanel();
        pnlFont = new javax.swing.JPanel();
        btnFontColor = new javax.swing.JButton();
        cbOverride = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtWeights = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        txtFont = new javax.swing.JTextField();
        spFontSize = new javax.swing.JSpinner();
        rbItalic = new javax.swing.JRadioButton();
        rbBold = new javax.swing.JRadioButton();
        rbBoldItalic = new javax.swing.JRadioButton();
        rbPlain = new javax.swing.JRadioButton();
        btnInvertValues = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Converter Palette");
        setResizable(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Background Color:");

        btnBackColor.setText("...");
        btnBackColor.addActionListener((java.awt.event.ActionEvent evt) -> btnBackColorActionPerformed(evt));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Font Color:");

        pnlBack.setBackground(Color.WHITE);
        pnlBack.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout pnlBackLayout = new javax.swing.GroupLayout(pnlBack);
        pnlBack.setLayout(pnlBackLayout);
        pnlBackLayout.setHorizontalGroup(
            pnlBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );
        pnlBackLayout.setVerticalGroup(
            pnlBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        pnlFont.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		pnlFont.setBackground(Color.BLACK);
		
        javax.swing.GroupLayout pnlFontLayout = new javax.swing.GroupLayout(pnlFont);
        pnlFont.setLayout(pnlFontLayout);
        pnlFontLayout.setHorizontalGroup(
            pnlFontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );
        pnlFontLayout.setVerticalGroup(
            pnlFontLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
		
        btnFontColor.setText("...");
        btnFontColor.addActionListener((java.awt.event.ActionEvent evt) -> btnFontColorActionPerformed(evt));

        cbOverride.setText("Override Image Colors");
        cbOverride.setHideActionText(true);
        cbOverride.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cbOverride.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cbOverride.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        cbOverride.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cbOverride.addActionListener((java.awt.event.ActionEvent evt) -> cbOverrideActionPerformed(evt));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Font Style:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("String Weights/Phrase:");

        txtWeights.setText("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ");
        txtWeights.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCancel.setText("Cancel");
        btnCancel.addActionListener((java.awt.event.ActionEvent evt) -> btnCancelActionPerformed(evt));

        btnOK.setText("OK");
        btnOK.setSelected(true);
        btnOK.addActionListener((java.awt.event.ActionEvent evt) -> btnOKActionPerformed(evt));

        txtFont.setText("Consolas");
        txtFont.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        spFontSize.setValue(12);

        rbItalic.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        rbItalic.setText("I");

        rbBold.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbBold.setText("B");

        rbBoldItalic.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        rbBoldItalic.setText("BI");

        rbPlain.setSelected(true);
        rbPlain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbPlain.setText("P");

        btnInvertValues.setText("Invert Values");
        btnInvertValues.addActionListener((java.awt.event.ActionEvent evt) -> btnInvertValuesActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBackColor))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInvertValues)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWeights, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtFont)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbOverride, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pnlFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnFontColor))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbPlain, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.DEFAULT_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbBold, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.DEFAULT_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbItalic, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.DEFAULT_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbBoldItalic, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.DEFAULT_SIZE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBackColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFontColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlFont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbOverride, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFont, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rbBold)
                            .addComponent(rbItalic)
                            .addComponent(rbBoldItalic)
                            .addComponent(rbPlain))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtWeights, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOK)
                    .addComponent(btnInvertValues))
                .addContainerGap())
        );
		
		ButtonGroup g = new ButtonGroup();
		g.add(rbPlain);
		g.add(rbBold);
		g.add(rbItalic);
		g.add(rbBoldItalic);	
		
		
        pack();
    }
    
    
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
    	} catch (Exception ex) {ex.printStackTrace();}
    }
	
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> 
        {
            SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    private javax.swing.JButton btnBackColor;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnFontColor;
    private javax.swing.JButton btnOK;
    private javax.swing.JCheckBox cbOverride;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtWeights;
    private javax.swing.JPanel pnlBack;
    private javax.swing.JPanel pnlFont;
    private javax.swing.JRadioButton rbBold;
    private javax.swing.JRadioButton rbBoldItalic;
    private javax.swing.JRadioButton rbItalic;
    private javax.swing.JRadioButton rbPlain;
    private javax.swing.JSpinner spFontSize;
    private javax.swing.JTextField txtFont;
    private javax.swing.JButton btnInvertValues;
    
    // End netbeans crap
    private void getSettings() 
    {
    	ASCII a = parent.a;
        pnlBack.setBackground(a.getBackColor());
        pnlFont.setBackground(a.getFontColor());
        cbOverride.setSelected(a.getOverrideRTF());

        txtFont.setText(a.getFont().getFontName());
        spFontSize.setValue(a.getFont().getSize());
        int fontStyle = a.getFont().getStyle();
        switch (fontStyle) 
        {
            case Font.PLAIN:
                rbPlain.setSelected(true);
                break;
            case Font.BOLD:
                rbBold.setSelected(true);
                break;
            case Font.ITALIC:
                rbItalic.setSelected(true);
                break;
            case Font.BOLD+Font.ITALIC:
                rbBoldItalic.setSelected(true);
                break;
            default:
                rbPlain.setSelected(true);
                break;
        }

        txtWeights.setText(combine(a.weights));
    }
    
    private void setSettings() 
    {
    	ASCII a = parent.a;
    	a.setBackColor(pnlBack.getBackground());
    	a.setFontColor(pnlFont.getBackground());
    	a.setOverrideRTF(cbOverride.isSelected());

        int fontStyle=Font.PLAIN;
        
        if (rbBold.isSelected()==true)
            fontStyle=Font.BOLD;
        else if (rbItalic.isSelected())
            fontStyle=Font.ITALIC;
        else if (rbBoldItalic.isSelected())
            fontStyle=Font.BOLD + Font.ITALIC;	

        a.setFont(new Font(txtFont.getText(),fontStyle,(int)spFontSize.getValue()));
        a.weights = split(txtWeights.getText());
    }
    
    
    private String combine(String[] str) 
    {
        String ret = "";
        for(String s:str)
            ret+=s;
        
        return ret;
    }
    
    private String[] split(String str) 
    {
    	return str.split("");
    }
    
    private void btnBackColorActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	Color tempColor = JColorChooser.showDialog(this,"Choose Color",pnlBack.getBackground());
    	
    	if(tempColor != null)
            pnlBack.setBackground(tempColor);
    }
    
    private void btnFontColorActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	Color tempColor = JColorChooser.showDialog(this,"Choose Color",pnlFont.getBackground());
    	
    	if(tempColor != null)
            pnlFont.setBackground(tempColor);
    }

    private void cbOverrideActionPerformed(java.awt.event.ActionEvent evt) 
    {
    }

    private void btnInvertValuesActionPerformed(java.awt.event.ActionEvent evt) 
    {
        Color bColor = pnlBack.getBackground();
        Color fColor = pnlFont.getBackground();

        pnlBack.setBackground(fColor);
        pnlFont.setBackground(bColor);

        String flipped = "";
        String str = txtWeights.getText();
        for (int i=str.length()-1;i>=0;i--) 
            flipped += str.substring(i,i+1);

        txtWeights.setText(flipped);
    }
	
    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) 
    {
        setSettings();
    	this.dispose();
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) 
    {
    	this.dispose();
    }
}
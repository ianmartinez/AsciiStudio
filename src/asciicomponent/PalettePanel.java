/*
 * Copyright (C) 2020 Ian Martinez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package asciicomponent;

import asciilib.FontUtil;
import asciilib.Palette;
import asciistudio.SimpleDocumentListener;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.*;
import java.awt.GraphicsEnvironment;
import javax.swing.ButtonGroup;

/**
 * A panel for modifying a palette.
 *
 * @author Ian Martinez
 */
public class PalettePanel extends javax.swing.JPanel {

    private Palette palette = new Palette();
    private final ButtonGroup weightsPhraseGroup = new ButtonGroup();

    /**
     * Creates new form FontPicker
     */
    public PalettePanel() {
        initComponents();

        // Add button groups
        weightsPhraseGroup.add(useWeightsRadioButton);
        weightsPhraseGroup.add(usePhraseRadioButton);

        setPalette(palette);

        // Add event listeners
        weightsPhraseValueTextField.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            palette.setWeightsString(weightsPhraseValueTextField.getText());
        });
        
    }

    /**
     * Set the palette UI to match a palette
     *
     * @param palette the palette
     */
    public final void setPalette(Palette palette) {
        this.palette = palette;

        // Load font combo with current selection from the palette
        fontFamiliesComboBox.removeAllItems();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        var allFonts = FontUtil.getAllFontNames(true);
        for (String fontName : allFonts) {
            fontFamiliesComboBox.addItem(fontName);
            if (fontName.equals(palette.getFont().getName())) {
                fontFamiliesComboBox.setSelectedItem(fontName);
            }
        }

        // Set font size from palette
        fontSizeSpinner.setValue(palette.getFont().getSize());

        // Set font styles from palette
        fontBoldCheckbox.setSelected(palette.getFont().isBold());
        fontItalicCheckbox.setSelected(palette.getFont().isItalic());

        // Set colors from palette
        backgroundColorPanel.setColor(palette.getBackgroundColor());
        fontColorPanel.setColor(palette.getFontColor());
        overrideImageCheckbox.setSelected(palette.isOverridingImageColors());

        // Set weights/phrase
        weightsPhraseValueTextField.setText(palette.getWeightsString());
        if (palette.isUsingPhrase()) {
            usePhraseRadioButton.setSelected(true);
        } else {
            useWeightsRadioButton.setSelected(true);
        }
    }

    /**
     * Refresh the palette object with the values from the UI
     */
    private void savePalette() {
        // Font
        var fontName = (String) fontFamiliesComboBox.getSelectedItem();
        var fontSize = (int) fontSizeSpinner.getValue();
        var isBold = fontBoldCheckbox.isSelected();
        var isItalic = fontItalicCheckbox.isSelected();
        int fontStyle = PLAIN;

        if (isBold && isItalic) {
            fontStyle = BOLD | ITALIC;
        } else if (isBold) {
            fontStyle = BOLD;
        } else if (isItalic) {
            fontStyle = ITALIC;
        }

        // Set font 
        palette.setFont(new Font(fontName, fontStyle, fontSize));

        // Colors
        palette.setOverridingImageColors(overrideImageCheckbox.isSelected());
        palette.setBackgroundColor(backgroundColorPanel.getColor());
        palette.setFontColor(fontColorPanel.getColor());

        // Weights/phrase
        palette.setUsingPhrase(usePhraseRadioButton.isSelected());
        palette.setWeightsString(weightsPhraseValueTextField.getText());
    }

    /**
     * @return The palette stored by this control
     */
    public Palette getPalette() {
        savePalette();
        return new Palette(palette);
    }

    /**
     * Reset the palette to its default state.
     */
    public void resetPalette() {
        setPalette(new Palette());
    }

    /**
     * Swap the background and font colors of the palette and reverse the
     * weights if not using a phrase.
     */
    public void invertPalette() {
        var invertedPalette = new Palette(palette);
        invertedPalette.setBackgroundColor(palette.getFontColor());
        invertedPalette.setFontColor(palette.getBackgroundColor());

        if (!palette.isUsingPhrase()) {
            var reversedWeights = Palette.reverseWeightsString(palette.getWeightsString());
            invertedPalette.setWeightsString(reversedWeights);
        }

        setPalette(invertedPalette);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        paletteTabs = new javax.swing.JTabbedPane();
        colorsTab = new javax.swing.JPanel();
        backgroundColorContainer = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        backgroundColorButton = new javax.swing.JButton();
        backgroundColorPanel = new asciicomponent.ColorPanel();
        fontColorContainer = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        overrideImageCheckbox = new javax.swing.JCheckBox();
        fontColorPanel = new asciicomponent.ColorPanel();
        fontColorButton = new javax.swing.JButton();
        fontTab = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        fontFamiliesComboBox = new javax.swing.JComboBox<>();
        fontSizeSpinner = new javax.swing.JSpinner();
        fontBoldCheckbox = new javax.swing.JCheckBox();
        fontItalicCheckbox = new javax.swing.JCheckBox();
        weightsPhraseTab = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        useWeightsRadioButton = new javax.swing.JRadioButton();
        usePhraseRadioButton = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        weightsPhraseValueTextField = new javax.swing.JTextField();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(0, 10, 10);
        flowLayout1.setAlignOnBaseline(true);
        colorsTab.setLayout(flowLayout1);

        backgroundColorContainer.setMinimumSize(new java.awt.Dimension(200, 45));
        backgroundColorContainer.setOpaque(false);
        backgroundColorContainer.setPreferredSize(new java.awt.Dimension(270, 30));
        java.awt.GridBagLayout jPanel7Layout = new java.awt.GridBagLayout();
        jPanel7Layout.columnWeights = new double[] {1.0, 0.0, 0.0};
        backgroundColorContainer.setLayout(jPanel7Layout);

        jLabel4.setFont(jLabel4.getFont().deriveFont(jLabel4.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel4.setText("Background Color:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 9, 3, 9);
        backgroundColorContainer.add(jLabel4, gridBagConstraints);

        backgroundColorButton.setText("...");
        backgroundColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backgroundColorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        backgroundColorContainer.add(backgroundColorButton, gridBagConstraints);

        backgroundColorPanel.setColor(new java.awt.Color(255, 255, 255));
        backgroundColorPanel.setPreferredSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout backgroundColorPanelLayout = new javax.swing.GroupLayout(backgroundColorPanel);
        backgroundColorPanel.setLayout(backgroundColorPanelLayout);
        backgroundColorPanelLayout.setHorizontalGroup(
            backgroundColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        backgroundColorPanelLayout.setVerticalGroup(
            backgroundColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        backgroundColorContainer.add(backgroundColorPanel, gridBagConstraints);

        colorsTab.add(backgroundColorContainer);

        fontColorContainer.setMinimumSize(new java.awt.Dimension(200, 45));
        fontColorContainer.setOpaque(false);
        fontColorContainer.setPreferredSize(new java.awt.Dimension(340, 30));
        java.awt.GridBagLayout jPanel9Layout = new java.awt.GridBagLayout();
        jPanel9Layout.columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};
        fontColorContainer.setLayout(jPanel9Layout);

        jLabel5.setFont(jLabel5.getFont().deriveFont(jLabel5.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel5.setText("Font Color:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 9, 3, 9);
        fontColorContainer.add(jLabel5, gridBagConstraints);

        overrideImageCheckbox.setToolTipText("");
        overrideImageCheckbox.setDoubleBuffered(true);
        overrideImageCheckbox.setLabel("Override image");
        overrideImageCheckbox.setMinimumSize(new java.awt.Dimension(130, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fontColorContainer.add(overrideImageCheckbox, gridBagConstraints);

        fontColorPanel.setPreferredSize(new java.awt.Dimension(100, 30));

        javax.swing.GroupLayout fontColorPanelLayout = new javax.swing.GroupLayout(fontColorPanel);
        fontColorPanel.setLayout(fontColorPanelLayout);
        fontColorPanelLayout.setHorizontalGroup(
            fontColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        fontColorPanelLayout.setVerticalGroup(
            fontColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        fontColorContainer.add(fontColorPanel, gridBagConstraints);

        fontColorButton.setText("...");
        fontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontColorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fontColorContainer.add(fontColorButton, gridBagConstraints);

        colorsTab.add(fontColorContainer);

        paletteTabs.addTab("Colors", colorsTab);

        fontTab.setLayout(new java.awt.FlowLayout(0));

        jPanel12.setMinimumSize(new java.awt.Dimension(300, 45));
        jPanel12.setPreferredSize(new java.awt.Dimension(450, 30));
        java.awt.GridBagLayout jPanel12Layout = new java.awt.GridBagLayout();
        jPanel12Layout.columnWeights = new double[] {1.0, 0.0, 0.0};
        jPanel12.setLayout(jPanel12Layout);

        jLabel8.setFont(jLabel8.getFont().deriveFont(jLabel8.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel8.setText("Font:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 9, 3, 9);
        jPanel12.add(jLabel8, gridBagConstraints);

        fontFamiliesComboBox.setMinimumSize(new java.awt.Dimension(150, 27));
        fontFamiliesComboBox.setPreferredSize(new java.awt.Dimension(150, 27));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel12.add(fontFamiliesComboBox, gridBagConstraints);

        fontSizeSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));
        fontSizeSpinner.setMinimumSize(new java.awt.Dimension(70, 26));
        fontSizeSpinner.setPreferredSize(new java.awt.Dimension(25, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel12.add(fontSizeSpinner, gridBagConstraints);

        fontBoldCheckbox.setText("Bold");
        fontBoldCheckbox.setMinimumSize(new java.awt.Dimension(70, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel12.add(fontBoldCheckbox, gridBagConstraints);

        fontItalicCheckbox.setText("Italic");
        fontItalicCheckbox.setMinimumSize(new java.awt.Dimension(70, 23));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        jPanel12.add(fontItalicCheckbox, gridBagConstraints);

        fontTab.add(jPanel12);

        paletteTabs.addTab("Font", fontTab);

        weightsPhraseTab.setLayout(new java.awt.GridLayout(0, 1, 5, 5));

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        useWeightsRadioButton.setSelected(true);
        useWeightsRadioButton.setText("Use Weights");
        useWeightsRadioButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jPanel4.add(useWeightsRadioButton);

        usePhraseRadioButton.setText("Use Phrase");
        usePhraseRadioButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        jPanel4.add(usePhraseRadioButton);

        weightsPhraseTab.add(jPanel4);

        jPanel13.setMinimumSize(new java.awt.Dimension(600, 45));
        jPanel13.setPreferredSize(new java.awt.Dimension(520, 30));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(jLabel9.getFont().deriveFont(jLabel9.getFont().getStyle() | java.awt.Font.BOLD));
        jLabel9.setText("Value:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 9, 3, 9);
        jPanel13.add(jLabel9, gridBagConstraints);

        weightsPhraseValueTextField.setMinimumSize(new java.awt.Dimension(150, 26));
        weightsPhraseValueTextField.setPreferredSize(new java.awt.Dimension(150, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel13.add(weightsPhraseValueTextField, gridBagConstraints);

        weightsPhraseTab.add(jPanel13);

        paletteTabs.addTab("Weights/Phrase", weightsPhraseTab);

        add(paletteTabs);
    }// </editor-fold>//GEN-END:initComponents

    private void backgroundColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backgroundColorButtonActionPerformed
        var newColor = ColorPickerDialog.showDialog(this, "Choose background color", backgroundColorPanel.getColor());

        if (newColor != null) {
            backgroundColorPanel.setColor(newColor);
        }
    }//GEN-LAST:event_backgroundColorButtonActionPerformed

    private void fontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontColorButtonActionPerformed
        Color newColor = ColorPickerDialog.showDialog(this, "Choose font color", fontColorPanel.getColor());

        if (newColor != null) {
            fontColorPanel.setColor(newColor);
        }
    }//GEN-LAST:event_fontColorButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backgroundColorButton;
    private javax.swing.JPanel backgroundColorContainer;
    private asciicomponent.ColorPanel backgroundColorPanel;
    private javax.swing.JPanel colorsTab;
    private javax.swing.JCheckBox fontBoldCheckbox;
    private javax.swing.JButton fontColorButton;
    private javax.swing.JPanel fontColorContainer;
    private asciicomponent.ColorPanel fontColorPanel;
    private javax.swing.JComboBox<String> fontFamiliesComboBox;
    private javax.swing.JCheckBox fontItalicCheckbox;
    private javax.swing.JSpinner fontSizeSpinner;
    private javax.swing.JPanel fontTab;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JCheckBox overrideImageCheckbox;
    private javax.swing.JTabbedPane paletteTabs;
    private javax.swing.JRadioButton usePhraseRadioButton;
    private javax.swing.JRadioButton useWeightsRadioButton;
    private javax.swing.JPanel weightsPhraseTab;
    private javax.swing.JTextField weightsPhraseValueTextField;
    // End of variables declaration//GEN-END:variables

}
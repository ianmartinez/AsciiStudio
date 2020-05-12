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
package asciistudio;

import javax.swing.UIManager;

/**
 * Main class
 *
 * @author Ian Martinez
 */
public class AsciiStudio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {      
        // Change the app name in macOS's menu bar
        System.setProperty("apple.awt.application.name", "ASCII Studio");
        
        // Use native menu on macOS
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        
        // Use native look and feel
        try {
            if(System.getProperty("os.name", "").startsWith("Mac OS")) {        
                // Use improved Aqua look and feel over native Java version.
                // Adds support for dark mode, among other things
                UIManager.setLookAndFeel("org.violetlib.aqua.AquaLookAndFeel");
            } else {                
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            // Couldn't load native look and feel, so just continue with
            // default.
        }

        // Launch main window
        var mainWindow = new MainWindow();
        // Center on screen
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setTitle(App.getAppTitle());
        
        // Configure styling
        App.setRootProperty(mainWindow, "Aqua.windowStyle", "unifiedToolBar");
        App.setProperty(mainWindow.sidebarPanel, "Aqua.backgroundStyle", "vibrantSidebar");
        /*App.setProperty(mainWindow.importButton, "JButton.buttonType", "toolbarItem");
        App.setProperty(mainWindow.exportButton, "JButton.buttonType", "toolbarItem");*/

        
        // Show
        mainWindow.setVisible(true);
    }

}

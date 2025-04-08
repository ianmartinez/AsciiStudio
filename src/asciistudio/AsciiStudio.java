/*
 * Copyright (C) 2025 Ian Martinez
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

import asciilib.Platform;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Desktop;
import java.awt.desktop.AboutEvent;
import java.util.Collections;

/**
 * Main class.
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
        // Use dark mode on macOS
        System.setProperty("apple.awt.application.appearance", "system");

        // Load FlatLaf dark macOS look and feel
        try {
            FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#2493d4"));
            // Don't theme titlebar
            System.setProperty("flatlaf.useWindowDecorations", "false");
            
            if (Platform.isMac()) {
                FlatMacDarkLaf.setup();
            } else {               
                FlatDarkLaf.setup(); 
            }
        } catch (Exception e) {
            // Couldn't load look and feel, so just continue with
            // default.
            e.printStackTrace();
        }

        // Launch main window
        var mainWindow = new MainWindow();
        // Center on screen
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setTitle(App.getAppTitle());

        // Set about handler to open the about window on macOS
        if (Platform.isMac()) {
            Desktop.getDesktop().setAboutHandler((AboutEvent aboutEvent) -> {
                var aboutDialog = new AboutDialog(mainWindow, true);
                aboutDialog.setLocationRelativeTo(mainWindow);
                aboutDialog.setVisible(true);
            });
        }

        // Show
        mainWindow.setVisible(true);
    }

}

package com.argenischacon.util;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.prefs.Preferences;

public class ThemeManager {
    private static final Logger logger = LoggerFactory.getLogger(ThemeManager.class);

    private static final Preferences prefs = Preferences.userNodeForPackage(ThemeManager.class);
    private static final String KEY_THEME = "theme";
    private static final String THEME_LIGHT = "light";
    private static final String THEME_DARK = "dark";

    private ThemeManager(){

    }

    public static void applySavedTheme() {
        String saved = prefs.get(KEY_THEME, THEME_LIGHT);

        try {
            if (THEME_DARK.equals(saved)) {
                FlatDarkLaf.setup();
            } else{
                FlatLightLaf.setup();
            }
        } catch (Exception e) {
            logger.error("Failed to apply saved theme", e);
            //fallback
            FlatLightLaf.setup();
        }
    }

    public static void saveTheme(boolean isDark){
        prefs.put(KEY_THEME, isDark ? THEME_DARK : THEME_LIGHT);
    }

    public static boolean isDark(){
        return UIManager.getLookAndFeel() instanceof FlatDarkLaf;
    }

    public static void toggleTheme(){
        boolean newThemeIsDark = !isDark();
        try{
            FlatAnimatedLafChange.showSnapshot();
            if(newThemeIsDark){
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }else{
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            FlatLaf.updateUI();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
            saveTheme(newThemeIsDark);
        } catch (Exception e) {
            logger.error("Failed to toggle theme", e);
        }
    }

    public static void resetThemePreference(){
        try{
            prefs.remove(KEY_THEME);
            prefs.flush();
            applySavedTheme();
        } catch (Exception e){
            logger.error("Failed to reset theme preference", e);
        }
    }
}

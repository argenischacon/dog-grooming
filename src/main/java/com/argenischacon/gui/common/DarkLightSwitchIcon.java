package com.argenischacon.gui.common;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.AnimatedIcon;
import com.formdev.flatlaf.util.ColorFunctions;
import com.formdev.flatlaf.util.UIScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class DarkLightSwitchIcon implements AnimatedIcon {

    private static final Logger logger = LoggerFactory.getLogger(DarkLightSwitchIcon.class);

    private final int iconGap = 3;
    private final int centerSpace = 5;

    private final Icon darkIcon;
    private final Icon lightIcon;

    private final Color darkColor = new Color(80, 80, 80);
    private final Color lightColor = new Color(230, 230, 230);

    public DarkLightSwitchIcon() {
        this.darkIcon = loadIcon("/icons/theme/dark.svg");
        this.lightIcon = loadIcon("/icons/theme/light.svg");
    }

    private static Icon loadIcon(String path) {
        try {
            URL url = DarkLightSwitchIcon.class.getResource(path);
            if (url != null) return new FlatSVGIcon(url);
            logger.warn("Icon resource not found: {}", path);
        } catch (Exception e) {
            logger.error("Failed to load icon: {}", path, e);
        }
        return null;
    }

    @Override
    public void paintIconAnimated(Component c, Graphics g, int x, int y, float animatedValue) {
        Graphics2D g2 = (Graphics2D) g.create();
        FlatUIUtils.setRenderingHints(g2);
        Color color = ColorFunctions.mix(darkColor, lightColor, animatedValue);
        int size = getIconHeight();
        int width = getIconWidth();
        float arc = Math.min(FlatUIUtils.getBorderArc((JComponent) c), size);
        float animatedX = x + (width - size) * animatedValue;

        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Double(animatedX, y, size, size, arc, arc));
        float darkY = (y - size + (animatedValue * size));
        float lightY = y + animatedValue * size;
        g2.setClip(new Rectangle(x, y, width, size));
        paintIcon(c, (Graphics2D) g.create(), animatedX, darkY, darkIcon, animatedValue);
        paintIcon(c, (Graphics2D) g.create(), animatedX, lightY, lightIcon, 1f - animatedValue);
        g2.dispose();
    }

    public void paintIcon(Component c, Graphics2D g, float x, float y, Icon icon, float alpha) {
        int gap = UIScale.scale(iconGap);
        g.translate(x, y);
        g.setComposite(AlphaComposite.SrcOver.derive(alpha));
        icon.paintIcon(c, g, gap, gap);
        g.dispose();
    }

    @Override
    public int getAnimationDuration() {
        return 500;
    }

    @Override
    public float getValue(Component c) {
        return ((AbstractButton) c).isSelected() ? 1 : 0;
    }

    @Override
    public int getIconWidth() {
        return darkIcon.getIconWidth() + lightIcon.getIconWidth() + UIScale.scale(centerSpace) + (UIScale.scale(iconGap) * 4);
    }

    @Override
    public int getIconHeight() {
        return Math.max(darkIcon.getIconHeight(), lightIcon.getIconHeight()) + UIScale.scale(iconGap) * 2;
    }
}

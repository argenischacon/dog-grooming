package com.argenischacon.gui.common;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.net.URL;
import javax.swing.JTextField;

public class SearchTextField extends JTextField {

    public SearchTextField() {
        URL url = getClass().getResource("/icons/search.svg");
        if (url != null) {
            putClientProperty("JTextField.leadingIcon", new FlatSVGIcon(url));
        }
        putClientProperty("JTextField.placeholderText", "Ingresa búsqueda");
    }
}

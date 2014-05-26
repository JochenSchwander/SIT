package de.hs_mannheim.sit.ss14.auxiliaries;

import javax.swing.text.*;
import java.awt.*;

/**
 * 
 * @author André Uhres
 * @url http://www.java-forum.org/bilder-gui-damit-zusammenhaengt/7213-jtextfield-dokumentarten.html#post587501
 *
 */
public class DocumentSizeFilter extends DocumentFilter {
    private final int maxCharacters;
    private final String pattern;
    public DocumentSizeFilter(final int maxChars, final String pattern) {
        maxCharacters = maxChars;
        this.pattern = pattern;
    }
    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
            throws BadLocationException {
        if (str.matches(pattern) && (fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
            super.replace(fb, offs, length, str, a);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
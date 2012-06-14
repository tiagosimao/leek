/*
 * This file is part of Leek.
 * 
 * Leek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Leek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Leek.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.irenical.leek.view.html.core;

public interface HTMLConstants {
    
    /**
     * ATTRIBUTES
     */
    
    public static final String TYPE_KEY = "type";
    
    public static final String CSS_CLASS_KEY = "class";
    
    public static final String SRC_KEY = "src";
    
    public static final String HREF_KEY = "href";
    
    public static final String ONCLICK_KEY = "onclick";
    
    public static final String WIDTH_KEY = "width";
    
    public static final String HEIGHT_KEY = "height";
    
    public static final String TITLE_KEY = "title";
    
    public static final String ALT_KEY = "alt";
    
    /**
     * ATTRIBUTE VALUES
     */
    
    public static final String TEXT_ATTR = "text";
    
    public static final String CHECKBOX_ATTR = "checkbox";
    
    public static final String RADIO_ATTR = "radio";
    
    public static final String SUBMIT_ATTR = "submit";
    
    /**
     * SYMBOLS
     */
    
    public static final char SYMBOL_EQUALS = '=';
    
    public static final char SYMBOL_WHITESPACE = ' ';
    
    public static final char SYMBOL_QUOTES = '"';
    
    public static final char SYMBOL_APOSTROPHE = '\'';
    
    public static final char SYMBOL_LESS_THAN = '<';
    
    public static final char SYMBOL_GREATER_THAN = '>';
    
    public static final char SYMBOL_SLASH = '/';
    
    public static final char SYMBOL_NEWLINE = '\n';
    
    public static final char SYMBOL_EXCLAMATION = '!';
    
    public static final char SYMBOL_HYPHEN = '-';
    
    public static final String OPENING_COMMENT = new String(new char[] { SYMBOL_LESS_THAN, SYMBOL_EXCLAMATION, SYMBOL_HYPHEN, SYMBOL_HYPHEN });
    
    public static final String CLOSING_COMMENT = new String(new char[] { SYMBOL_HYPHEN, SYMBOL_HYPHEN, SYMBOL_EXCLAMATION, SYMBOL_GREATER_THAN });
    
    public static final String SELF_CLOSING = new String(new char[] { SYMBOL_SLASH, SYMBOL_GREATER_THAN });
    
    public static final String OPENING_CLOSE = new String(new char[] { SYMBOL_LESS_THAN, SYMBOL_SLASH });
    
    public static final String CLOSING_CLOSE = new String(new char[] { SYMBOL_GREATER_THAN });
    
    public static final String CDATA_START = "//<![CDATA[\n";
    
    public static final String CDATA_END = "//]]>";

}

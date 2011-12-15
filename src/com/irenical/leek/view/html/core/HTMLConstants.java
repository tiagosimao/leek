package com.irenical.leek.view.html.core;

public interface HTMLConstants {
	
	public static final String CSS_CLASS_KEY = "class";
	
	public static final String SRC_KEY = "src";
	
	public static final String HREF_KEY = "src";
	
	public static final String ONCLICK_KEY = "onclick";
	
	public static final String WIDTH_KEY = "width";
	
	public static final String HEIGHT_KEY = "height";

	public static final char SYMBOL_EQUALS = '=';
	
	public static final char SYMBOL_WHITESPACE = ' ';
	
	public static final char SYMBOL_QUOTES = '"';
	
	public static final char SYMBOL_LESS_THAN = '<';

	public static final char SYMBOL_GREATER_THAN = '>';
	
	public static final char SYMBOL_SLASH = '/';
	
	public static final char SYMBOL_NEWLINE = '\n';
	
	public static final char SYMBOL_EXCLAMATION = '!';
	
	public static final char SYMBOL_HYPHEN = '-';
	
	public static final String OPENING_COMMENT = new String(new char[]{SYMBOL_LESS_THAN,SYMBOL_EXCLAMATION,SYMBOL_HYPHEN,SYMBOL_HYPHEN});
	
	public static final String CLOSING_COMMENT = new String(new char[]{SYMBOL_HYPHEN,SYMBOL_HYPHEN,SYMBOL_EXCLAMATION,SYMBOL_GREATER_THAN});
	
	public static final String SELF_CLOSING=new String(new char[]{SYMBOL_SLASH,SYMBOL_GREATER_THAN});
	
	public static final String OPENING_CLOSE=new String(new char[]{SYMBOL_LESS_THAN,SYMBOL_SLASH});
	
	public static final String CLOSING_CLOSE=new String(new char[]{SYMBOL_GREATER_THAN});
	
}

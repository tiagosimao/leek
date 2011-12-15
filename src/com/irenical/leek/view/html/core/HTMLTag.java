package com.irenical.leek.view.html.core;



public class HTMLTag implements HTMLConstants{
	
	public static final HTMLTag A = new HTMLTag("a");
	public static final HTMLTag ABBR = new HTMLTag("abbr");
	public static final HTMLTag ACRONYM = new HTMLTag("acronym");
	public static final HTMLTag ADDRESS = new HTMLTag("address");
	public static final HTMLTag AREA = new HTMLTag("area");
	public static final HTMLTag B = new HTMLTag("b");
	public static final HTMLTag BDO = new HTMLTag("bdo");
	public static final HTMLTag BIG = new HTMLTag("big");
	public static final HTMLTag BLOCKQUOTE = new HTMLTag("blockquote");
	public static final HTMLTag BODY = new HTMLTag("body");
	public static final HTMLTag BR = new HTMLTag("br");
	public static final HTMLTag BUTTON = new HTMLTag("button");
	public static final HTMLTag CAPTION = new HTMLTag("caption");
	public static final HTMLTag CITE = new HTMLTag("cite");
	public static final HTMLTag CODE = new HTMLTag("code");
	public static final HTMLTag COL = new HTMLTag("col");
	public static final HTMLTag COLGROUP = new HTMLTag("colgroup");
	public static final HTMLTag DD = new HTMLTag("dd");
	public static final HTMLTag DEL = new HTMLTag("del");
	public static final HTMLTag DFN = new HTMLTag("dfn");
	public static final HTMLTag DIV = new HTMLTag("div",false,false);
	public static final HTMLTag DL = new HTMLTag("dl");
	public static final HTMLTag DT = new HTMLTag("dt");
	public static final HTMLTag EM = new HTMLTag("em");
	public static final HTMLTag FIELDSET = new HTMLTag("fieldset");
	public static final HTMLTag FORM = new HTMLTag("form");
	public static final HTMLTag FRAME = new HTMLTag("frame");
	public static final HTMLTag FRAMESET = new HTMLTag("frameset");
	public static final HTMLTag H1 = new HTMLTag("h1");
	public static final HTMLTag H2 = new HTMLTag("h2");
	public static final HTMLTag H3 = new HTMLTag("h3");
	public static final HTMLTag H4 = new HTMLTag("h4");
	public static final HTMLTag H5 = new HTMLTag("h5");
	public static final HTMLTag H6 = new HTMLTag("h6");
	public static final HTMLTag HEAD = new HTMLTag("head");
	public static final HTMLTag HR = new HTMLTag("hr");
	public static final HTMLTag HTML = new HTMLTag("html");
	public static final HTMLTag I = new HTMLTag("i");
	public static final HTMLTag IFRAME = new HTMLTag("iframe");
	public static final HTMLTag IMG = new HTMLTag("img");
	public static final HTMLTag INPUT = new HTMLTag("input");
	public static final HTMLTag INS = new HTMLTag("ins");
	public static final HTMLTag KBD = new HTMLTag("kbd");
	public static final HTMLTag LABEL = new HTMLTag("label");
	public static final HTMLTag LEGEND = new HTMLTag("legend");
	public static final HTMLTag LI = new HTMLTag("li");
	public static final HTMLTag LINK = new HTMLTag("link");
	public static final HTMLTag MAP = new HTMLTag("map");
	public static final HTMLTag META = new HTMLTag("meta");
	public static final HTMLTag NOFRAMES = new HTMLTag("noframes");
	public static final HTMLTag NOSCRIPT = new HTMLTag("noscript");
	public static final HTMLTag OBJECT = new HTMLTag("object");
	public static final HTMLTag OL = new HTMLTag("ol");
	public static final HTMLTag OPTGROUP = new HTMLTag("optgroup");
	public static final HTMLTag OPTION = new HTMLTag("option");
	public static final HTMLTag P = new HTMLTag("p");
	public static final HTMLTag PARAM = new HTMLTag("param");
	public static final HTMLTag PRE = new HTMLTag("pre");
	public static final HTMLTag Q = new HTMLTag("q");
	public static final HTMLTag S = new HTMLTag("s");
	public static final HTMLTag SAMP = new HTMLTag("samp");
	public static final HTMLTag SCRIPT = new HTMLTag("script");
	public static final HTMLTag SELECT = new HTMLTag("select");
	public static final HTMLTag SMALL = new HTMLTag("small");
	public static final HTMLTag SPAN = new HTMLTag("span",false,true);
	public static final HTMLTag STRONG = new HTMLTag("strong");
	public static final HTMLTag STYLE = new HTMLTag("style");
	public static final HTMLTag SUB = new HTMLTag("sub");
	public static final HTMLTag SUP = new HTMLTag("sup");
	public static final HTMLTag TABLE = new HTMLTag("table");
	public static final HTMLTag TBODY = new HTMLTag("tbody");
	public static final HTMLTag TD = new HTMLTag("td");
	public static final HTMLTag TEXTAREA = new HTMLTag("textarea");
	public static final HTMLTag TFOOT = new HTMLTag("tfoot");
	public static final HTMLTag TH = new HTMLTag("th");
	public static final HTMLTag THEAD = new HTMLTag("thead");
	public static final HTMLTag TITLE = new HTMLTag("title");
	public static final HTMLTag TR = new HTMLTag("tr");
	public static final HTMLTag TT = new HTMLTag("tt");
	public static final HTMLTag UL = new HTMLTag("ul");
	public static final HTMLTag VAR = new HTMLTag("var");
	
	public final boolean inline;
	
	public final boolean canSelfClose;
	
	private final String name;
	
	private HTMLTag(String name){
		this(name,true,false);
	}
	
	private HTMLTag(String name, boolean canSelfClose, boolean inline){
		this.canSelfClose=canSelfClose;
		this.inline=inline;
		this.name=name;
	}
	
	protected <MODEL_CLASS> void htmlOpen(StringBuilder builder,MODEL_CLASS model,HTMLAttributes<MODEL_CLASS> attributes,boolean selfClosing,boolean isCommented) {
		if(isCommented){
			builder.append(OPENING_COMMENT);
		}
		builder.append(SYMBOL_LESS_THAN+name);
		if(attributes!=null){
			attributes.draw(builder,model);
		}
		builder.append(selfClosing?(isCommented?CLOSING_COMMENT:SELF_CLOSING):CLOSING_CLOSE);
	}
	
	protected void htmlClose(StringBuilder builder,boolean isCommented) {
		builder.append(OPENING_CLOSE);
		builder.append(name);
		builder.append(CLOSING_CLOSE);
		if(isCommented){
			builder.append(CLOSING_COMMENT);
		}
	}
	
}
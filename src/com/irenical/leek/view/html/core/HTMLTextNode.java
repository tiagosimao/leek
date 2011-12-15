package com.irenical.leek.view.html.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.view.ViewConfig;
import com.irenical.leek.view.string.StringView;


public class HTMLTextNode<MODEL_CLASS> extends StringView<MODEL_CLASS> implements HTMLConstants {
	
	private final List<Object> allText = Collections.synchronizedList(new LinkedList<Object>());
	
	protected HTMLTextNode(ViewConfig<MODEL_CLASS> config) {
		super(config);
	}
	
	public HTMLTextNode<MODEL_CLASS> addStaticText(String text){
		return addText(text);
	}
	
	public HTMLTextNode<MODEL_CLASS> addMutableText(ModelTransformer<MODEL_CLASS,String> transformer){
		return addText(transformer);
	}
	
	private HTMLTextNode<MODEL_CLASS> addText(Object value){
		allText.add(value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildString(StringBuilder builder,MODEL_CLASS model) {
		if(config==null||config.isShowing(model)){
			for(Object text : allText){
				String stringValue = null;
				if(text instanceof ModelTransformer<?,?>){
					stringValue = ((ModelTransformer<MODEL_CLASS,String>)text).transform(model);
				} else if(text instanceof String){
					stringValue = (String)text;
				}
				if(stringValue!=null){
					builder.append(stringValue);
				}
			}
		}
	}

}

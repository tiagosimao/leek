package com.irenical.leek.view.html.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.irenical.leek.model.ModelToManyTransformer;
import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.view.ViewConfig;
import com.irenical.leek.view.string.StringView;

public class HTMLNode<MODEL_CLASS> extends StringView<MODEL_CLASS> implements HTMLConstants {
	
	private final Map<StringView<?>,ModelTransformer<MODEL_CLASS,?>> transformers = Collections.synchronizedMap(new HashMap<StringView<?>, ModelTransformer<MODEL_CLASS, ?>>());
	
	private final Map<StringView<?>,ViewConfig<?>> configs = Collections.synchronizedMap(new HashMap<StringView<?>, ViewConfig<?>>());
	
	private final List<StringView<?>> children = Collections.synchronizedList(new LinkedList<StringView<?>>());
	
	private final HTMLAttributes<MODEL_CLASS> attributes = new HTMLAttributes<MODEL_CLASS>();
	
	private final HTMLTag tag;
	
	private boolean commented = false;
	
	public HTMLNode(HTMLTag tag, ViewConfig<MODEL_CLASS> config) {
		super(config);
		this.tag=tag;
	}
	
	public void setCommented(boolean commented) {
		this.commented = commented;
	}
	
	public HTMLAttributes<MODEL_CLASS> getAttributes() {
		return attributes;
	}
	
	@SuppressWarnings("unchecked")
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS> addTextNode(){
		return (HTMLTextNode<CHILD_MODEL_CLASS>)createNode(null,null,null);
	}
	
	@SuppressWarnings("unchecked")
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS> addTextNode(Class<CHILD_MODEL_CLASS> modelClass){
		return (HTMLTextNode<CHILD_MODEL_CLASS>)createNode(null,null,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS> addTextNode(ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS> transformer){
		return (HTMLTextNode<CHILD_MODEL_CLASS>) createNode(null,transformer,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS> addTextNode(ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS> transformer,ViewConfig<CHILD_MODEL_CLASS> config){
		return (HTMLTextNode<CHILD_MODEL_CLASS>) createNode(null,transformer,config);
	}
	
	@SuppressWarnings("unchecked")
	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS> addNode(HTMLTag tag){
		return (HTMLNode<CHILD_MODEL_CLASS>) createNode(tag,null,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS> addNode(HTMLTag tag,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS> transformer){
		return (HTMLNode<CHILD_MODEL_CLASS>) createNode(tag,transformer,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS> addNode(HTMLTag tag,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS> transformer,ViewConfig<CHILD_MODEL_CLASS> config){
		return (HTMLNode<CHILD_MODEL_CLASS>) createNode(tag,transformer,config);
	}
	
	private <CHILD_MODEL_CLASS> StringView<CHILD_MODEL_CLASS> createNode(HTMLTag tag,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS> transformer,ViewConfig<CHILD_MODEL_CLASS> config){
		StringView<CHILD_MODEL_CLASS> child = null;
		if(tag==null){
			child = new HTMLTextNode<CHILD_MODEL_CLASS>(config);
		} else {
			child = new HTMLNode<CHILD_MODEL_CLASS>(tag,config);
		}
		transformers.put(child, transformer);
		configs.put(child, config);
		children.add(child);
		return child;
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	protected void buildString(StringBuilder builder,MODEL_CLASS model) {
		if(config==null||config.isShowing(model)){
			boolean selfClosing = children.isEmpty() && tag.canSelfClose;
			tag.htmlOpen(builder, model, attributes, selfClosing, commented);
			for(StringView child : children){
				ModelTransformer<MODEL_CLASS, ?> transformer = transformers.get(child);
				if(transformer instanceof ModelToManyTransformer<?, ?>){
					Iterable<?> models = ((ModelToManyTransformer<MODEL_CLASS,?>)transformer).toMany(model);
					if(models!=null){
						for(Object subModel : models){
							builder.append(SYMBOL_NEWLINE);
							child.draw(builder, subModel);
						}
					}
				} else {
					Object targetModel = transformer == null ? model : transformer.transform(model);
					builder.append(SYMBOL_NEWLINE);
					child.draw(builder,targetModel);
				}
			}
			if(!selfClosing){
				builder.append(SYMBOL_NEWLINE);
				tag.htmlClose(builder, commented);
			}
		}
	}
	
	public static <MODEL_CLASS> HTMLNode<MODEL_CLASS> createNode(HTMLTag tag, ViewConfig<MODEL_CLASS> config){
		return new HTMLNode<MODEL_CLASS>(tag,config);
	}
	
}

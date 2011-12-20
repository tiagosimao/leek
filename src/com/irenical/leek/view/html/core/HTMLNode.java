/*
	This file is part of Leek.

    Leek is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Leek is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Leek.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.irenical.leek.view.html.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.view.ViewConfigInterface;
import com.irenical.leek.view.string.StringView;

public class HTMLNode<MODEL_CLASS,CONFIG_CLASS extends ViewConfigInterface> extends StringView<MODEL_CLASS,CONFIG_CLASS> implements HTMLConstants {
	
	private final Map<StringView<?,?>,ModelTransformer<MODEL_CLASS,?,CONFIG_CLASS>> transformers = Collections.synchronizedMap(new HashMap<StringView<?,?>, ModelTransformer<MODEL_CLASS,?,CONFIG_CLASS>>());
	
	private final List<StringView<?,?>> children = Collections.synchronizedList(new LinkedList<StringView<?,?>>());
	
	private final HTMLAttributes<MODEL_CLASS,CONFIG_CLASS> attributes = new HTMLAttributes<MODEL_CLASS,CONFIG_CLASS>();
	
	private final HTMLTag tag;
	
	private boolean commented = false;
	
	public HTMLNode(HTMLTag tag) {
		this.tag=tag;
	}
	
	public void setCommented(boolean commented) {
		this.commented = commented;
	}
	
	public void setStaticAttribute(String key,String value){
		attributes.setStaticAttribute(key, value);
	}
	
	public void setStaticAttribute(String key,String value,boolean append){
		attributes.setStaticAttribute(key, value, append);
	}
	
	public void setMutableAttribute(String key,ModelTransformer<MODEL_CLASS,String,CONFIG_CLASS> transformer){
		attributes.setMutableAttribute(key, transformer);
	}
	
	public void setMutableAttribute(String key,ModelTransformer<MODEL_CLASS,String,CONFIG_CLASS> transformer,boolean append){
		attributes.setMutableAttribute(key, transformer, append);
	}
	
	@SuppressWarnings("unchecked")
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addTextNode(){
		return (HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(null,null,null);
	}
	
	@SuppressWarnings("unchecked")
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addTextNode(Class<CHILD_MODEL_CLASS> modelClass){
		return (HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(null,null,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addTextNode(ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS,CONFIG_CLASS> transformer){
		return (HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(null,transformer,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addTextNode(ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS,CONFIG_CLASS> transformer,CONFIG_CLASS config){
		return (HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(null,transformer,config);
	}
	
	@SuppressWarnings("unchecked")
	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addNode(HTMLTag tag){
		return (HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(tag,null,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addNode(HTMLTag tag,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS,CONFIG_CLASS> transformer){
		return (HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(tag,transformer,null);
	}
	
	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS> addNode(HTMLTag tag,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS,CONFIG_CLASS> transformer,CONFIG_CLASS config){
		return (HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS>) createNode(tag,transformer,config);
	}
	
	public <CHILD_MODEL_CLASS> void addNode(HTMLNode<CHILD_MODEL_CLASS,?> child,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS,CONFIG_CLASS> transformer){
		transformers.put(child, transformer);
		children.add(child);
	}
	
	private <CHILD_MODEL_CLASS> StringView<CHILD_MODEL_CLASS,CONFIG_CLASS> createNode(HTMLTag tag,ModelTransformer<MODEL_CLASS,CHILD_MODEL_CLASS,CONFIG_CLASS> transformer,CONFIG_CLASS config){
		StringView<CHILD_MODEL_CLASS,CONFIG_CLASS> child = null;
		if(tag==null){
			child = new HTMLTextNode<CHILD_MODEL_CLASS,CONFIG_CLASS>();
		} else {
			child = new HTMLNode<CHILD_MODEL_CLASS,CONFIG_CLASS>(tag);
		}
		transformers.put(child, transformer);
		children.add(child);
		return child;
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	protected void buildString(StringBuilder builder,MODEL_CLASS model,CONFIG_CLASS config) {
		if(config==null||config.isShowing()){
			boolean selfClosing = children.isEmpty() && tag.canSelfClose;
			tag.htmlOpen(builder, model, config, attributes, selfClosing, commented);
			for(StringView child : children){
				ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS> transformer = transformers.get(child);
				Iterable<?> models = transformer == null ? null : ((ModelTransformer<MODEL_CLASS,?,CONFIG_CLASS>)transformer).toMany(model);
				if(models!=null){
					for(Object subModel : models){
						builder.append(SYMBOL_NEWLINE);
						child.draw(builder,subModel,config);
					}
				} else {
					Object targetModel = transformer == null ? model : transformer.transform(model,config);
					builder.append(SYMBOL_NEWLINE);
					child.draw(builder,targetModel,config);
				}
			}
			if(!selfClosing){
				builder.append(SYMBOL_NEWLINE);
				tag.htmlClose(builder, commented);
			}
		}
	}
	
}

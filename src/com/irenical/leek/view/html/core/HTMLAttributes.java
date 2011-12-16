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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.view.ViewConfig;
import com.irenical.leek.view.string.StringView;

public class HTMLAttributes<MODEL_CLASS> extends StringView<MODEL_CLASS,ViewConfig<MODEL_CLASS>> implements HTMLConstants {
	
	private final Map<String,Object> allAttributes = Collections.synchronizedMap(new HashMap<String,Object>());
	
	protected HTMLAttributes() {
		super(null);
	}
	
	public void setStaticAttribute(String key,String value){
		setAttribute(key, value, false);
	}
	
	public void setStaticAttribute(String key,String value,boolean append){
		setAttribute(key, value, append);
	}
	
	public void setMutableAttribute(String key,ModelTransformer<MODEL_CLASS,String> transformer){
		setAttribute(key, transformer, false);
	}
	
	public void setMutableAttribute(String key,ModelTransformer<MODEL_CLASS,String> transformer,boolean append){
		setAttribute(key, transformer, append);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setAttribute(String key,Object value,boolean append){
		if(append){
			Object oldValue = allAttributes.get(key);
			if(oldValue instanceof Collection<?>){
				((Collection)oldValue).add(value);
			} else if(oldValue != null){
				List<Object> collection = Collections.synchronizedList(new LinkedList<Object>());
				collection.add(oldValue);
				collection.add(value);
				oldValue = collection;
			} else {
				oldValue = value;
			}
			allAttributes.put(key, oldValue);
		} else {
			allAttributes.put(key, value);
		}
	}
	
	@Override
	protected void buildString(StringBuilder builder,MODEL_CLASS model) {
		for(String key:allAttributes.keySet()){
			Object values=allAttributes.get(key);
			if(values!=null){
				builder.append(SYMBOL_WHITESPACE);
				builder.append(key);
				builder.append(SYMBOL_EQUALS);
				builder.append(SYMBOL_QUOTES);
				buildValueString(builder,values,model);
				builder.append(SYMBOL_QUOTES);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void buildValueString(StringBuilder builder,Object value,MODEL_CLASS model){
		if(value instanceof Collection<?>){
			for(Object v : ((Collection<?>)value)){
				buildValueString(builder,v,model);
				builder.append(SYMBOL_WHITESPACE);
			}
		} else if(value instanceof ModelTransformer<?,?>) {
			String stringValue = ((ModelTransformer<MODEL_CLASS,String>)value).transform(model);
			builder.append(stringValue);
		} else if(value instanceof String) {
			builder.append(value);
		}
	}
	
}

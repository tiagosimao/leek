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
import java.util.LinkedList;
import java.util.List;

import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.view.ViewConfigInterface;
import com.irenical.leek.view.string.StringView;


public class HTMLTextNode<MODEL_CLASS,CONFIG_CLASS extends ViewConfigInterface<MODEL_CLASS>> extends StringView<MODEL_CLASS,CONFIG_CLASS> implements HTMLConstants {
	
	private final List<Object> allText = Collections.synchronizedList(new LinkedList<Object>());
	
	protected HTMLTextNode() {
	}
	
	public HTMLTextNode<MODEL_CLASS,CONFIG_CLASS> addStaticText(String text){
		return addText(text);
	}
	
	public HTMLTextNode<MODEL_CLASS,CONFIG_CLASS> addMutableText(ModelTransformer<MODEL_CLASS,String> transformer){
		return addText(transformer);
	}
	
	private HTMLTextNode<MODEL_CLASS,CONFIG_CLASS> addText(Object value){
		allText.add(value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildString(StringBuilder builder,MODEL_CLASS model,CONFIG_CLASS config) {
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

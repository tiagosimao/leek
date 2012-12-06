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

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.utils.TextEncoder;
import com.irenical.leek.view.string.StringView;

public class HTMLTextNode<MODEL_CLASS, CONFIG_CLASS> extends StringView<MODEL_CLASS, CONFIG_CLASS> implements HTMLConstants {

	private final List<Object> allText = Collections.synchronizedList(new LinkedList<Object>());

	public HTMLTextNode() {
	}

	public HTMLTextNode<MODEL_CLASS, CONFIG_CLASS> addStaticText(String text) {
		return addText(text);
	}

	public HTMLTextNode<MODEL_CLASS, CONFIG_CLASS> addMutableText(ModelTransformer<MODEL_CLASS, String, CONFIG_CLASS> transformer) {
		return addText(transformer);
	}

	private HTMLTextNode<MODEL_CLASS, CONFIG_CLASS> addText(Object value) {
		allText.add(value);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void buildString(Appendable builder, MODEL_CLASS model, CONFIG_CLASS config, boolean inCData,
                               boolean noEscape, int groupIndex) throws IOException {
		if (isVisible(model, config, groupIndex)) {
			for (Object text : allText) {
				String stringValue = null;
				if (text instanceof ModelTransformer<?, ?, ?>) {
					Object value = ((ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS>) text).transform(model, config, groupIndex);
					stringValue = (value == null) ? null : value.toString();
				} else if (text instanceof String) {
					stringValue = (String) text;
				}
				if (stringValue != null) {
                    builder.append(noEscape ? stringValue : TextEncoder.encodeHTML(stringValue));
                    if(inCData) TextEncoder.encodeCData(stringValue);
                }
			}
		}
	}

}

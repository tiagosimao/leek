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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.irenical.leek.model.ModelTransformer;
import com.irenical.leek.view.string.StringView;

public class HTMLNode<MODEL_CLASS, CONFIG_CLASS> extends StringView<MODEL_CLASS, CONFIG_CLASS> implements HTMLConstants {

	private final Map<StringView<?, ?>, ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS>> transformers = Collections.synchronizedMap(new HashMap<StringView<?, ?>, ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS>>());

	private final List<StringView<?, ?>> children = Collections.synchronizedList(new LinkedList<StringView<?, ?>>());

	private final HTMLAttributes<MODEL_CLASS, CONFIG_CLASS> attributes = new HTMLAttributes<MODEL_CLASS, CONFIG_CLASS>();

	private HTMLTag tag;
	
	private HTMLTagControllerInterface<MODEL_CLASS, CONFIG_CLASS> tagController;

	private boolean commented = false;

	private boolean printTag = true;
	
	public HTMLNode(HTMLTagControllerInterface<MODEL_CLASS, CONFIG_CLASS> tagController) {
		this.tagController = tagController;
	}

	public HTMLNode(HTMLTag tag) {
		this.tag = tag;
	}
	
	public void setTagController(HTMLTagControllerInterface<MODEL_CLASS, CONFIG_CLASS> tagController) {
		this.tagController = tagController;
	}

	public void setCommented(boolean commented) {
		this.commented = commented;
	}

	public void setPrintTag(boolean printTag) {
		this.printTag = printTag;
	}

	public boolean isPrintTag() {
		return printTag;
	}

	public void setStaticAttribute(String key, String value) {
		attributes.setStaticAttribute(key, value);
	}

	public void setStaticAttribute(String key, String value, boolean append) {
		attributes.setStaticAttribute(key, value, append);
	}

	public void setMutableAttribute(String key, ModelTransformer<MODEL_CLASS, String, CONFIG_CLASS> transformer) {
		attributes.setMutableAttribute(key, transformer);
	}

	public void setMutableAttribute(String key, ModelTransformer<MODEL_CLASS, String, CONFIG_CLASS> transformer, boolean append) {
		attributes.setMutableAttribute(key, transformer, append);
	}

	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS> createTextNode() {
		return (HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS>) createTextNode((ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS>) null);
	}

	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS> createTextNode(Class<CHILD_MODEL_CLASS> modelClass) {
		return (HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS>) createTextNode((ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS>) null);
	}

	public <CHILD_MODEL_CLASS> HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS> createTextNode(ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS> transformer) {
		return addTextNode(new HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS>(), transformer);
	}

	public <CHILD_MODEL_CLASS, RETURN_TYPE extends HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS>> HTMLTextNode<CHILD_MODEL_CLASS, CONFIG_CLASS> addTextNode(RETURN_TYPE node, ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS> transformer) {
		add(node, transformer);
		return node;
	}

	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS> createNode(HTMLTag tag) {
		return (HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS>) createNode(tag, (ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS>) null);
	}

	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS> createNode(HTMLTag tag, Class<CHILD_MODEL_CLASS> modelClass) {
		return (HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS>) createNode(tag, (ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS>) null);
	}

	public <CHILD_MODEL_CLASS> HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS> createNode(HTMLTag tag, ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS> transformer) {
		return addNode(new HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS>(tag), transformer);
	}

	public <CHILD_MODEL_CLASS, RETURN_TYPE extends HTMLNode<CHILD_MODEL_CLASS, CONFIG_CLASS>> RETURN_TYPE addNode(RETURN_TYPE node, ModelTransformer<MODEL_CLASS, CHILD_MODEL_CLASS, CONFIG_CLASS> transformer) {
		add(node, transformer);
		return node;
	}

	private void add(StringView<?, ?> node, ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS> transformer) {
		if (!children.contains(node)) {
			transformers.put(node, transformer);
			children.add(node);
			node.parent = this;
		}
	}

	public List<StringView<?, ?>> getChildren() {
		return children;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void buildString(Appendable builder, MODEL_CLASS model, CONFIG_CLASS config, int groupIndex) throws IOException {
		if (isVisible(model, config, groupIndex)) {
			boolean selfClosing = children.isEmpty() && tag.canSelfClose;
			HTMLTag tag = printTag ? (tagController == null ? null : tagController.getTag(model, config, groupIndex) ) : null;
			tag = (printTag && tag == null) ? this.tag : null;
			if (tag != null ) {
				tag.htmlOpen(builder, model, config, groupIndex, attributes, selfClosing, commented);
			}
			for (StringView child : children) {
				ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS> transformer = transformers.get(child);
				CONFIG_CLASS targetConfig = transformer == null ? config : transformer.transformConfig(config);
				Iterable<?> models = transformer == null ? null : ((ModelTransformer<MODEL_CLASS, ?, CONFIG_CLASS>) transformer).toMany(model, config);
				if (models != null) {
					int gi = 0;
					for (Object subModel : models) {
						builder.append(SYMBOL_NEWLINE);
						child.draw(builder, subModel, targetConfig, gi++);
					}
				} else {
					Object targetModel = transformer == null ? model : transformer.transform(model, config, groupIndex);
					builder.append(SYMBOL_NEWLINE);
					child.draw(builder, targetModel, targetConfig, groupIndex);
				}
			}
			if (!selfClosing) {
				builder.append(SYMBOL_NEWLINE);
				if (tag != null) {
					tag.htmlClose(builder, commented);
				}
			}
		}
	}

	@Override
	public String toString() {
		String result = "+[" + tag.getName() + "(" + getClass().getSimpleName() + "): ";
		for (StringView<?, ?> c : children) {
			result += c.toString();
		}
		return result + "]";
	}

}

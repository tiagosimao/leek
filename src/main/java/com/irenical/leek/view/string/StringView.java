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
package com.irenical.leek.view.string;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.irenical.leek.controller.VisibilityControllerInterface;

public abstract class StringView<MODEL_CLASS, CONFIG_CLASS> {

	private final List<VisibilityControllerInterface<MODEL_CLASS, CONFIG_CLASS>> visibilityControllers = Collections.synchronizedList(new LinkedList<VisibilityControllerInterface<MODEL_CLASS, CONFIG_CLASS>>());

	public StringView<?, ?> parent;

	public void addVisibilityController(VisibilityControllerInterface<MODEL_CLASS, CONFIG_CLASS> controller) {
		if (controller != null) {
			visibilityControllers.add(controller);
		}
	}

	public boolean isVisible(MODEL_CLASS model, CONFIG_CLASS config, int groupIndex) {
		boolean result = true;
		for (VisibilityControllerInterface<MODEL_CLASS, CONFIG_CLASS> controller : visibilityControllers) {
			result &= controller.isVisible(model, config);
			if (!result) {
				break;
			}
		}
		return result;
	}

    public final void draw(Appendable builder, MODEL_CLASS model, CONFIG_CLASS config, int groupIndex) throws IOException {
        buildString(builder, model, config, false, false, groupIndex);
    }

    public final void draw(Appendable builder, MODEL_CLASS model, CONFIG_CLASS config, boolean inCData, boolean noEscape,
                           int groupIndex) throws IOException {
        buildString(builder, model, config, inCData, noEscape, groupIndex);
    }

	protected abstract void buildString(Appendable builder, MODEL_CLASS model, CONFIG_CLASS config, boolean inCData,
                                        boolean noEscape, int groupIndex) throws IOException;

}

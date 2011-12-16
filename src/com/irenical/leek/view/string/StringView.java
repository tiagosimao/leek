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

import com.irenical.leek.view.ViewConfig;

public abstract class StringView<MODEL_CLASS,CONFIG_CLASS extends ViewConfig<MODEL_CLASS>>{
	
	private final CONFIG_CLASS config;
	
	protected StringView(CONFIG_CLASS config){
		this.config = config;
	}
	
	public CONFIG_CLASS getConfig() {
		return config;
	}
	
	public final void draw(StringBuilder builder,MODEL_CLASS model){
		buildString(builder,model);
	}
	
	protected abstract void buildString(StringBuilder builder,MODEL_CLASS model);

}

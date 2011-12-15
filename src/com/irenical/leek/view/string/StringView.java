package com.irenical.leek.view.string;

import com.irenical.leek.view.ViewConfig;

public abstract class StringView<MODEL_CLASS>{
	
	protected final ViewConfig<MODEL_CLASS> config;
	
	protected StringView(ViewConfig<MODEL_CLASS> config){
		this.config = config;
	}
	
	public final void draw(StringBuilder builder,MODEL_CLASS model){
		buildString(builder,model);
	}
	
	protected abstract void buildString(StringBuilder builder,MODEL_CLASS model);

}

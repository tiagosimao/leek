package com.irenical.leek.view.html.core;


public interface HTMLTagControllerInterface<MODEL_CLASS, CONFIG_CLASS> {
	
	public HTMLTag getTag(MODEL_CLASS model, CONFIG_CLASS config, int groupIndex);

}

package com.irenical.leek.controller;


public interface VisibilityControllerInterface<MODEL_CLASS, CONFIG_CLASS> {
	
	public boolean isVisible(MODEL_CLASS model, CONFIG_CLASS config);

}

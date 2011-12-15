package com.irenical.leek.model;

public interface ModelTransformer<FROM_MODEL,TO_MODEL> {
	
	public TO_MODEL transform(FROM_MODEL model);

}

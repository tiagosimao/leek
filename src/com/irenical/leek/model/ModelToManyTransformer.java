package com.irenical.leek.model;


public abstract class ModelToManyTransformer<FROM_MODEL,TO_MODEL> implements ModelTransformer<FROM_MODEL,TO_MODEL> {
	
	@Override
	public final TO_MODEL transform(FROM_MODEL model) {
		return null;
	};

	public abstract Iterable<TO_MODEL> toMany(FROM_MODEL model);

}

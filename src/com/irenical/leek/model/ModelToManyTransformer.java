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
package com.irenical.leek.model;


public abstract class ModelToManyTransformer<FROM_MODEL,TO_MODEL> implements ModelTransformer<FROM_MODEL,TO_MODEL> {
	
	@Override
	public final TO_MODEL transform(FROM_MODEL model) {
		return null;
	};

	public abstract Iterable<TO_MODEL> toMany(FROM_MODEL model);

}

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

package com.irenical.leek.utils;


public class TextStripper {
    public static String alphaOnly(String value) {
        value = value.replaceAll("[^A-Za-z0-9-]", "");

        if(value.isEmpty())
            return "_";
        else
            return value;
    }
}

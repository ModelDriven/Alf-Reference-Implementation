/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;


public interface MultiplicityElement extends Element {
	public boolean getIsOrdered();

	public void setIsOrdered(boolean isOrdered);

	public boolean getIsUnique();

	public void setIsUnique(boolean isUnique);

	public int getUpper();
	
	public void setUpper(int upper);

	public int getLower();
	
	public void setLower(int lower);

	public ValueSpecification getUpperValue();

	public void setUpperValue(ValueSpecification upperValue);

	public ValueSpecification getLowerValue();

	public void setLowerValue(ValueSpecification lowerValue);
}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;


public interface Property extends StructuralFeature {
	public boolean getIsDerived();

	public void setIsDerived(boolean isDerived);

	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);

	public boolean getIsDerivedUnion();

	public void setIsDerivedUnion(boolean isDerivedUnion);

	public String getAggregation();

	public void setAggregation(String aggregation);

	public boolean getIsComposite();

	public Association getOwningAssociation();

	public DataType getDatatype();

	public Association getAssociation();

	public Class_ getClass_();

	public Property getOpposite();
}

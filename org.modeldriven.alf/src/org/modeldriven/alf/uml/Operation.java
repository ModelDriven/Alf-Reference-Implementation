/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;

public interface Operation extends BehavioralFeature {
	public boolean getIsQuery();

	public void setIsQuery(boolean isQuery);

	public boolean getIsOrdered();

	public boolean getIsUnique();

	public int getLower();

	public int getUpper();

	public Class_ getClass_();

    // NOTE: This is needed for mapping an OperationDefinition, even though it 
    // is the opposite of the composite property Class::ownedOperation.
    public void setClass_(Class_ class_);
    
	public List<Operation> getRedefinedOperation();

	public void addRedefinedOperation(Operation redefinedOperation);

	public Type getType();

	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

}

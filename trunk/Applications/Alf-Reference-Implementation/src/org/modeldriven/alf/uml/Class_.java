/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;

public interface Class_ extends BehavioredClassifier {
	public boolean getIsAbstract();

	public void setIsAbstract(boolean isAbstract);

	public List<Operation> getOwnedOperation();

	public void addOwnedOperation(Operation ownedOperation);

	public List<Class_> getSuperClass();

	public boolean getIsActive();

	public void setIsActive(boolean isActive);

	public List<Reception> getOwnedReception();

	public void addOwnedReception(Reception ownedReception);

	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);

	public List<Classifier> getNestedClassifier();

	public void addNestedClassifier(Classifier nestedClassifier);
}

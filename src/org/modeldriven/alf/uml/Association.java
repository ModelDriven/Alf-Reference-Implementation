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

public interface Association extends Classifier {
	public boolean getIsDerived();

	public void setIsDerived(boolean isDerived);

	public List<Property> getOwnedEnd();

	public void addOwnedEnd(Property ownedEnd);

	public List<Type> getEndType();

	public List<Property> getMemberEnd();

	public List<Property> getNavigableOwnedEnd();

	public void addNavigableOwnedEnd(Property navigableOwnedEnd);
}

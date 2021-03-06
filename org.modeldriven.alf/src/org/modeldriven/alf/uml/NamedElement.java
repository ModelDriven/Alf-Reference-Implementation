/*******************************************************************************
 * Copyright 2011-2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.Collections;
import java.util.List;

public interface NamedElement extends Element {
	public String getName();

	public void setName(String name);

	// Legal visibility values: "public", "private", "protected", "package"
	public String getVisibility();

	public void setVisibility(String visibility);

	public String getQualifiedName();

	public Namespace getNamespace();
	
	public List<Dependency> getClientDependency();

	// This is a non-navigable association end in the UML metamodel.
    public default List<Dependency> getSupplierDependency() {
        return Collections.emptyList();
    }

    public boolean isDistinguishableFrom(NamedElement otherElement, Namespace namespace);

}

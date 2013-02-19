/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.ArrayList;
import java.util.List;

public class NamedElement extends Element implements
		org.modeldriven.alf.uml.NamedElement {

	public NamedElement(org.eclipse.uml2.uml.NamedElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.NamedElement getBase() {
		return (org.eclipse.uml2.uml.NamedElement) this.base;
	}

	public String getName() {
		return this.getBase().getName();
	}

	public void setName(String name) {
		this.getBase().setName(name);
	}

	public String getVisibility() {
		return this.getBase().getVisibility().toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				org.eclipse.uml2.uml.VisibilityKind.get(visibility));
	}

	public String getQualifiedName() {
		return this.getBase().getQualifiedName();
	}

	public org.modeldriven.alf.uml.Namespace getNamespace() {
		return (org.modeldriven.alf.uml.Namespace) wrap(this.getBase()
				.getNamespace());
	}

	@Override
	public List<org.modeldriven.alf.uml.Dependency> getClientDependency() {
		List<org.modeldriven.alf.uml.Dependency> list = 
				new ArrayList<org.modeldriven.alf.uml.Dependency>();
		for (org.eclipse.uml2.uml.Dependency dependency: 
			this.getBase().getClientDependencies()) {
			list.add((org.modeldriven.alf.uml.Dependency)wrap(dependency));
		}
		return list;
	}

    @Override
    public boolean isDistinguishableFrom(org.modeldriven.alf.uml.NamedElement otherElement,
            org.modeldriven.alf.uml.Namespace namespace) {
        return this.getBase().isDistinguishableFrom(
                ((NamedElement)otherElement).getBase(), 
                
                // NOTE: This construction allows the cast to work for
                // classifiers. Do to the lack of multiple inheritance, the
                // adapter class for Classifier is NOT a subtype of the
                // adapter class for Namespace.
                (org.eclipse.uml2.uml.Namespace)((Element)namespace).getBase());
    }

}

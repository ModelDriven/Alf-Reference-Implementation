/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.UMLFactory;

public class Constraint extends PackageableElement implements org.modeldriven.alf.uml.Constraint {

	public Constraint() {
		this(UMLFactory.eINSTANCE.createConstraint());
	}

	public Constraint(org.eclipse.uml2.uml.Constraint base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.Constraint getBase() {
		return (org.eclipse.uml2.uml.Constraint)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.Namespace getContext() {
		return (org.modeldriven.alf.uml.Namespace)wrap(this.getBase().getContext());
	}

	@Override
	public List<org.modeldriven.alf.uml.Element> getConstrainedElement() {
		List<org.modeldriven.alf.uml.Element> list = 
				new ArrayList<org.modeldriven.alf.uml.Element>();
		for (org.eclipse.uml2.uml.Element element: this.getBase().getConstrainedElements()) {
			list.add(wrap(element));
		}
		return list;
	}

	@Override
	public org.modeldriven.alf.uml.ValueSpecification getSpecification() {
		return (org.modeldriven.alf.uml.ValueSpecification)wrap(this.getBase().getSpecification());
	}

}

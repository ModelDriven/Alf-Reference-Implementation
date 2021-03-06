/*******************************************************************************
 * Copyright 2011-2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.uml2.uml.UMLFactory;

public class Operation extends BehavioralFeature implements
		org.modeldriven.alf.uml.Operation {
	public Operation() {
		this(UMLFactory.eINSTANCE.createOperation());
	}

	public Operation(org.eclipse.uml2.uml.Operation base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Operation getBase() {
		return (org.eclipse.uml2.uml.Operation) this.base;
	}

	public boolean getIsQuery() {
		return this.getBase().isQuery();
	}

	public void setIsQuery(boolean isQuery) {
		this.getBase().setIsQuery(isQuery);
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered();
	}

	public boolean getIsUnique() {
		return this.getBase().isUnique();
	}

	public int getLower() {
		return this.getBase().getLower();
	}

	public int getUpper() {
		return this.getBase().getUpper();
	}

	public org.modeldriven.alf.uml.Class_ getClass_() {
		return (org.modeldriven.alf.uml.Class_) wrap(this.getBase().getClass_());
	}

	public List<org.modeldriven.alf.uml.Operation> getRedefinedOperation() {
		List<org.modeldriven.alf.uml.Operation> list = new ArrayList<org.modeldriven.alf.uml.Operation>();
		for (org.eclipse.uml2.uml.Operation element : this.getBase()
				.getRedefinedOperations()) {
			list.add((org.modeldriven.alf.uml.Operation) wrap(element));
		}
		return list;
	}

	public void addRedefinedOperation(
			org.modeldriven.alf.uml.Operation redefinedOperation) {
		this.getBase().getRedefinedOperations().add(
				redefinedOperation == null ? null
						: ((Operation) redefinedOperation).getBase());
	}

	public org.modeldriven.alf.uml.Type getType() {
		return (org.modeldriven.alf.uml.Type) wrap(this.getBase().getType());
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (org.eclipse.uml2.uml.Parameter element : this.getBase()
				.getOwnedParameters()) {
			list.add((org.modeldriven.alf.uml.Parameter) wrap(element));
		}
		return list;
	}

	public void addOwnedParameter(
			org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase().getOwnedParameters().add(
				ownedParameter == null ? null : ((Parameter) ownedParameter)
						.getBase());
	}

    @Override
    public void setClass_(org.modeldriven.alf.uml.Class_ class_) {
        this.getBase().setClass_(((Class_)class_).getBase());
    }
    
}

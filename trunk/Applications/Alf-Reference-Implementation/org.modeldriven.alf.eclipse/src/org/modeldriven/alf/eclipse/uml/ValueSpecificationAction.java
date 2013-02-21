/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class ValueSpecificationAction extends Action implements
		org.modeldriven.alf.uml.ValueSpecificationAction {
	public ValueSpecificationAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createValueSpecificationAction());
	}

	public ValueSpecificationAction(
			org.eclipse.uml2.uml.ValueSpecificationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ValueSpecificationAction getBase() {
		return (org.eclipse.uml2.uml.ValueSpecificationAction) this.base;
	}

	public org.modeldriven.alf.uml.ValueSpecification getValue() {
		return (org.modeldriven.alf.uml.ValueSpecification) wrap(this.getBase()
				.getValue());
	}

	public void setValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().setValue(
				value == null ? null : ((ValueSpecification) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;


public class ValueSpecificationAction extends Action implements
		org.modeldriven.alf.uml.ValueSpecificationAction {
	public ValueSpecificationAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction());
	}

	public ValueSpecificationAction(
			fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction) this.base;
	}

	public org.modeldriven.alf.uml.ValueSpecification getValue() {
		return (ValueSpecification)this.wrap(this.getBase().value);
	}

	public void setValue(org.modeldriven.alf.uml.ValueSpecification value) {
		this.getBase().setValue(((ValueSpecification) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}

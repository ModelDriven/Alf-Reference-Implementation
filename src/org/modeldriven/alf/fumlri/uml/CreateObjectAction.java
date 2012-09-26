/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class CreateObjectAction extends Action implements
		org.modeldriven.alf.uml.CreateObjectAction {
	public CreateObjectAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.CreateObjectAction());
	}

	public CreateObjectAction(
			fUML.Syntax.Actions.IntermediateActions.CreateObjectAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.CreateObjectAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.CreateObjectAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (OutputPin)this.wrap(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(result==null? null: ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.Classifier getClassifier() {
		return (Classifier)this.wrap(this.getBase().classifier);
	}

	public void setClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase().setClassifier(classifier==null? null: ((Classifier) classifier).getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class ReadExtentAction extends Action implements
		org.modeldriven.alf.uml.ReadExtentAction {
	public ReadExtentAction() {
		this(new fUML.Syntax.Actions.CompleteActions.ReadExtentAction());
	}

	public ReadExtentAction(
			fUML.Syntax.Actions.CompleteActions.ReadExtentAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.ReadExtentAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.ReadExtentAction) this.base;
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

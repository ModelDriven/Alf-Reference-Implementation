/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CreateObjectAction extends Action implements
		org.modeldriven.alf.uml.CreateObjectAction {
	public CreateObjectAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createCreateObjectAction());
	}

	public CreateObjectAction(org.eclipse.uml2.uml.CreateObjectAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CreateObjectAction getBase() {
		return (org.eclipse.uml2.uml.CreateObjectAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.Classifier getClassifier() {
		return (org.modeldriven.alf.uml.Classifier) wrap(this.getBase()
				.getClassifier());
	}

	public void setClassifier(org.modeldriven.alf.uml.Classifier classifier) {
		this.getBase()
				.setClassifier(
						classifier == null ? null : ((Classifier) classifier)
								.getBase());
	}

}

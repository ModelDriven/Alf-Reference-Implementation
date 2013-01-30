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

public class ReadIsClassifiedObjectAction extends Action implements
		org.modeldriven.alf.uml.ReadIsClassifiedObjectAction {
	public ReadIsClassifiedObjectAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createReadIsClassifiedObjectAction());
	}

	public ReadIsClassifiedObjectAction(
			org.eclipse.uml2.uml.ReadIsClassifiedObjectAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadIsClassifiedObjectAction getBase() {
		return (org.eclipse.uml2.uml.ReadIsClassifiedObjectAction) this.base;
	}

	public boolean getIsDirect() {
		return this.getBase().isDirect();
	}

	public void setIsDirect(boolean isDirect) {
		this.getBase().setIsDirect(isDirect);
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

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}

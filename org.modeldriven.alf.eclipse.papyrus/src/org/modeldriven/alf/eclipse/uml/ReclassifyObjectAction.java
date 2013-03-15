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

public class ReclassifyObjectAction extends Action implements
		org.modeldriven.alf.uml.ReclassifyObjectAction {
	public ReclassifyObjectAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createReclassifyObjectAction());
	}

	public ReclassifyObjectAction(
			org.eclipse.uml2.uml.ReclassifyObjectAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReclassifyObjectAction getBase() {
		return (org.eclipse.uml2.uml.ReclassifyObjectAction) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll();
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public List<org.modeldriven.alf.uml.Classifier> getOldClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getOldClassifiers()) {
			list.add((org.modeldriven.alf.uml.Classifier) wrap(element));
		}
		return list;
	}

	public void addOldClassifier(
			org.modeldriven.alf.uml.Classifier oldClassifier) {
		this.getBase().getOldClassifiers().add(
				oldClassifier == null ? null : ((Classifier) oldClassifier)
						.getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

	public List<org.modeldriven.alf.uml.Classifier> getNewClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getNewClassifiers()) {
			list.add((org.modeldriven.alf.uml.Classifier) wrap(element));
		}
		return list;
	}

	public void addNewClassifier(
			org.modeldriven.alf.uml.Classifier newClassifier) {
		this.getBase().getNewClassifiers().add(
				newClassifier == null ? null : ((Classifier) newClassifier)
						.getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class ReclassifyObjectAction extends Action implements
		org.modeldriven.alf.uml.ReclassifyObjectAction {
	public ReclassifyObjectAction() {
		this(new fUML.Syntax.Actions.CompleteActions.ReclassifyObjectAction());
	}

	public ReclassifyObjectAction(
			fUML.Syntax.Actions.CompleteActions.ReclassifyObjectAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.ReclassifyObjectAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.ReclassifyObjectAction) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll;
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public List<org.modeldriven.alf.uml.Classifier> getOldClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().oldClassifier) {
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

	public void addOldClassifier(org.modeldriven.alf.uml.Classifier oldClassifier) {
		this.getBase().addOldClassifier(((Classifier) oldClassifier).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(((InputPin) object).getBase());
	}

	public List<org.modeldriven.alf.uml.Classifier> getNewClassifier() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().newClassifier) {
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

	public void addNewClassifier(org.modeldriven.alf.uml.Classifier newClassifier) {
		this.getBase().addNewClassifier(((Classifier) newClassifier).getBase());
	}

}

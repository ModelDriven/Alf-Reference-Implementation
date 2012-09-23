package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.InputPin;

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
			list.add(new Classifier(element));
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
			list.add(new Classifier(element));
		}
		return list;
	}

	public void addNewClassifier(org.modeldriven.alf.uml.Classifier newClassifier) {
		this.getBase().addNewClassifier(((Classifier) newClassifier).getBase());
	}

}

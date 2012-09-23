package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Behavior;
import org.modeldriven.uml.fuml.Classifier;

public class BehavioredClassifier extends Classifier implements
		org.modeldriven.alf.uml.BehavioredClassifier {

	public BehavioredClassifier(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.BehavioredClassifier) this.base;
	}

	public List<org.modeldriven.alf.uml.Behavior> getOwnedBehavior() {
		List<org.modeldriven.alf.uml.Behavior> list = new ArrayList<org.modeldriven.alf.uml.Behavior>();
		for (fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior element : this
				.getBase().ownedBehavior) {
			list.add(new Behavior(element));
		}
		return list;
	}

	public void addOwnedBehavior(org.modeldriven.alf.uml.Behavior ownedBehavior) {
		this.getBase().addOwnedBehavior(((Behavior) ownedBehavior).getBase());
	}

	public org.modeldriven.alf.uml.Behavior getClassifierBehavior() {
		return new Behavior(this.getBase().classifierBehavior);
	}

	public void setClassifierBehavior(
			org.modeldriven.alf.uml.Behavior classifierBehavior) {
		this.getBase().setClassifierBehavior(
				((Behavior) classifierBehavior).getBase());
	}

}

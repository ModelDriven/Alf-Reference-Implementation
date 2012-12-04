package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class BehavioredClassifier extends Classifier implements
		org.modeldriven.alf.uml.BehavioredClassifier {

	public BehavioredClassifier(org.eclipse.uml2.uml.BehavioredClassifier base) {
		super(base);
	}

	public org.eclipse.uml2.uml.BehavioredClassifier getBase() {
		return (org.eclipse.uml2.uml.BehavioredClassifier) this.base;
	}

	public List< org.modeldriven.alf.uml.Behavior> getOwnedBehavior
() {
		List< org.modeldriven.alf.uml.Behavior> list = new ArrayList< org.modeldriven.alf.uml.Behavior>();
		for (org.eclipse.uml2.uml.Behavior
 element: this.getBase().getOwnedBehavior
s()) {
			list.add( new Behavior(element)
);
		}
		return list;
	}

	public void addOwnedBehavior
( org.modeldriven.alf.uml.Behavior ownedBehavior) {
		this.getBase().getOwnedBehavior
s.add( ownedBehavior == null? null: ((Behavior)ownedBehavior).getBase()
);
	}

	public org.modeldriven.alf.uml.Behavior getClassifierBehavior() {
		return new Behavior(this.getBase().getClassifierBehavior());
	}

	public void setClassifierBehavior(
			org.modeldriven.alf.uml.Behavior classifierBehavior) {
		this.getBase().setClassifierBehavior(
				classifierBehavior == null ? null
						: ((Behavior) classifierBehavior).getBase());
	}

}

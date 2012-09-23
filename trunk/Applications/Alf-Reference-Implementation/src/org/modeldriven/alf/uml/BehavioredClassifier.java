package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Classifier;

public interface BehavioredClassifier extends Classifier {
	public List<Behavior> getOwnedBehavior();

	public void addOwnedBehavior(Behavior ownedBehavior);

	public Behavior getClassifierBehavior();

	public void setClassifierBehavior(Behavior classifierBehavior);
}

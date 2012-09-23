package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.BehavioralFeature;
import org.modeldriven.alf.uml.BehavioredClassifier;
import org.modeldriven.alf.uml.Parameter;

public interface Behavior extends Class {
	public boolean getIsReentrant();

	public void setIsReentrant(boolean isReentrant);

	public BehavioralFeature getSpecification();

	public void setSpecification(BehavioralFeature specification);

	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

	public BehavioredClassifier getContext();
}

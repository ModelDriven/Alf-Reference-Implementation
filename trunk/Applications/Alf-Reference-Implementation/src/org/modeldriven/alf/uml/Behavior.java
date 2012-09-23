package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.BehavioralFeature;
import org.modeldriven.uml.BehavioredClassifier;
import org.modeldriven.uml.Parameter;

public interface Behavior extends Class {
	public boolean getIsReentrant();

	public void setIsReentrant(boolean isReentrant);

	public BehavioralFeature getSpecification();

	public void setSpecification(BehavioralFeature specification);

	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

	public BehavioredClassifier getContext();
}

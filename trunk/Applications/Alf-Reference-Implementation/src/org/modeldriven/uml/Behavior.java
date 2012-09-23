package org.modeldriven.uml;

import java.util.List;

public interface Behavior extends Class {
	public boolean getIsReentrant();

	public void setIsReentrant(boolean isReentrant);

	public BehavioralFeature getSpecification();

	public void setSpecification(BehavioralFeature specification);

	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

	public BehavioredClassifier getContext();
}

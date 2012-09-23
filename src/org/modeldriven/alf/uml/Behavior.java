package org.modeldriven.alf.uml;

import java.util.List;

public interface Behavior extends Class_ {
	public boolean getIsReentrant();

	public void setIsReentrant(boolean isReentrant);

	public BehavioralFeature getSpecification();

	public void setSpecification(BehavioralFeature specification);

	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

	public BehavioredClassifier getContext();
}

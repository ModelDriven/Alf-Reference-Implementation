package org.modeldriven.alf.uml;

import java.util.List;

public interface Class_ extends BehavioredClassifier {
	public boolean getIsAbstract();

	public void setIsAbstract(boolean isAbstract);

	public List<Operation> getOwnedOperation();

	public void addOwnedOperation(Operation ownedOperation);

	public List<Class_> getSuperClass();

	public boolean getIsActive();

	public void setIsActive(boolean isActive);

	public List<Reception> getOwnedReception();

	public void addOwnedReception(Reception ownedReception);

	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);

	public List<Classifier> getNestedClassifier();

	public void addNestedClassifier(Classifier nestedClassifier);
}

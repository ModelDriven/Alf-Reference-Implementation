package org.modeldriven.uml;

import java.util.List;

public interface Class extends BehavioredClassifier {
	public boolean getIsAbstract();

	public void setIsAbstract(boolean isAbstract);

	public List<Operation> getOwnedOperation();

	public void addOwnedOperation(Operation ownedOperation);

	public List<Class> getSuperClass();

	public boolean getIsActive();

	public void setIsActive(boolean isActive);

	public List<Reception> getOwnedReception();

	public void addOwnedReception(Reception ownedReception);

	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);

	public List<Classifier> getNestedClassifier();

	public void addNestedClassifier(Classifier nestedClassifier);
}

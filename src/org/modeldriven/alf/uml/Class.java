package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.BehavioredClassifier;
import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.Operation;
import org.modeldriven.uml.Property;
import org.modeldriven.uml.Reception;

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

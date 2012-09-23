package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.BehavioredClassifier;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Property;
import org.modeldriven.alf.uml.Reception;

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

package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.Property;
import org.modeldriven.uml.Type;

public interface Association extends Classifier {
	public boolean getIsDerived();

	public void setIsDerived(boolean isDerived);

	public List<Property> getOwnedEnd();

	public void addOwnedEnd(Property ownedEnd);

	public List<Type> getEndType();

	public List<Property> getMemberEnd();

	public List<Property> getNavigableOwnedEnd();

	public void addNavigableOwnedEnd(Property navigableOwnedEnd);
}

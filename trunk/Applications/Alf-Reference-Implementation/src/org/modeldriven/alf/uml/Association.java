package org.modeldriven.alf.uml;

import java.util.List;

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

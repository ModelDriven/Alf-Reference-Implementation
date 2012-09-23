package org.modeldriven.uml;

import java.util.List;

public interface Signal extends Classifier {
	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);
}

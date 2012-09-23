package org.modeldriven.alf.uml;

import java.util.List;

public interface DataType extends Classifier {
	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);
}

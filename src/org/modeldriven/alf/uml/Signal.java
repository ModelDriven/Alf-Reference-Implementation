package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.Property;

public interface Signal extends Classifier {
	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);
}

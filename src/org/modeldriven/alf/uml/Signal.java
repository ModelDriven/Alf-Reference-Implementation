package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Property;

public interface Signal extends Classifier {
	public List<Property> getOwnedAttribute();

	public void addOwnedAttribute(Property ownedAttribute);
}

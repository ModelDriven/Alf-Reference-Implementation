package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.Feature;
import org.modeldriven.uml.MultiplicityElement;
import org.modeldriven.uml.TypedElement;

public interface StructuralFeature extends Feature, TypedElement,
		MultiplicityElement {
	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);
}

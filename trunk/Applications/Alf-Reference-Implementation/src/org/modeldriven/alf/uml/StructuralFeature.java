package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Feature;
import org.modeldriven.alf.uml.MultiplicityElement;
import org.modeldriven.alf.uml.TypedElement;

public interface StructuralFeature extends Feature, TypedElement,
		MultiplicityElement {
	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);
}

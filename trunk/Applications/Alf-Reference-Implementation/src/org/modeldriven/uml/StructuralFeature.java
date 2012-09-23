package org.modeldriven.uml;

import java.util.List;

public interface StructuralFeature extends Feature, TypedElement,
		MultiplicityElement {
	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);
}

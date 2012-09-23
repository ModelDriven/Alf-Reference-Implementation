package org.modeldriven.alf.uml;


public interface StructuralFeature extends Feature, TypedElement,
		MultiplicityElement {
	public boolean getIsReadOnly();

	public void setIsReadOnly(boolean isReadOnly);
}

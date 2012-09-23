package org.modeldriven.alf.uml;


public interface StructuralFeatureAction extends Action {
	public StructuralFeature getStructuralFeature();

	public void setStructuralFeature(StructuralFeature structuralFeature);

	public InputPin getObject();

	public void setObject(InputPin object);
}

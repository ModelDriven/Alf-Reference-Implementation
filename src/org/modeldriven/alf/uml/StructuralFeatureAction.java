package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.uml.StructuralFeature;

public interface StructuralFeatureAction extends Action {
	public StructuralFeature getStructuralFeature();

	public void setStructuralFeature(StructuralFeature structuralFeature);

	public InputPin getObject();

	public void setObject(InputPin object);
}

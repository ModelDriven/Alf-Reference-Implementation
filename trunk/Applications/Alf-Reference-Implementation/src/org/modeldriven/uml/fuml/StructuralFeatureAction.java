package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.StructuralFeature;

public class StructuralFeatureAction extends Action implements
		org.modeldriven.alf.uml.StructuralFeatureAction {

	public StructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.StructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.StructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.StructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.StructuralFeature getStructuralFeature() {
		return new StructuralFeature(this.getBase().structuralFeature);
	}

	public void setStructuralFeature(
			org.modeldriven.alf.uml.StructuralFeature structuralFeature) {
		this.getBase().setStructuralFeature(
				((StructuralFeature) structuralFeature).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(((InputPin) object).getBase());
	}

}

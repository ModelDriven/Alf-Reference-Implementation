package org.modeldriven.alf.uml;


public interface WriteStructuralFeatureAction extends StructuralFeatureAction {
	public InputPin getValue();

	public void setValue(InputPin value);

	public OutputPin getResult();

	public void setResult(OutputPin result);
}

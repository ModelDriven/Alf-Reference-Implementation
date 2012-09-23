package org.modeldriven.alf.uml;


public interface ReadStructuralFeatureAction extends StructuralFeatureAction {
	public OutputPin getResult();

	public void setResult(OutputPin result);
}

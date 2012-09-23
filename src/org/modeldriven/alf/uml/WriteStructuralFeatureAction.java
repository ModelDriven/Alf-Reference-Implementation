package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.StructuralFeatureAction;

public interface WriteStructuralFeatureAction extends StructuralFeatureAction {
	public InputPin getValue();

	public void setValue(InputPin value);

	public OutputPin getResult();

	public void setResult(OutputPin result);
}

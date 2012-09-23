package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.StructuralFeatureAction;

public interface ReadStructuralFeatureAction extends StructuralFeatureAction {
	public OutputPin getResult();

	public void setResult(OutputPin result);
}

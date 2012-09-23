package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.ValueSpecification;

public interface ValueSpecificationAction extends Action {
	public ValueSpecification getValue();

	public void setValue(ValueSpecification value);

	public OutputPin getResult();

	public void setResult(OutputPin result);
}

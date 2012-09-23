package org.modeldriven.alf.uml;


public interface ValueSpecificationAction extends Action {
	public ValueSpecification getValue();

	public void setValue(ValueSpecification value);

	public OutputPin getResult();

	public void setResult(OutputPin result);
}

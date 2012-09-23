package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Behavior;
import org.modeldriven.uml.Feature;
import org.modeldriven.uml.Parameter;

public interface BehavioralFeature extends Feature {
	public List<Parameter> getOwnedParameter();

	public void addOwnedParameter(Parameter ownedParameter);

	public boolean getIsAbstract();

	public void setIsAbstract(boolean isAbstract);

	public List<Behavior> getMethod();

	public void addMethod(Behavior method);

	public String getConcurrency();

	public void setConcurrency(String concurrency);
}

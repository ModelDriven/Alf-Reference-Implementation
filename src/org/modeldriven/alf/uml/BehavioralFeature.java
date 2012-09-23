package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Feature;
import org.modeldriven.alf.uml.Parameter;

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

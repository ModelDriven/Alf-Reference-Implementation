package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ObjectNode;
import org.modeldriven.uml.Parameter;

public interface ActivityParameterNode extends ObjectNode {
	public Parameter getParameter();

	public void setParameter(Parameter parameter);
}

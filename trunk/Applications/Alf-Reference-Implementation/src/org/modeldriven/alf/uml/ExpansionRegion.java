package org.modeldriven.alf.uml;

import java.util.List;

public interface ExpansionRegion extends StructuredActivityNode {
	public String getMode();

	public void setMode(String mode);

	public List<ExpansionNode> getOutputElement();

	public void addOutputElement(ExpansionNode outputElement);

	public List<ExpansionNode> getInputElement();

	public void addInputElement(ExpansionNode inputElement);
}

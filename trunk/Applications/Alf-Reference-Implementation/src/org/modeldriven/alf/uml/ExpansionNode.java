package org.modeldriven.alf.uml;


public interface ExpansionNode extends ObjectNode {
	public ExpansionRegion getRegionAsOutput();

	public void setRegionAsOutput(ExpansionRegion regionAsOutput);

	public ExpansionRegion getRegionAsInput();

	public void setRegionAsInput(ExpansionRegion regionAsInput);
}

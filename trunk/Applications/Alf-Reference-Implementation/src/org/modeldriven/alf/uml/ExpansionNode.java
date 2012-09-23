package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ExpansionRegion;
import org.modeldriven.alf.uml.ObjectNode;

public interface ExpansionNode extends ObjectNode {
	public ExpansionRegion getRegionAsOutput();

	public void setRegionAsOutput(ExpansionRegion regionAsOutput);

	public ExpansionRegion getRegionAsInput();

	public void setRegionAsInput(ExpansionRegion regionAsInput);
}

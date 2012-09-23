package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.LinkEndDestructionData;
import org.modeldriven.alf.uml.WriteLinkAction;

public interface DestroyLinkAction extends WriteLinkAction {
	public List<LinkEndDestructionData> getEndData();

	public void addEndData(LinkEndDestructionData endData);
}

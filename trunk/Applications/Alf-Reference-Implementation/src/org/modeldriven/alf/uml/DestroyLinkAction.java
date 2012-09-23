package org.modeldriven.alf.uml;

import java.util.List;

public interface DestroyLinkAction extends WriteLinkAction {
	public List<LinkEndDestructionData> getEndData();

	public void addEndData(LinkEndDestructionData endData);
}

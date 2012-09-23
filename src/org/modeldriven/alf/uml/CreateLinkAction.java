package org.modeldriven.alf.uml;

import java.util.List;

public interface CreateLinkAction extends WriteLinkAction {
	public List<LinkEndCreationData> getEndData();

	public void addEndData(LinkEndCreationData endData);
}

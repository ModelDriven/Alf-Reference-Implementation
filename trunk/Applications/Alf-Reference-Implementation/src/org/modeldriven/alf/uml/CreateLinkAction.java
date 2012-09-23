package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.LinkEndCreationData;
import org.modeldriven.alf.uml.WriteLinkAction;

public interface CreateLinkAction extends WriteLinkAction {
	public List<LinkEndCreationData> getEndData();

	public void addEndData(LinkEndCreationData endData);
}

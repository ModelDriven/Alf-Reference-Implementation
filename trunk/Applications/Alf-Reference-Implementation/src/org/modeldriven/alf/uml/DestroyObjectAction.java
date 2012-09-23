package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;

public interface DestroyObjectAction extends Action {
	public boolean getIsDestroyLinks();

	public void setIsDestroyLinks(boolean isDestroyLinks);

	public boolean getIsDestroyOwnedObjects();

	public void setIsDestroyOwnedObjects(boolean isDestroyOwnedObjects);

	public InputPin getTarget();

	public void setTarget(InputPin target);
}

package org.modeldriven.alf.uml;


public interface DestroyObjectAction extends Action {
	public boolean getIsDestroyLinks();

	public void setIsDestroyLinks(boolean isDestroyLinks);

	public boolean getIsDestroyOwnedObjects();

	public void setIsDestroyOwnedObjects(boolean isDestroyOwnedObjects);

	public InputPin getTarget();

	public void setTarget(InputPin target);
}

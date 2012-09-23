package org.modeldriven.alf.uml;


public interface ClearAssociationAction extends Action {
	public Association getAssociation();

	public void setAssociation(Association association);

	public InputPin getObject();

	public void setObject(InputPin object);
}

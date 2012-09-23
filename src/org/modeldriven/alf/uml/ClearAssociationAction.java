package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.uml.Association;

public interface ClearAssociationAction extends Action {
	public Association getAssociation();

	public void setAssociation(Association association);

	public InputPin getObject();

	public void setObject(InputPin object);
}

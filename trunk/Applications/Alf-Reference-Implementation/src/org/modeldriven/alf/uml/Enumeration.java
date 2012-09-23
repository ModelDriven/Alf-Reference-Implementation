package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.EnumerationLiteral;

public interface Enumeration extends DataType {
	public List<EnumerationLiteral> getOwnedLiteral();

	public void addOwnedLiteral(EnumerationLiteral ownedLiteral);
}

package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.DataType;
import org.modeldriven.uml.EnumerationLiteral;

public interface Enumeration extends DataType {
	public List<EnumerationLiteral> getOwnedLiteral();

	public void addOwnedLiteral(EnumerationLiteral ownedLiteral);
}

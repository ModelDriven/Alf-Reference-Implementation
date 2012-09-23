package org.modeldriven.alf.uml;

import java.util.List;

public interface Enumeration extends DataType {
	public List<EnumerationLiteral> getOwnedLiteral();

	public void addOwnedLiteral(EnumerationLiteral ownedLiteral);
}

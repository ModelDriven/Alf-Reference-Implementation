package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.LiteralSpecification;

public interface LiteralString extends LiteralSpecification {
	public String getValue();

	public void setValue(String value);
}

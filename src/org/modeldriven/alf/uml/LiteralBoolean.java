package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.LiteralSpecification;

public interface LiteralBoolean extends LiteralSpecification {
	public boolean getValue();

	public void setValue(boolean value);
}

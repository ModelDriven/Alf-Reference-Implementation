package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.LiteralSpecification;

public interface LiteralInteger extends LiteralSpecification {
	public int getValue();

	public void setValue(int value);
}

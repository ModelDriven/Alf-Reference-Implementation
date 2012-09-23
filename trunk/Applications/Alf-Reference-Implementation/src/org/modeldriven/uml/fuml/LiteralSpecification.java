package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ValueSpecification;

public class LiteralSpecification extends ValueSpecification implements
		org.modeldriven.alf.uml.LiteralSpecification {

	public LiteralSpecification(
			fUML.Syntax.Classes.Kernel.LiteralSpecification base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralSpecification getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralSpecification) this.base;
	}

}

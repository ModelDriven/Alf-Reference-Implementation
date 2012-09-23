package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LiteralSpecification;

public class LiteralNull extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralNull {
	public LiteralNull() {
		this(new fUML.Syntax.Classes.Kernel.LiteralNull());
	}

	public LiteralNull(fUML.Syntax.Classes.Kernel.LiteralNull base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralNull getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralNull) this.base;
	}

}

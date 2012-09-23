package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.DataType;

public class PrimitiveType extends DataType implements
		org.modeldriven.alf.uml.PrimitiveType {
	public PrimitiveType() {
		this(new fUML.Syntax.Classes.Kernel.PrimitiveType());
	}

	public PrimitiveType(fUML.Syntax.Classes.Kernel.PrimitiveType base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.PrimitiveType getBase() {
		return (fUML.Syntax.Classes.Kernel.PrimitiveType) this.base;
	}

}

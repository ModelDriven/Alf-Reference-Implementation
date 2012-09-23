package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.OpaqueBehavior;

public class FunctionBehavior extends OpaqueBehavior implements
		org.modeldriven.alf.uml.FunctionBehavior {
	public FunctionBehavior() {
		this(new fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior());
	}

	public FunctionBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior) this.base;
	}

}

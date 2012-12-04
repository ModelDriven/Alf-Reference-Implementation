package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class FunctionBehavior extends OpaqueBehavior implements
		org.modeldriven.alf.uml.FunctionBehavior {
	public FunctionBehavior() {
		this(UMLFactory.eINSTANCE.createFunctionBehavior());
	}

	public FunctionBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.FunctionBehavior base) {
		super(base);
	}

	public org.eclipse.uml2.uml.FunctionBehavior getBase() {
		return (org.eclipse.uml2.uml.FunctionBehavior) this.base;
	}

}

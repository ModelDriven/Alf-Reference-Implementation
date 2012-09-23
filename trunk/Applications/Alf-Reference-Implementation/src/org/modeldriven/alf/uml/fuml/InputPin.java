package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Pin;

public class InputPin extends Pin implements org.modeldriven.alf.uml.InputPin {
	public InputPin() {
		this(new fUML.Syntax.Actions.BasicActions.InputPin());
	}

	public InputPin(fUML.Syntax.Actions.BasicActions.InputPin base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.InputPin getBase() {
		return (fUML.Syntax.Actions.BasicActions.InputPin) this.base;
	}

}

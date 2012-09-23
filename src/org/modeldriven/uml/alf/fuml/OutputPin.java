package org.modeldriven.uml.alf.fuml;


public class OutputPin extends Pin implements org.modeldriven.alf.uml.OutputPin {
	public OutputPin() {
		this(new fUML.Syntax.Actions.BasicActions.OutputPin());
	}

	public OutputPin(fUML.Syntax.Actions.BasicActions.OutputPin base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.OutputPin getBase() {
		return (fUML.Syntax.Actions.BasicActions.OutputPin) this.base;
	}

}

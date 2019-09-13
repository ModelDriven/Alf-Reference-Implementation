/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


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

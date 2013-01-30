/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class SendSignalAction extends InvocationAction implements
		org.modeldriven.alf.uml.SendSignalAction {
	public SendSignalAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createSendSignalAction());
	}

	public SendSignalAction(org.eclipse.uml2.uml.SendSignalAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.SendSignalAction getBase() {
		return (org.eclipse.uml2.uml.SendSignalAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getTarget());
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(
				target == null ? null : ((InputPin) target).getBase());
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return (org.modeldriven.alf.uml.Signal) wrap(this.getBase().getSignal());
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(
				signal == null ? null : ((Signal) signal).getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class SignalEvent extends MessageEvent implements
		org.modeldriven.alf.uml.SignalEvent {
	public SignalEvent() {
		this(new fUML.Syntax.CommonBehaviors.Communications.SignalEvent());
	}

	public SignalEvent(
			fUML.Syntax.CommonBehaviors.Communications.SignalEvent base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.SignalEvent getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.SignalEvent) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return (Signal)this.wrap(this.getBase().signal);
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(signal==null? null: ((Signal) signal).getBase());
	}

}

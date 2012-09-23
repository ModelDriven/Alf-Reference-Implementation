/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;


public class Reception extends BehavioralFeature implements
		org.modeldriven.alf.uml.Reception {
	public Reception() {
		this(new fUML.Syntax.CommonBehaviors.Communications.Reception());
	}

	public Reception(fUML.Syntax.CommonBehaviors.Communications.Reception base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Reception getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Reception) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().signal);
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(((Signal) signal).getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class Trigger extends NamedElement implements
		org.modeldriven.alf.uml.Trigger {
	public Trigger() {
		this(new fUML.Syntax.CommonBehaviors.Communications.Trigger());
	}

	public Trigger(fUML.Syntax.CommonBehaviors.Communications.Trigger base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Trigger getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Trigger) this.base;
	}

	public org.modeldriven.alf.uml.Event getEvent() {
		return (Event)wrap(this.getBase().event);
	}

	public void setEvent(org.modeldriven.alf.uml.Event event) {
		this.getBase().setEvent(event==null? null: ((Event) event).getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class Trigger extends NamedElement implements
		org.modeldriven.alf.uml.Trigger {
	public Trigger() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createTrigger());
	}

	public Trigger(org.eclipse.uml2.uml.Trigger base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Trigger getBase() {
		return (org.eclipse.uml2.uml.Trigger) this.base;
	}

	public org.modeldriven.alf.uml.Event getEvent() {
		return (org.modeldriven.alf.uml.Event) wrap(this.getBase().getEvent());
	}

	public void setEvent(org.modeldriven.alf.uml.Event event) {
		this.getBase().setEvent(
				event == null ? null : ((Event) event).getBase());
	}

}

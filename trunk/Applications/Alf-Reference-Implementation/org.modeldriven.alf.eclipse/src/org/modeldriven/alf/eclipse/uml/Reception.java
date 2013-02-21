/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class Reception extends BehavioralFeature implements
		org.modeldriven.alf.uml.Reception {
	public Reception() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createReception());
	}

	public Reception(org.eclipse.uml2.uml.Reception base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Reception getBase() {
		return (org.eclipse.uml2.uml.Reception) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return (org.modeldriven.alf.uml.Signal) wrap(this.getBase().getSignal());
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(
				signal == null ? null : ((Signal) signal).getBase());
	}

}

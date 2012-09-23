/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;


public abstract class Event extends PackageableElement implements
		org.modeldriven.alf.uml.Event {

	public Event(fUML.Syntax.CommonBehaviors.Communications.Event base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Event getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Event) this.base;
	}

}

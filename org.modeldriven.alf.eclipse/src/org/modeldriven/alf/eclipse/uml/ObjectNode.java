/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class ObjectNode extends ActivityNode implements
		org.modeldriven.alf.uml.ObjectNode {

	public ObjectNode(org.eclipse.uml2.uml.ObjectNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ObjectNode getBase() {
		return (org.eclipse.uml2.uml.ObjectNode) this.base;
	}

	public org.modeldriven.alf.uml.Type getType() {
		return (org.modeldriven.alf.uml.Type) wrap(this.getBase().getType());
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(type == null ? null : ((Type) type).getBase());
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class ExecutableNode extends ActivityNode implements
		org.modeldriven.alf.uml.ExecutableNode {

	public ExecutableNode(org.eclipse.uml2.uml.ExecutableNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ExecutableNode getBase() {
		return (org.eclipse.uml2.uml.ExecutableNode) this.base;
	}

}

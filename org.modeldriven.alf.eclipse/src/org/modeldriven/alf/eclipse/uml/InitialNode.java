/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class InitialNode extends ControlNode implements
		org.modeldriven.alf.uml.InitialNode {
	public InitialNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createInitialNode());
	}

	public InitialNode(org.eclipse.uml2.uml.InitialNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InitialNode getBase() {
		return (org.eclipse.uml2.uml.InitialNode) this.base;
	}

}

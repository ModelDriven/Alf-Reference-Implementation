/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class JoinNode extends ControlNode implements
		org.modeldriven.alf.uml.JoinNode {
	public JoinNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.JoinNode());
	}

	public JoinNode(fUML.Syntax.Activities.IntermediateActivities.JoinNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.JoinNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.JoinNode) this.base;
	}

}

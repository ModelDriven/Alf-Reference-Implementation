/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class ActivityNode extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityNode {

	public ActivityNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityNode) this.base;
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return (StructuredActivityNode)this.wrap(this.getBase().inStructuredNode);
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return (Activity)this.wrap(this.getBase().activity);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getOutgoing() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge element : this
				.getBase().outgoing) {
			list.add((ActivityEdge)this.wrap(element));
		}
		return list;
	}

	public void addOutgoing(org.modeldriven.alf.uml.ActivityEdge outgoing) {
		outgoing.setSource(this);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getIncoming() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge element : this
				.getBase().incoming) {
			list.add((ActivityEdge)this.wrap(element));
		}
		return list;
	}

	public void addIncoming(org.modeldriven.alf.uml.ActivityEdge incoming) {
		incoming.setTarget(this);
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;

import java.util.ArrayList;
import java.util.List;

public class Activity extends Behavior implements org.modeldriven.alf.uml.Activity {
	public Activity() {
		this(new fUML.Syntax.Activities.IntermediateActivities.Activity());
	}

	public Activity(fUML.Syntax.Activities.IntermediateActivities.Activity base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.Activity getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.Activity) this.base;
	}

	public List<org.modeldriven.alf.uml.StructuredActivityNode> getStructuredNode() {
		List<org.modeldriven.alf.uml.StructuredActivityNode> list = new ArrayList<org.modeldriven.alf.uml.StructuredActivityNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode element : this
				.getBase().structuredNode) {
			list.add(new StructuredActivityNode(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.ActivityNode> getNode() {
		List<org.modeldriven.alf.uml.ActivityNode> list = new ArrayList<org.modeldriven.alf.uml.ActivityNode>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityNode element : this
				.getBase().node) {
			list.add((ActivityNode)this.wrap(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
		this.getBase().addNode(node==null? null: ((ActivityNode) node).getBase());
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly;
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getEdge() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge element : this
				.getBase().edge) {
			list.add((ActivityEdge)this.wrap(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().addEdge(edge==null? null: ((ActivityEdge) edge).getBase());
	}

}

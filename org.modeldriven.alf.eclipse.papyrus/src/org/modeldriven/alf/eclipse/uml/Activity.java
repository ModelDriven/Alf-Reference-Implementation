/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Activity extends Behavior implements
		org.modeldriven.alf.uml.Activity {
	public Activity() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createActivity());
	}

	public Activity(org.eclipse.uml2.uml.Activity base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Activity getBase() {
		return (org.eclipse.uml2.uml.Activity) this.base;
	}

	public List<org.modeldriven.alf.uml.StructuredActivityNode> getStructuredNode() {
		List<org.modeldriven.alf.uml.StructuredActivityNode> list = new ArrayList<org.modeldriven.alf.uml.StructuredActivityNode>();
		for (org.eclipse.uml2.uml.StructuredActivityNode element : this
				.getBase().getStructuredNodes()) {
			list
					.add((org.modeldriven.alf.uml.StructuredActivityNode) wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.ActivityNode> getNode() {
		List<org.modeldriven.alf.uml.ActivityNode> list = new ArrayList<org.modeldriven.alf.uml.ActivityNode>();
		for (org.eclipse.uml2.uml.ActivityNode element : this.getBase()
				.getNodes()) {
			list.add((org.modeldriven.alf.uml.ActivityNode) wrap(element));
		}
		return list;
	}

	public void addNode(org.modeldriven.alf.uml.ActivityNode node) {
	    org.eclipse.uml2.uml.ActivityNode baseNode = node == null ? null : ((ActivityNode) node).getBase();
	    org.eclipse.uml2.uml.Activity base = this.getBase();
	    if (!(node instanceof StructuredActivityNode)) {
	        base.getOwnedNodes().add(baseNode);
	    } else {
	        base.getStructuredNodes().add((org.eclipse.uml2.uml.StructuredActivityNode)baseNode);
	    }
	}

	public boolean getIsReadOnly() {
		return this.getBase().isReadOnly();
	}

	public void setIsReadOnly(boolean isReadOnly) {
		this.getBase().setIsReadOnly(isReadOnly);
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getEdge() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getEdges()) {
			list.add((org.modeldriven.alf.uml.ActivityEdge) wrap(element));
		}
		return list;
	}

	public void addEdge(org.modeldriven.alf.uml.ActivityEdge edge) {
		this.getBase().getEdges().add(
				edge == null ? null : ((ActivityEdge) edge).getBase());
	}

}

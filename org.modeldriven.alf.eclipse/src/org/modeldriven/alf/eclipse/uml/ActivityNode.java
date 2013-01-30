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

public class ActivityNode extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityNode {

	public ActivityNode(org.eclipse.uml2.uml.ActivityNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityNode getBase() {
		return (org.eclipse.uml2.uml.ActivityNode) this.base;
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return (org.modeldriven.alf.uml.StructuredActivityNode) wrap(this
				.getBase().getInStructuredNode());
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return (org.modeldriven.alf.uml.Activity) wrap(this.getBase()
				.getActivity());
	}

	public List<org.modeldriven.alf.uml.ActivityEdge> getOutgoing() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getOutgoings()) {
			list.add((org.modeldriven.alf.uml.ActivityEdge) wrap(element));
		}
		return list;
	}

	public void addOutgoing(org.modeldriven.alf.uml.ActivityEdge outgoing) {
		this.getBase().getOutgoings().add(
				outgoing == null ? null : ((ActivityEdge) outgoing).getBase());
	}
	
	public void removeOutgoing(org.modeldriven.alf.uml.ActivityEdge outgoing) {
	    if (outgoing != null) {
	        this.getBase().getOutgoings().remove(((ActivityEdge) outgoing).getBase());
	    }
    }

	public List<org.modeldriven.alf.uml.ActivityEdge> getIncoming() {
		List<org.modeldriven.alf.uml.ActivityEdge> list = new ArrayList<org.modeldriven.alf.uml.ActivityEdge>();
		for (org.eclipse.uml2.uml.ActivityEdge element : this.getBase()
				.getIncomings()) {
			list.add((org.modeldriven.alf.uml.ActivityEdge) wrap(element));
		}
		return list;
	}

	public void addIncoming(org.modeldriven.alf.uml.ActivityEdge incoming) {
		this.getBase().getIncomings().add(
				incoming == null ? null : ((ActivityEdge) incoming).getBase());
	}

    public void removeIncoming(org.modeldriven.alf.uml.ActivityEdge incoming) {
        if (incoming != null) {
            this.getBase().getIncomings().remove(((ActivityEdge) incoming).getBase());
        }
    }

}

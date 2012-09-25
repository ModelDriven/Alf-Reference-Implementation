/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fumlri;


public abstract class ActivityEdge extends RedefinableElement implements
		org.modeldriven.alf.uml.ActivityEdge {

	public ActivityEdge(
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityEdge getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityEdge) this.base;
	}

	public org.modeldriven.alf.uml.Activity getActivity() {
		return (Activity)this.wrap(this.getBase().activity);
	}

	public org.modeldriven.alf.uml.ActivityNode getSource() {
		return (ActivityNode)this.wrap(this.getBase().source);
	}

	public void setSource(org.modeldriven.alf.uml.ActivityNode source) {
		this.getBase().setSource(source==null? null: ((ActivityNode) source).getBase());
	}

	public org.modeldriven.alf.uml.ActivityNode getTarget() {
		return (ActivityNode)this.wrap(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.ActivityNode target) {
		this.getBase().setTarget(target==null? null: ((ActivityNode) target).getBase());
	}

	public org.modeldriven.alf.uml.ValueSpecification getGuard() {
		return (ValueSpecification)this.wrap(this.getBase().guard);
	}

	public void setGuard(org.modeldriven.alf.uml.ValueSpecification guard) {
		this.getBase().setGuard(guard==null? null: ((ValueSpecification) guard).getBase());
	}

	public org.modeldriven.alf.uml.StructuredActivityNode getInStructuredNode() {
		return (StructuredActivityNode)this.wrap(this.getBase().inStructuredNode);
	}

}

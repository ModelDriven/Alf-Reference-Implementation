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

public class ActivityFinalNode extends FinalNode implements
		org.modeldriven.alf.uml.ActivityFinalNode {
	public ActivityFinalNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createActivityFinalNode());
	}

	public ActivityFinalNode(org.eclipse.uml2.uml.ActivityFinalNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityFinalNode getBase() {
		return (org.eclipse.uml2.uml.ActivityFinalNode) this.base;
	}

}

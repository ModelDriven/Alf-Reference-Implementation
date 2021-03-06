/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;

public interface ActivityNode extends RedefinableElement {
	public StructuredActivityNode getInStructuredNode();

	public Activity getActivity();

	public List<ActivityEdge> getOutgoing();

	public void addOutgoing(ActivityEdge outgoing);

	public void removeOutgoing(ActivityEdge outgoing);
	
	public List<ActivityEdge> getIncoming();

	public void addIncoming(ActivityEdge incoming);
	
	public void removeIncoming(ActivityEdge incoming);
}

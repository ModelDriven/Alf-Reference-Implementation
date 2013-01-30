/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;

public interface StructuredActivityNode extends Action {
	public List<ActivityNode> getNode();

	public void addNode(ActivityNode node);

	public Activity getActivity();

	public boolean getMustIsolate();

	public void setMustIsolate(boolean mustIsolate);

	public List<ActivityEdge> getEdge();

	public void addEdge(ActivityEdge edge);

	public List<OutputPin> getStructuredNodeOutput();

	public void addStructuredNodeOutput(OutputPin structuredNodeOutput);

	public List<InputPin> getStructuredNodeInput();

	public void addStructuredNodeInput(InputPin structuredNodeInput);
}

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

public interface AcceptEventAction extends Action {
	public boolean getIsUnmarshall();

	public void setIsUnmarshall(boolean isUnmarshall);

	public List<OutputPin> getResult();

	public void addResult(OutputPin result);

	public List<Trigger> getTrigger();

	public void addTrigger(Trigger trigger);
}

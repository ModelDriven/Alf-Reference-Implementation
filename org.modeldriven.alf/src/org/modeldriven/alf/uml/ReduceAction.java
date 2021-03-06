/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;


public interface ReduceAction extends Action {
	public Behavior getReducer();

	public void setReducer(Behavior reducer);

	public OutputPin getResult();

	public void setResult(OutputPin result);

	public InputPin getCollection();

	public void setCollection(InputPin collection);

	public boolean getIsOrdered();

	public void setIsOrdered(boolean isOrdered);
}

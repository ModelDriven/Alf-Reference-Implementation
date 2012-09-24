/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class LinkEndDestructionData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndDestructionData {
	public LinkEndDestructionData() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData());
	}

	public LinkEndDestructionData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData) this.base;
	}

	public boolean getIsDestroyDuplicates() {
		return this.getBase().isDestroyDuplicates;
	}

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates) {
		this.getBase().setIsDestroyDuplicates(isDestroyDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getDestroyAt() {
		return new InputPin(this.getBase().destroyAt);
	}

	public void setDestroyAt(org.modeldriven.alf.uml.InputPin destroyAt) {
		this.getBase().setDestroyAt(((InputPin) destroyAt).getBase());
	}

}

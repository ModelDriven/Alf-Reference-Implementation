/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class LinkEndCreationData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndCreationData {
	public LinkEndCreationData() {
		this(new fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData());
	}

	public LinkEndCreationData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll;
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public org.modeldriven.alf.uml.InputPin getInsertAt() {
		return (InputPin)this.wrap(this.getBase().insertAt);
	}

	public void setInsertAt(org.modeldriven.alf.uml.InputPin insertAt) {
		this.getBase().setInsertAt(insertAt==null? null: ((InputPin) insertAt).getBase());
	}

}

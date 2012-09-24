/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class LinkEndData extends Element implements
		org.modeldriven.alf.uml.LinkEndData {
	public LinkEndData() {
		this(new fUML.Syntax.Actions.IntermediateActions.LinkEndData());
	}

	public LinkEndData(fUML.Syntax.Actions.IntermediateActions.LinkEndData base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkEndData getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkEndData) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return new InputPin(this.getBase().value);
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.Property getEnd() {
		return new Property(this.getBase().end);
	}

	public void setEnd(org.modeldriven.alf.uml.Property end) {
		this.getBase().setEnd(((Property) end).getBase());
	}

}

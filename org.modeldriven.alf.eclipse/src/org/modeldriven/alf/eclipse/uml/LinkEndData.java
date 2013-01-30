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

public class LinkEndData extends Element implements
		org.modeldriven.alf.uml.LinkEndData {
	public LinkEndData() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLinkEndData());
	}

	public LinkEndData(org.eclipse.uml2.uml.LinkEndData base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LinkEndData getBase() {
		return (org.eclipse.uml2.uml.LinkEndData) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getValue());
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(
				value == null ? null : ((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.Property getEnd() {
		return (org.modeldriven.alf.uml.Property) wrap(this.getBase().getEnd());
	}

	public void setEnd(org.modeldriven.alf.uml.Property end) {
		this.getBase().setEnd(end == null ? null : ((Property) end).getBase());
	}

}

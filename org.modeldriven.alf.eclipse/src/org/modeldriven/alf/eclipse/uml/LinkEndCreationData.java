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

public class LinkEndCreationData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndCreationData {
	public LinkEndCreationData() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createLinkEndCreationData());
	}

	public LinkEndCreationData(org.eclipse.uml2.uml.LinkEndCreationData base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LinkEndCreationData getBase() {
		return (org.eclipse.uml2.uml.LinkEndCreationData) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll();
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public org.modeldriven.alf.uml.InputPin getInsertAt() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getInsertAt());
	}

	public void setInsertAt(org.modeldriven.alf.uml.InputPin insertAt) {
		this.getBase().setInsertAt(
				insertAt == null ? null : ((InputPin) insertAt).getBase());
	}

}

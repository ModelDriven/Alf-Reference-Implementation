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

public class CreateLinkAction extends WriteLinkAction implements
		org.modeldriven.alf.uml.CreateLinkAction {
	public CreateLinkAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createCreateLinkAction());
	}

	public CreateLinkAction(org.eclipse.uml2.uml.CreateLinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CreateLinkAction getBase() {
		return (org.eclipse.uml2.uml.CreateLinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndCreationData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndCreationData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndCreationData>();
		for (org.eclipse.uml2.uml.LinkEndData element : this.getBase()
				.getEndData()) {
			list
					.add((org.modeldriven.alf.uml.LinkEndCreationData) wrap(element));
		}
		return list;
	}

	public void addEndData(org.modeldriven.alf.uml.LinkEndCreationData endData) {
		this.getBase().getEndData().add(
				endData == null ? null : ((LinkEndCreationData) endData)
						.getBase());
	}

}

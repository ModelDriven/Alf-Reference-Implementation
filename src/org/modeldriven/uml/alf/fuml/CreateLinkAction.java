/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class CreateLinkAction extends WriteLinkAction implements
		org.modeldriven.alf.uml.CreateLinkAction {
	public CreateLinkAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.CreateLinkAction());
	}

	public CreateLinkAction(
			fUML.Syntax.Actions.IntermediateActions.CreateLinkAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.CreateLinkAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.CreateLinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndCreationData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndCreationData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndCreationData>();
		for (fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData element : this
				.getBase().endData) {
			list.add(new LinkEndCreationData(element));
		}
		return list;
	}

	public void addEndData(org.modeldriven.alf.uml.LinkEndCreationData endData) {
		this.getBase().addEndData(((LinkEndCreationData) endData).getBase());
	}

}

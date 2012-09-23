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

public class DestroyLinkAction extends WriteLinkAction implements
		org.modeldriven.alf.uml.DestroyLinkAction {
	public DestroyLinkAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.DestroyLinkAction());
	}

	public DestroyLinkAction(
			fUML.Syntax.Actions.IntermediateActions.DestroyLinkAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.DestroyLinkAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.DestroyLinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndDestructionData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndDestructionData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndDestructionData>();
		for (fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData element : this
				.getBase().endData) {
			list.add(new LinkEndDestructionData(element));
		}
		return list;
	}

	public void addEndData(org.modeldriven.alf.uml.LinkEndDestructionData endData) {
		this.getBase().addEndData(((LinkEndDestructionData) endData).getBase());
	}

}

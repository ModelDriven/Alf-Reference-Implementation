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

public class WriteLinkAction extends LinkAction implements
		org.modeldriven.alf.uml.WriteLinkAction {

	public WriteLinkAction(org.eclipse.uml2.uml.WriteLinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.WriteLinkAction getBase() {
		return (org.eclipse.uml2.uml.WriteLinkAction) this.base;
	}

}

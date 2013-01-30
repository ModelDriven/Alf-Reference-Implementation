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

public class ReadSelfAction extends Action implements
		org.modeldriven.alf.uml.ReadSelfAction {
	public ReadSelfAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createReadSelfAction());
	}

	public ReadSelfAction(org.eclipse.uml2.uml.ReadSelfAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadSelfAction getBase() {
		return (org.eclipse.uml2.uml.ReadSelfAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}

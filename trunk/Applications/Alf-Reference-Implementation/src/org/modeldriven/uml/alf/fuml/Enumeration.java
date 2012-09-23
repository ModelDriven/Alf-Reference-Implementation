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

public class Enumeration extends DataType implements
		org.modeldriven.alf.uml.Enumeration {
	public Enumeration() {
		this(new fUML.Syntax.Classes.Kernel.Enumeration());
	}

	public Enumeration(fUML.Syntax.Classes.Kernel.Enumeration base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Enumeration getBase() {
		return (fUML.Syntax.Classes.Kernel.Enumeration) this.base;
	}

	public List<org.modeldriven.alf.uml.EnumerationLiteral> getOwnedLiteral() {
		List<org.modeldriven.alf.uml.EnumerationLiteral> list = new ArrayList<org.modeldriven.alf.uml.EnumerationLiteral>();
		for (fUML.Syntax.Classes.Kernel.EnumerationLiteral element : this
				.getBase().ownedLiteral) {
			list.add(new EnumerationLiteral(element));
		}
		return list;
	}

	public void addOwnedLiteral(
			org.modeldriven.alf.uml.EnumerationLiteral ownedLiteral) {
		this.getBase().addOwnedLiteral(
				((EnumerationLiteral) ownedLiteral).getBase());
	}

}

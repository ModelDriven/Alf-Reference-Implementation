/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;

import java.util.ArrayList;
import java.util.List;

public class DataType extends Classifier implements
		org.modeldriven.alf.uml.DataType {
	public DataType() {
		this(new fUML.Syntax.Classes.Kernel.DataType());
	}

	public DataType(fUML.Syntax.Classes.Kernel.DataType base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.DataType getBase() {
		return (fUML.Syntax.Classes.Kernel.DataType) this.base;
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().ownedAttribute) {
			list.add(new Property(element));
		}
		return list;
	}

	public void addOwnedAttribute(org.modeldriven.alf.uml.Property ownedAttribute) {
		this.getBase().addOwnedAttribute(ownedAttribute==null? null: ((Property) ownedAttribute).getBase());
	}

}

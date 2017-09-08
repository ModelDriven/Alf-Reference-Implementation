/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library;

public class Value implements org.modeldriven.alf.fuml.library.Value {

	private final org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue base;
	
	public Value(org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue base) {
		this.base = base;
	}
	
	public org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue getBase() {
		return this.base;
	}
	
	public boolean equals(Object other) {
		return other instanceof Value && this.base.equals(((Value)other).getBase());
	}
}

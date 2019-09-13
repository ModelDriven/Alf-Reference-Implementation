/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public abstract class Type extends Namespace implements
		org.modeldriven.alf.uml.Type {

	public Type(fUML.Syntax.Classes.Kernel.Type base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Type getBase() {
		return (fUML.Syntax.Classes.Kernel.Type) this.base;
	}

	public org.modeldriven.alf.uml.Package getPackage() {
		return (Package)wrap(this.getBase().package_);
	}

}

/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;


public class Generalization extends Element implements
		org.modeldriven.alf.uml.Generalization {
	public Generalization() {
		this(new fUML.Syntax.Classes.Kernel.Generalization());
	}

	public Generalization(fUML.Syntax.Classes.Kernel.Generalization base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Generalization getBase() {
		return (fUML.Syntax.Classes.Kernel.Generalization) this.base;
	}

	public boolean getIsSubstitutable() {
		return this.getBase().isSubstitutable;
	}

	public void setIsSubstitutable(boolean isSubstitutable) {
		this.getBase().setIsSubstitutable(isSubstitutable);
	}

	public org.modeldriven.alf.uml.Classifier getSpecific() {
		return (Classifier)this.wrap(this.getBase().specific);
	}

	public org.modeldriven.alf.uml.Classifier getGeneral() {
		return (Classifier)this.wrap(this.getBase().general);
	}

	public void setGeneral(org.modeldriven.alf.uml.Classifier general) {
		this.getBase().setGeneral(((Classifier) general).getBase());
	}

}

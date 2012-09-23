/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;


public class ElementImport extends Element implements
		org.modeldriven.alf.uml.ElementImport {
	public ElementImport() {
		this(new fUML.Syntax.Classes.Kernel.ElementImport());
	}

	public ElementImport(fUML.Syntax.Classes.Kernel.ElementImport base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.ElementImport getBase() {
		return (fUML.Syntax.Classes.Kernel.ElementImport) this.base;
	}

	public String getVisibility() {
		return this.getBase().visibility.toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility));
	}

	public String getAlias() {
		return this.getBase().alias;
	}

	public void setAlias(String alias) {
		this.getBase().setAlias(alias);
	}

	public org.modeldriven.alf.uml.PackageableElement getImportedElement() {
		return (PackageableElement)this.wrap(this.getBase().importedElement);
	}

	public void setImportedElement(
			org.modeldriven.alf.uml.PackageableElement importedElement) {
		this.getBase().setImportedElement(
				((PackageableElement) importedElement).getBase());
	}

	public org.modeldriven.alf.uml.Namespace getImportingNamespace() {
		return (Namespace)this.wrap(this.getBase().importingNamespace);
	}

}

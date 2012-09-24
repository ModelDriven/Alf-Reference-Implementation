/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class PackageImport extends Element implements
		org.modeldriven.alf.uml.PackageImport {
	public PackageImport() {
		this(new fUML.Syntax.Classes.Kernel.PackageImport());
	}

	public PackageImport(fUML.Syntax.Classes.Kernel.PackageImport base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.PackageImport getBase() {
		return (fUML.Syntax.Classes.Kernel.PackageImport) this.base;
	}

	public String getVisibility() {
        String visibility = this.getBase().visibility.toString();
        return visibility == null? null: visibility;
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(visibility == null? null:
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility));
	}

	public org.modeldriven.alf.uml.Namespace getImportingNamespace() {
		return (Namespace)this.wrap(this.getBase().importingNamespace);
	}

	public org.modeldriven.alf.uml.Package getImportedPackage() {
		return (Package)this.wrap(this.getBase().importedPackage);
	}

	public void setImportedPackage(org.modeldriven.alf.uml.Package importedPackage) {
		this.getBase()
				.setImportedPackage(importedPackage==null? null: ((Package) importedPackage).getBase());
	}

}

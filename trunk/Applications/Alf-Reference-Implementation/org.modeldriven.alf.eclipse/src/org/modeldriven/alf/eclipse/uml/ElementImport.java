/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class ElementImport extends Element implements
		org.modeldriven.alf.uml.ElementImport {
	public ElementImport() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createElementImport());
	}

	public ElementImport(org.eclipse.uml2.uml.ElementImport base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ElementImport getBase() {
		return (org.eclipse.uml2.uml.ElementImport) this.base;
	}

	public String getVisibility() {
		return this.getBase().getVisibility().toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				org.eclipse.uml2.uml.VisibilityKind.get(visibility));
	}

	public String getAlias() {
		return this.getBase().getAlias();
	}

	public void setAlias(String alias) {
		this.getBase().setAlias(alias);
	}

	public org.modeldriven.alf.uml.PackageableElement getImportedElement() {
		return (org.modeldriven.alf.uml.PackageableElement) wrap(this.getBase()
				.getImportedElement());
	}

	public void setImportedElement(
			org.modeldriven.alf.uml.PackageableElement importedElement) {
		this.getBase().setImportedElement(
				importedElement == null ? null:
				        // NOTE: The Package interface extends both Namespace and PackageableElement,
				        // but the Package implementation class can only singly extend Namespace.
						(org.eclipse.uml2.uml.PackageableElement)((Element) importedElement).getBase());
	}

	public org.modeldriven.alf.uml.Namespace getImportingNamespace() {
		return (org.modeldriven.alf.uml.Namespace) wrap(this.getBase()
				.getImportingNamespace());
	}

}

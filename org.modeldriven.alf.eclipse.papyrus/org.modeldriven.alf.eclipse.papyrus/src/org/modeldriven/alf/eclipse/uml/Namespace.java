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

public class Namespace extends NamedElement implements
		org.modeldriven.alf.uml.Namespace {

	public Namespace(org.eclipse.uml2.uml.Namespace base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Namespace getBase() {
		return (org.eclipse.uml2.uml.Namespace) this.base;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement element : this.getBase()
				.getMembers()) {
			list.add((org.modeldriven.alf.uml.NamedElement) wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getOwnedMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement element : this.getBase()
				.getOwnedMembers()) {
			list.add((org.modeldriven.alf.uml.NamedElement) wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.ElementImport> getElementImport() {
		List<org.modeldriven.alf.uml.ElementImport> list = new ArrayList<org.modeldriven.alf.uml.ElementImport>();
		for (org.eclipse.uml2.uml.ElementImport element : this.getBase()
				.getElementImports()) {
			list.add((org.modeldriven.alf.uml.ElementImport) wrap(element));
		}
		return list;
	}

	public void addElementImport(
			org.modeldriven.alf.uml.ElementImport elementImport) {
		this.getBase().getElementImports().add(
				elementImport == null ? null : ((ElementImport) elementImport)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.PackageImport> getPackageImport() {
		List<org.modeldriven.alf.uml.PackageImport> list = new ArrayList<org.modeldriven.alf.uml.PackageImport>();
		for (org.eclipse.uml2.uml.PackageImport element : this.getBase()
				.getPackageImports()) {
			list.add((org.modeldriven.alf.uml.PackageImport) wrap(element));
		}
		return list;
	}

	public void addPackageImport(
			org.modeldriven.alf.uml.PackageImport packageImport) {
		this.getBase().getPackageImports().add(
				packageImport == null ? null : ((PackageImport) packageImport)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.PackageableElement> getImportedMember() {
		List<org.modeldriven.alf.uml.PackageableElement> list = new ArrayList<org.modeldriven.alf.uml.PackageableElement>();
		for (org.eclipse.uml2.uml.PackageableElement element : this.getBase()
				.getImportedMembers()) {
			list
					.add((org.modeldriven.alf.uml.PackageableElement) wrap(element));
		}
		return list;
	}

    @Override
    public List<String> getNamesOfMember(org.modeldriven.alf.uml.NamedElement member) {
        return this.getBase().getNamesOfMember(((NamedElement)member).getBase());
    }

}

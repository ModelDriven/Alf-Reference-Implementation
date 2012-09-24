/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;

import java.util.ArrayList;
import java.util.List;

public abstract class Namespace extends NamedElement implements
		org.modeldriven.alf.uml.Namespace {

	public Namespace(fUML.Syntax.Classes.Kernel.Namespace base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Namespace getBase() {
		return (fUML.Syntax.Classes.Kernel.Namespace) this.base;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (fUML.Syntax.Classes.Kernel.NamedElement element : this.getBase().member) {
			list.add((NamedElement)this.wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getOwnedMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (fUML.Syntax.Classes.Kernel.NamedElement element : this.getBase().ownedMember) {
			list.add((NamedElement)this.wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.ElementImport> getElementImport() {
		List<org.modeldriven.alf.uml.ElementImport> list = new ArrayList<org.modeldriven.alf.uml.ElementImport>();
		for (fUML.Syntax.Classes.Kernel.ElementImport element : this.getBase().elementImport) {
			list.add(new ElementImport(element));
		}
		return list;
	}

	public void addElementImport(org.modeldriven.alf.uml.ElementImport elementImport) {
		this.getBase().addElementImport(
				((ElementImport) elementImport).getBase());
	}

	public List<org.modeldriven.alf.uml.PackageImport> getPackageImport() {
		List<org.modeldriven.alf.uml.PackageImport> list = new ArrayList<org.modeldriven.alf.uml.PackageImport>();
		for (fUML.Syntax.Classes.Kernel.PackageImport element : this.getBase().packageImport) {
			list.add(new PackageImport(element));
		}
		return list;
	}

	public void addPackageImport(org.modeldriven.alf.uml.PackageImport packageImport) {
		this.getBase().addPackageImport(
				((PackageImport) packageImport).getBase());
	}

	public List<org.modeldriven.alf.uml.PackageableElement> getImportedMember() {
		List<org.modeldriven.alf.uml.PackageableElement> list = new ArrayList<org.modeldriven.alf.uml.PackageableElement>();
		for (fUML.Syntax.Classes.Kernel.PackageableElement element : this
				.getBase().importedMember) {
			list.add((PackageableElement)this.wrap(element));
		}
		return list;
	}

    @Override
    public List<String> getNamesOfMember(org.modeldriven.alf.uml.NamedElement member) {
        List<String> names = new ArrayList<String>();
        fUML.Syntax.Classes.Kernel.NamedElement base = ((NamedElement)member).getBase();
        for (org.modeldriven.alf.uml.NamedElement ownedMember: this.getOwnedMember()) {
            if (((NamedElement)ownedMember).getBase() == base) {
                names.add(member.getName());
            }
        }
        if (names.isEmpty()) {
            for (org.modeldriven.alf.uml.ElementImport elementImport: this.getElementImport()) {
                if (((Element)elementImport.getImportedElement()).getBase() == base) {
                    names.add(elementImport.getAlias() != null? 
                            elementImport.getAlias(): 
                            elementImport.getImportedElement().getName());
                }
            }
        }
        // TODO: Correct this to work in the case of cyclic package imports.
        if (names.isEmpty()) {
            for (org.modeldriven.alf.uml.PackageImport packageImport: this.getPackageImport()) {
                for (org.modeldriven.alf.uml.NamedElement namedElement: 
                    packageImport.getImportedPackage().visibleMembers()) {
                    if (((NamedElement)namedElement).getBase() == base) {
                        names.addAll(packageImport.getImportedPackage().getNamesOfMember(member));
                    }
                }
            }
        }
        return names;
    }

}

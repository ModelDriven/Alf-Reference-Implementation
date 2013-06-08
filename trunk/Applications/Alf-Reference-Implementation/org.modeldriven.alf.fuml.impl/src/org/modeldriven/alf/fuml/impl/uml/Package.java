/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.uml.ProfileApplication;

public class Package extends Namespace implements org.modeldriven.alf.uml.Package {

    public Package() {
		this(new fUML.Syntax.Classes.Kernel.Package());
	}

	public Package(fUML.Syntax.Classes.Kernel.Package base) {
		super(base);
	}

    @Override
	public fUML.Syntax.Classes.Kernel.Package getBase() {
		return (fUML.Syntax.Classes.Kernel.Package) this.base;
	}

    @Override
	public List<org.modeldriven.alf.uml.PackageableElement> getPackagedElement() {
		List<org.modeldriven.alf.uml.PackageableElement> list = new ArrayList<org.modeldriven.alf.uml.PackageableElement>();
		for (fUML.Syntax.Classes.Kernel.PackageableElement element : this
				.getBase().packagedElement) {
			list.add((PackageableElement)wrap(element));
		}
		return list;
	}

    @Override
	public void addPackagedElement(
			org.modeldriven.alf.uml.PackageableElement packagedElement) {
	    // NOTE: Instance specifications are (erroneously) not packageable
	    // elements in fUML.
	    if (!(packagedElement instanceof org.modeldriven.alf.uml.InstanceSpecification)) {
    		this.getBase().addPackagedElement(
    				((PackageableElement) packagedElement).getBase());
	    }
	}

    @Override
    public List<ProfileApplication> getProfileApplication() {
        return new ArrayList<ProfileApplication>();
    }

    @Override
    public void addProfileApplication(ProfileApplication profileApplication) {
    }

    @Override
	public List<org.modeldriven.alf.uml.Type> getOwnedType() {
		List<org.modeldriven.alf.uml.Type> list = new ArrayList<org.modeldriven.alf.uml.Type>();
		for (fUML.Syntax.Classes.Kernel.Type element : this.getBase().ownedType) {
			list.add((Type)wrap(element));
		}
		return list;
	}

    @Override
	public List<org.modeldriven.alf.uml.Package> getNestedPackage() {
		List<org.modeldriven.alf.uml.Package> list = new ArrayList<org.modeldriven.alf.uml.Package>();
		for (fUML.Syntax.Classes.Kernel.Package element : this.getBase().nestedPackage) {
			list.add(new Package(element));
		}
		return list;
	}

    @Override
	public org.modeldriven.alf.uml.Package getNestingPackage() {
		return (Package)wrap(this.getBase().nestingPackage);
	}

    @Override
    public List<org.modeldriven.alf.uml.NamedElement> visibleMembers() {
        List<org.modeldriven.alf.uml.NamedElement> visibleMembers = 
            new ArrayList<org.modeldriven.alf.uml.NamedElement>();
        fUML.Syntax.Classes.Kernel.Package base = this.getBase();
        for (fUML.Syntax.Classes.Kernel.NamedElement member: base.member) {
            boolean addMember = false;
            if (base.ownedMember.contains(member) && 
                    member.visibility == fUML.Syntax.Classes.Kernel.VisibilityKind.public_) {
                addMember = true;;
            } else {
                for (fUML.Syntax.Classes.Kernel.ElementImport elementImport: 
                    base.elementImport) {
                    if (elementImport.visibility == fUML.Syntax.Classes.Kernel.VisibilityKind.public_ && 
                            elementImport.importedElement == member) {
                        addMember = true;
                        break;
                    }
                }
                if (!addMember) {
                    for (fUML.Syntax.Classes.Kernel.PackageImport packageImport: 
                        base.packageImport) {
                        if (packageImport.visibility == fUML.Syntax.Classes.Kernel.VisibilityKind.public_ &&
                                packageImport.importedPackage.member.contains(member)) {
                            addMember = true;
                            break;
                        }
                    }
                }
            }
            if (addMember) {
                visibleMembers.add((NamedElement)wrap(member));
            }
        }
        return visibleMembers;
    }

}

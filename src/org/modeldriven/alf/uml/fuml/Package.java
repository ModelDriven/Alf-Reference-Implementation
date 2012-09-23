package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.NamedElement;
import org.modeldriven.uml.fuml.Namespace;
import org.modeldriven.uml.fuml.Package;
import org.modeldriven.uml.fuml.PackageableElement;
import org.modeldriven.uml.fuml.Type;

public class Package extends Namespace implements org.modeldriven.alf.uml.Package {
	public Package() {
		this(new fUML.Syntax.Classes.Kernel.Package());
	}

	public Package(fUML.Syntax.Classes.Kernel.Package base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Package getBase() {
		return (fUML.Syntax.Classes.Kernel.Package) this.base;
	}

	public List<org.modeldriven.alf.uml.PackageableElement> getPackagedElement() {
		List<org.modeldriven.alf.uml.PackageableElement> list = new ArrayList<org.modeldriven.alf.uml.PackageableElement>();
		for (fUML.Syntax.Classes.Kernel.PackageableElement element : this
				.getBase().packagedElement) {
			list.add(new PackageableElement(element));
		}
		return list;
	}

	public void addPackagedElement(
			org.modeldriven.alf.uml.PackageableElement packagedElement) {
		this.getBase().addPackagedElement(
				((PackageableElement) packagedElement).getBase());
	}

	public List<org.modeldriven.alf.uml.Type> getOwnedType() {
		List<org.modeldriven.alf.uml.Type> list = new ArrayList<org.modeldriven.alf.uml.Type>();
		for (fUML.Syntax.Classes.Kernel.Type element : this.getBase().ownedType) {
			list.add(new Type(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Package> getNestedPackage() {
		List<org.modeldriven.alf.uml.Package> list = new ArrayList<org.modeldriven.alf.uml.Package>();
		for (fUML.Syntax.Classes.Kernel.Package element : this.getBase().nestedPackage) {
			list.add(new Package(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Package getNestingPackage() {
		return new Package(this.getBase().nestingPackage);
	}

    @Override
    public List<org.modeldriven.alf.uml.NamedElement> visibleMembers() {
        List<org.modeldriven.alf.uml.NamedElement> visibleMembers = 
            new ArrayList<org.modeldriven.alf.uml.NamedElement>();
        fUML.Syntax.Classes.Kernel.Package base = this.getBase();
        for (fUML.Syntax.Classes.Kernel.NamedElement member: base.member) {
            boolean addMember = false;
            if (base.ownedMember.contains(member)) {
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
                if (addMember) {
                    visibleMembers.add(new NamedElement(member));
                }
            }
        }
        return visibleMembers;
    }

}

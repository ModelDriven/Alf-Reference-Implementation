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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Classifier extends Type implements org.modeldriven.alf.uml.Classifier {

	public Classifier(fUML.Syntax.Classes.Kernel.Classifier base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Classifier getBase() {
		return (fUML.Syntax.Classes.Kernel.Classifier) this.base;
	}

	public boolean getIsAbstract() {
		return this.getBase().isAbstract;
	}

	public void setIsAbstract(boolean isAbstract) {
		this.getBase().setIsAbstract(isAbstract);
	}

	public List<org.modeldriven.alf.uml.Generalization> getGeneralization() {
		List<org.modeldriven.alf.uml.Generalization> list = new ArrayList<org.modeldriven.alf.uml.Generalization>();
		for (fUML.Syntax.Classes.Kernel.Generalization element : this.getBase().generalization) {
			list.add(new Generalization(element));
		}
		return list;
	}

	public void addGeneralization(
			org.modeldriven.alf.uml.Generalization generalization) {
		this.getBase().addGeneralization(
				((Generalization) generalization).getBase());
	}

	public List<org.modeldriven.alf.uml.Feature> getFeature() {
		List<org.modeldriven.alf.uml.Feature> list = new ArrayList<org.modeldriven.alf.uml.Feature>();
		for (fUML.Syntax.Classes.Kernel.Feature element : this.getBase().feature) {
			list.add((Feature)this.wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getInheritedMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (fUML.Syntax.Classes.Kernel.NamedElement element : this.getBase().inheritedMember) {
			list.add((NamedElement)this.wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Property> getAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().attribute) {
			list.add(new Property(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Classifier> getGeneral() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (fUML.Syntax.Classes.Kernel.Classifier element : this.getBase().general) {
			list.add((Classifier)this.wrap(element));
		}
		return list;
	}

	public boolean getIsFinalSpecialization() {
		return this.getBase().isFinalSpecialization;
	}

	public void setIsFinalSpecialization(boolean isFinalSpecialization) {
		this.getBase().setIsFinalSpecialization(isFinalSpecialization);
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
    public Set<org.modeldriven.alf.uml.Classifier> parents() {
        return new HashSet<org.modeldriven.alf.uml.Classifier>(this.getGeneral());
    }

    @Override
    public Set<org.modeldriven.alf.uml.Classifier> allParents() {
        return this.allParents(new HashSet<org.modeldriven.alf.uml.Classifier>());
    }
    
    // This will work even if there are (illegal) cyclic generalization relationships.
    private Set<org.modeldriven.alf.uml.Classifier> allParents(Collection<org.modeldriven.alf.uml.Classifier> alreadySeen) {
        Set<org.modeldriven.alf.uml.Classifier> parents = this.parents();
        parents.removeAll(alreadySeen);
        Set<org.modeldriven.alf.uml.Classifier> allParents = new HashSet<org.modeldriven.alf.uml.Classifier>(parents);
        for (org.modeldriven.alf.uml.Classifier parent: parents) {
            alreadySeen.add(parent);
            allParents.addAll(((Classifier)parent).allParents(alreadySeen));
        }
        return allParents;
    }

    @Override
    public boolean conformsTo(org.modeldriven.alf.uml.Classifier classifier) {
        if (classifier == null) {
            return true;
        }
        for (org.modeldriven.alf.uml.Classifier parent: this.allParents()) {
            if (((Classifier)parent).getBase() == ((Classifier)classifier).getBase()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<org.modeldriven.alf.uml.NamedElement> inheritableMembers() {
        List<org.modeldriven.alf.uml.NamedElement> inheritable = 
            new ArrayList<org.modeldriven.alf.uml.NamedElement>();
        for (org.modeldriven.alf.uml.NamedElement member: this.getMember()) {
            if (!"private".equals(member.getVisibility())) {
                inheritable.add(member);
            }
        }
        return inheritable;
    }

    @Override
    public List<String> getNamesOfMember(org.modeldriven.alf.uml.NamedElement member) {
        return ((Namespace)this.wrap(this.getBase())).getNamesOfMember(member);
    }
    
    @Override
    public org.modeldriven.alf.uml.TemplateSignature getOwnedTemplateSignature() {
        return null;
    }
    
    public void setOwnedTemplateSignature(org.modeldriven.alf.uml.TemplateSignature signature) {
    }
    
    @Override
    public boolean isTemplate() {
        return false;
    }
    
    @Override
    public List<org.modeldriven.alf.uml.TemplateBinding> getTemplateBinding() {
        return null;
    }
    
    @Override
    public void addTemplateBinding(org.modeldriven.alf.uml.TemplateBinding templateBinding) {
    }

    @Override
    public org.modeldriven.alf.uml.TemplateParameter getTemplateParameter() {
        return null;
    }
    
    @Override
    public org.modeldriven.alf.uml.TemplateableElement instantiate(
            Collection<org.modeldriven.alf.uml.StereotypeApplication> stereotypeApplications,
            Set<org.modeldriven.alf.uml.Element> externalReferences) {
        return null;
    }
    
    @Override
    public List<org.modeldriven.alf.uml.NamedElement> bindTo(org.modeldriven.alf.uml.TemplateableElement template) {
        return new ArrayList<org.modeldriven.alf.uml.NamedElement>();
    }

    public void replace(org.modeldriven.alf.uml.Element element, org.modeldriven.alf.uml.Element newElement) {
    }
}

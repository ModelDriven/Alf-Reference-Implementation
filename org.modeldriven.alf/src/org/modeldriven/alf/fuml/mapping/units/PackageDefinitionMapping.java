
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.units.NamespaceDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Model;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.PackageableElement;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.ProfileApplication;

public class PackageDefinitionMapping extends NamespaceDefinitionMapping {

    private Package package_ = null;
    
    /**
     * 1. A package definition maps to a package. If the package definition is a
     * stub, then it is mapped according to the associated subunit definition.
     * 
     * 2. The applied profiles of a package definition map to profile
     * application relationships from the package to each of the applied
     * profiles.
     * 
     * 3. Each package member is mapped according to its kind. The resulting
     * elements are a packaged elements of the package.
     */
    
    // The mapping of visibility is handled in MemberMapping.
    // Stubs are handled in NamespaceDefinitionMapping. 
    
    protected Package mapPackage() {
        return this.create(
                this.getPackageDefinition().getImpl().isModelLibrary()? 
                        Model.class: Package.class);
    }
    
    public void mapTo(Package package_) throws MappingError {
        PackageDefinition packageDefinition = this.getPackageDefinition();
        if (packageDefinition != null && 
                !packageDefinition.getAppliedProfile().isEmpty()) {
            this.throwError("Cannot map profile application to fUML for package " + 
                    packageDefinition.getName());
        }
        
        if (package_ instanceof Model) {
            ElementReference standardProfile = RootNamespace.getStandardProfile();
            if (standardProfile != null) {
                ProfileApplication profileApplication = this.create(ProfileApplication.class);
                profileApplication.setAppliedProfile((Profile)standardProfile.getImpl().getUml());
                package_.addProfileApplication(profileApplication);
            }
        }
        
        super.mapTo(package_);
        
        if (packageDefinition != null && 
                packageDefinition.getImpl().isModelLibrary()) {
            ModelNamespaceMapping.applyStereotype(
                    packageDefinition, RootNamespace.getModelLibraryStereotype());
        }
    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (!(element instanceof PackageableElement)) {
            this.throwError("Member is not packageable: " + element);
        } else {
            ((Package)namespace).addPackagedElement((PackageableElement)element);
        }
    }
    
    @Override
    public NamedElement getNamedElement() throws MappingError {
        return this.getPackage();
	}
    
    @Override
    public Element getElement() {
        return this.package_;
    }

    public Package getPackage() throws MappingError {
        if (this.package_ == null) {
            this.package_ = this.mapPackage();
            this.mapTo(this.package_);
          }

          return this.package_;
    }
    
	public PackageDefinition getPackageDefinition() {
		return (PackageDefinition) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " package:" + this.package_);
	}
	
} // PackageDefinitionMapping

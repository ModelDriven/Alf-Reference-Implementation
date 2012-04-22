
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.units.NamespaceDefinitionMapping;

import org.modeldriven.alf.syntax.units.PackageDefinition;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Package;
import fUML.Syntax.Classes.Kernel.PackageableElement;

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
    
    public void mapTo(Package package_) throws MappingError {
        PackageDefinition packageDefinition = this.getPackageDefinition();
        if (packageDefinition != null && 
                !packageDefinition.getAppliedProfile().isEmpty()) {
            this.throwError("Cannot map profile application to fUML for package " + 
                    packageDefinition.getName());
        }
        
        super.mapTo(package_);
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
            this.package_ = new Package();
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

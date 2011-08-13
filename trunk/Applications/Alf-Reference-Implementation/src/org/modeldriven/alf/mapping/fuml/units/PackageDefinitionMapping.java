
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

import java.util.ArrayList;
import java.util.List;

public class PackageDefinitionMapping extends NamespaceDefinitionMapping {

    private Package package_ = null;
    
    @Override
    public List<Element> getModelElements() throws MappingError {
        ArrayList<Element> elements = new ArrayList<Element>();
        elements.add(this.getPackage());
        return elements;
	}
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        if (!(element instanceof PackageableElement)) {
            this.throwError("Member is not packageable:" + element);
        } else {
            ((Package)namespace).addPackagedElement((PackageableElement)element);
        }
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

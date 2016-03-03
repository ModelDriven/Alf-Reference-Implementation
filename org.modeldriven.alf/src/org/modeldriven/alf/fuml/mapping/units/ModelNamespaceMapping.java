/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.uml.Model;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Package;

public class ModelNamespaceMapping extends PackageDefinitionMapping {
    
    @Override
    protected Package mapPackage() {
        return this.create(Model.class);
    }
    
    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        super.addMemberTo(element, namespace);
        
        if (element instanceof NamedElement) {
            ((NamedElement)element).setVisibility("public");
        }
    }
        
    @Override
    public PackageDefinition getPackageDefinition() {
        return null;
    }

}

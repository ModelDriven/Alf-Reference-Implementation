/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.units;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.papyrus.uml.tools.utils.NameResolutionUtils;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Element;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;

public class ModelNamespaceImpl extends  
	org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl {
	
	Namespace contextNamespace = null;	
    
    public ModelNamespaceImpl(ModelNamespace self) {
        super(self);
    }
    
    public void setContext(Element contextElement) {
        this.contextNamespace = !(contextElement instanceof NamedElement)? null:
        	((NamedElement)contextElement).getNamespace();
        if (this.contextNamespace != null) {
        	this.setExactName(contextNamespace.getName());
        }
    }
    
    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
    	Collection<Member> members = new ArrayList<Member>();
    	if (this.contextNamespace != null) {
	    	for (NamedElement element: NameResolutionUtils.getNamedElements(
	    			name, this.contextNamespace, 
	    			classifierOnly? UMLPackage.Literals.CLASSIFIER: null)) {
	    		members.add(ImportedMemberImpl.makeImportedMember(
	    				element.getName(), 
	    				org.modeldriven.alf.eclipse.uml.Element.wrap(element), null));
	    	}
    	}
    	return members;
    }
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return RootNamespace.resolve(qualifiedName);
    }

}

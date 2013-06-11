/*******************************************************************************
 * Copyright 2013 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.units;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;

import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Namespace;

public class ModelNamespaceImpl extends 
	org.modeldriven.alf.fuml.units.ModelNamespaceImpl {

	private Namespace contextNamespace = null;
    
    public ModelNamespaceImpl(ModelNamespace self) {
        super(self);
    }
    
    public void setContext(Element contextElement) {
        this.contextNamespace = !(contextElement instanceof NamedElement)? null:
        	((NamedElement)contextElement).namespace;
        if (this.contextNamespace != null) {
        	this.setExactName(contextNamespace.name);
        }
    }
    
    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
    	Collection<Member> members = new ArrayList<Member>();
    	if (this.contextNamespace != null) {
    		// TODO: Handle resolution in enclosing namespaces.
	    	for (NamedElement element: this.contextNamespace.member) {
	    		if (!classifierOnly || element instanceof Classifier) {
		    		members.add(ImportedMemberImpl.makeImportedMember(
		    				element.name, 
		    				org.modeldriven.alf.fuml.impl.uml.Element.wrap(element), null));
	    		}
	    	}
    	}
    	return members;
    }
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return new MissingUnit(qualifiedName);
    }


}

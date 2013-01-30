
/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import java.util.Collection;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.units.MemberMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.ClassifierTemplateParameter;
import org.modeldriven.alf.syntax.units.ImportReference;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.ElementImport;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.PackageImport;

public abstract class NamespaceDefinitionMapping extends MemberMapping {
    
    /**
     * 1. A namespace definition maps to a namespace and its owned members, as
     * specified for each kind of namespace definition in the appropriate
     * subsequent subclause.
     * 
     * 2. A visibility indicator maps to the visibility of the named element
     * mapped from the definition containing the visibility indicator, with an
     * empty visibility indicator mapping to a visibility kind of "package".
     */
    
    // Visibility mapping is handled by MemberMapping.
    
    // Note: An operation is a namespace in full UML, but not in fUML, 
    // so the "namespace" parameter has the type "NamedElement" to accommodate
    // this.
    public void mapTo(NamedElement namespace) throws MappingError {
        super.mapTo(namespace);
        
        NamespaceDefinition definition = this.getNamespaceDefinition();

        // Map owned members of the namespace.
        for (Member member: definition.getOwnedMember()) {
            // Note: Ignore classifiers that are not completely bound and 
            // classifier template parameters.
            if (member.getImpl().isCompletelyBound() && 
                    !(member instanceof ClassifierTemplateParameter)) {
                if (member.getIsStub()) {
                    UnitDefinition subunit = member.getSubunit();
                    if (subunit == null) {
                        this.throwError("Cannot resolve subunit for " + 
                                member.getImpl().getQualifiedName().getPathName());
                    }
                    member = subunit.getDefinition();
                }
                FumlMapping mapping = this.fumlMap(member);
                for (Element element: mapping.getModelElements()) {
                    this.addMemberTo(element, namespace);
                }
            }
        }
        
        // If the namespace is the definition of a unit, then map the import
        // references for that unit.
        UnitDefinition unit = definition.getUnit();
        if (unit != null) {
            for (ImportReference importReference: unit.getImport()) {
                FumlMapping mapping = this.fumlMap(importReference);
                for (Element element: mapping.getModelElements()) {
                    if (element instanceof ElementImport) {
                        ((Namespace)namespace).addElementImport((ElementImport)element);
                    } else if (element instanceof PackageImport) {                        
                        ((Namespace)namespace).addPackageImport((PackageImport)element);
                    }
                    
                }
            }
        }
        
        /*
         * Map any statements nested in members of the namespace as a
         * second pass, to avoid possible circular mapping due to internal
         * references between members and between members and the namespace
         * itself.
         */
        // NOTE: Using an indexed loop allows for the possibility that members
        // may get added to a namespace due to derivations while mapping.
        for (int i = 0; i < definition.getOwnedMember().size(); i++) {
            Member member = definition.getOwnedMember().get(i);
            if (member.getIsStub()) {
                member = member.getSubunit().getDefinition();
            }
            Mapping mapping = member.getImpl().getMapping();
            if (mapping instanceof MemberMapping) {
                for (Element element: ((MemberMapping)mapping).mapBody()) {
                    this.addMemberTo(element, namespace);
                }
            }
        }
    }
    
    public abstract void addMemberTo(Element element, NamedElement namespace) 
        throws MappingError;
    
	public NamespaceDefinition getNamespaceDefinition() {
		return (NamespaceDefinition) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    NamespaceDefinition source = this.getNamespaceDefinition();
	    UnitDefinition unit = source.getUnit();
	    if (unit != null) {
	        Collection<ImportReference> imports = unit.getImport();
	        if (!imports.isEmpty()) {
	            System.out.println(prefix + " import:");
    	        for (ImportReference importReference: imports) {
    	            Mapping mapping = importReference.getImpl().getMapping();
    	            if (mapping != null) {
    	                mapping.printChild(prefix);
    	            }
    	        }
	        }
	    }
	    
	    Collection<Member> ownedMembers = source.getOwnedMember();
	    if (!ownedMembers.isEmpty()) {
	        System.out.println(prefix + " ownedMember:");
    	    for (Member member: ownedMembers) {
    	        if (member.getIsStub()) {
    	            UnitDefinition subunit = member.getSubunit();
    	            if (subunit != null) {
    	                member = member.getSubunit().getDefinition();
    	            }
    	        }
    	        Mapping mapping = member.getImpl().getMapping();
    	        if (mapping != null) {
    	            mapping.printChild(prefix);
    	        }
    	    }
	    }
	}
	
	public static String makeDistinguishableName(
	        NamespaceDefinition namespace,
	        String name) {
	    int i = 1;
	    String distinguishableName;
	    do {
	        distinguishableName = name + "$" + i++;
	    } while (!namespace.getImpl().resolve(distinguishableName).isEmpty());
	    return distinguishableName;
	}
} // NamespaceDefinitionMapping

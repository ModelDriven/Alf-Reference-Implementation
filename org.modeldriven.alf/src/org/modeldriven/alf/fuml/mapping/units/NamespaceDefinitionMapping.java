
/*******************************************************************************
 * Copyright 2011-2016 Model Driven Solutions, Inc.
 * Copyright 2013 Ivar Jacobson International SA
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import java.util.Collection;
import java.util.List;

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
        // NOTE: Not using an iterator allows for possible modification of the
        // owned members during mapping.
        List<Member> ownedMembers = definition.getOwnedMember();
        boolean supportsTemplates = this.supportsTemplates();
        for (int i = 0; i < ownedMembers.size(); i++) {
            Member member = ownedMembers.get(i);
            // Ignore members that are not completely bound, unless templates
            // are supported in the target UML implementation. Always ignore
            // template parameters (which are handled during classifier mapping).
            if ((supportsTemplates || member.getImpl().isCompletelyBound()) && 
                    !(member instanceof ClassifierTemplateParameter) &&
                    // Ignore stereotypes, which cannot be mapped to fUML.
                    !member.getImpl().isStereotype()) {
                // System.out.println("[mapTo] member=" + member);
                FumlMapping mapping = this.fumlMap(getNonfeatureSubunit(member));
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
        
        // System.out.println("[mapTo] Mapping bodies...");
        
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
            // System.out.println("[mapTo] member=" + member);
            Mapping mapping = getNonfeatureSubunit(member).getImpl().getMapping();
            if (mapping instanceof MemberMapping) {
                for (Element element: ((MemberMapping)mapping).mapBody()) {
                    this.addMemberTo(element, namespace);
                }
            }
        }
    }
    
    private static Member getNonfeatureSubunit(Member member) {
        if (!member.getIsFeature()) {
            UnitDefinition subunit = member.getSubunit();
            if (subunit != null) {
                member = subunit.getDefinition();
            }
        }
        return member;
    }
    
    public abstract void addMemberTo(Element element, NamedElement namespace) 
        throws MappingError;
    
	public NamespaceDefinition getNamespaceDefinition() {
		return (NamespaceDefinition) this.getSource();
	}
	
    public NamespaceDefinition getEffectiveNamespaceDefinition() {
        NamespaceDefinition definition = this.getNamespaceDefinition();
        UnitDefinition subunit = definition.getSubunit();
        return subunit == null? definition: subunit.getDefinition();
    }
    
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    NamespaceDefinition source = this.getNamespaceDefinition();
	    UnitDefinition unit = source.getSubunit();
	    if (unit == null) {
	        unit = source.getUnit();
	    }
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
    	        Mapping mapping = getNonfeatureSubunit(member).getImpl().getMapping();
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

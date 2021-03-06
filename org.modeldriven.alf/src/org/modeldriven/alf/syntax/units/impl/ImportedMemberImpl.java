/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.Element;

public class ImportedMemberImpl extends MemberImpl {

    private ElementReference referent = null;
    
    private boolean isImported = true;

	public ImportedMemberImpl(ImportedMember self) {
		super(self);
	}

	@Override
	public ImportedMember getSelf() {
		return (ImportedMember) this.self;
	}

	@Override
    public ElementReference getReferent() {
        return this.referent;
    }
	
    public void setReferent(ElementReference referent) {
        this.referent = referent;
    }
    
    @Override
    public boolean isImported() {
        return this.isImported;
    }
    
    public void setIsImported(boolean isImported) {
        this.isImported = isImported;
    }
    
    @Override
    public UnitDefinition getSubunit() {
        return null;
    }

    /**
     * An imported element should not generally be considered a feature of the
     * namespace it is imported into.
     **/
	@Override
	protected Boolean deriveIsFeature() {
	    return !this.isImported && this.getReferent().getImpl().isFeature();
	}
	
	/*
	 * Derivations
	 */

    public boolean importedMemberIsFeatureDerivation() {
        this.getSelf().getIsFeature();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * An imported element is not a stub.
	 **/
	public boolean importedMemberNotStub() {
		return !this.isImported() || !this.getSelf().getIsStub();
	}
	
	 @Override
	 public boolean memberStub() {
	     return true;
	 }

    /*
     * Helper Methods
     */
    
	/**
	 * Returns false. (Imported members do not have annotations.)
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false;
	} // annotationAllowed

	/**
	 * If the given member is not an imported member, then return the result of
	 * checking whether the given member is the same kind as this member.
	 * Else, if the element of the referent for this member is an Alf member,
	 * then return the result of checking whether that element is
	 * distinguishable from the given member. Else, if the element of the
	 * referent for the given member is an Alf member, then return the result of
	 * checking whether that element is distinguishable from this member. Else,
	 * the referents for both this and the given member are UML elements, so
	 * return the result of checking their distinguishability according to the
	 * rules of the UML superstructure.
	 **/
	public Boolean isSameKindAs(Member member) {
	    return super.isSameKindAs(member);
	}
	
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
	    return false;
	}

    @Override
    public boolean isProfile() {
        return this.getReferent().getImpl().isProfile();
    }
    
    @Override
    public boolean isStereotype() {
        return this.getReferent().getImpl().isStereotype();
    }
    
    @Override
    public ElementReference getContext() {
        return this.getReferent().getImpl().getContext();
    }
    
    public static ImportedMember makeImportedMember(Member member) {
        ImportedMember importedMember = new ImportedMember();
        importedMember.setReferent(member.getImpl().getReferent());
        importedMember.setName(member.getName());
        importedMember.setVisibility(member.getImpl().getVisibility());
        return importedMember;
    }
    
    public static ImportedMember makeImportedMember(ElementReference reference) {
        return makeImportedMember(reference, true);        
    }

    public static ImportedMember makeImportedMember(ElementReference reference, boolean isImported) {
        ImportedMember importedMember = new ImportedMember();
        importedMember.setReferent(reference);
        importedMember.getImpl().setExactName(reference.getImpl().getName());
        importedMember.getImpl().setIsImported(isImported);
        importedMember.setVisibility(reference.getImpl().getVisibility());
        return importedMember;        
    }

    public static ImportedMember makeImportedMember(
            String name, Element element, NamespaceDefinition namespace) {
        ImportedMember importedMember = makeImportedMember(
                ElementReferenceImpl.makeElementReference(element), false);
        importedMember.getImpl().setExactName(name == null? "": name);
        importedMember.setNamespace(namespace);
        return importedMember;
    }
    
    protected void bindTo(SyntaxElement base, 
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof Member) {
            ImportedMember baseMember = (ImportedMember)base;
            this.setReferent(baseMember.getImpl().getReferent());
            this.setIsImported(baseMember.getImpl().isImported());
        }
    }
    
} // ImportedMemberImpl


/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;

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

    /**
     * An imported element should not generally be considered a feature of the
     * namespace it is imported into.
     **/
	@Override
	protected Boolean deriveIsFeature() {
	    return false;
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
		return !this.getSelf().getIsStub();
	}

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
	    ImportedMember self = this.getSelf();
	    if (member == null) {
	        return false;
	    } else if (!(member instanceof ImportedMember)) {
	        return member.isSameKindAs(self);
	    } else {
	        ElementReference referent = self.getReferent();
	        ElementReference otherReferent = ((ImportedMember)member).getReferent();
	        if (referent == null || otherReferent == null) {
	            return false;
	        } else {
    	        SyntaxElement element = referent.getImpl().getAlf();
    	        SyntaxElement otherElement = otherReferent.getImpl().getAlf();
    	        if (element != null) {
    	            return element instanceof Member && ((Member)element).isSameKindAs(member);
    	        } else if (otherElement != null) {
    	            return otherElement instanceof Member && ((Member)otherElement).isSameKindAs(self);
    	        } else {
    	            Element umlElement = referent.getImpl().getUml();
    	            Element otherUmlElement = referent.getImpl().getUml();
    	            if (!(umlElement instanceof NamedElement && 
    	                    otherUmlElement instanceof NamedElement)) {
    	                return false;
    	            } else {
    	                NamedElement namedElement = (NamedElement)umlElement;
    	                Namespace namespace = namedElement.getNamespace();
    	                return namespace != null && 
    	                        namedElement.isDistinguishableFrom(
    	                                (NamedElement)otherUmlElement, namespace);
    	            }
    	        }
	        }
	    }
	} // isSameKindAs
	
	/*
	 * Helper Methods
	 */
	
    public static ImportedMember makeImportedMember(Member member) {
        ImportedMember importedMember = new ImportedMember();
        importedMember.setReferent(member.getImpl().getReferent());
        importedMember.setName(member.getName());
        importedMember.setVisibility(member.getImpl().getVisibility());
        return importedMember;
    }
    
    public static ImportedMember makeImportedMember(ElementReference reference) {
        ImportedMember importedMember = new ImportedMember();
        importedMember.setReferent(reference);
        importedMember.getImpl().setExactName(reference.getImpl().getName());
        importedMember.setVisibility(reference.getImpl().getVisibility());
        return importedMember;        
    }

    public static ImportedMember makeImportedMember(String name, NamedElement element) {
        ExternalElementReference reference = new ExternalElementReference();
        reference.setElement(element);
        ImportedMember importedMember = makeImportedMember(reference);
        importedMember.getImpl().setExactName(name);
        importedMember.getImpl().setIsImported(false);
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

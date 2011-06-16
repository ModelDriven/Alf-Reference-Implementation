
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.NamedElement;

public class ImportedMemberImpl extends MemberImpl {

    private ElementReference referent = null;

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
    	            // TODO: Handle isSameKindOf check for external element references.
    	            return false;
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
        return importedMember;
    }
    
    protected void bindTo(Member base, 
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        this.setReferent(base.getImpl().getReferent());
    }
    
} // ImportedMemberImpl

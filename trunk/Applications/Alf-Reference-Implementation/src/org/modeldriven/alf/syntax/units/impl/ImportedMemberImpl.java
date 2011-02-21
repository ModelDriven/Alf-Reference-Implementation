
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

public class ImportedMemberImpl extends
		org.modeldriven.alf.syntax.units.impl.MemberImpl {

	public ImportedMemberImpl(ImportedMember self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ImportedMember getSelf() {
		return (ImportedMember) this.self;
	}
	
    /**
     * An imported element is a feature if its referent is a feature.
     **/
	@Override
	public Boolean deriveIsFeature() {
	    ElementReference referent = this.getSelf().getReferent();
	    if (referent == null) {
	        return false;
	    } else {
	        SyntaxElement element = referent.getImpl().getAlf();
	        if (element != null) {
	            return element instanceof Member && ((Member)element).getIsFeature();
	        } else {
	            // TODO: Handle deriveIsFeature for external element references.
	            return false;
	        }
	    }
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
	 * checking whether the given member is distinguishable from this member.
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
	
	@Override
	public ElementReference getReferent() {
	    return this.getSelf().getReferent();
	}

} // ImportedMemberImpl

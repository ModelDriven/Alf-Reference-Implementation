
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReferenceImpl extends ImportReferenceImpl {

    private String alias = "";

	public ElementImportReferenceImpl(ElementImportReference self) {
		super(self);
	}

	public ElementImportReference getSelf() {
		return (ElementImportReference) this.self;
	}
	
    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

	/*
	 * Helper Methods
	 */

    @Override
    public ArrayList<Member> getImportedMembers() {
        ElementImportReference self = this.getSelf();
        ArrayList<Member> members = new ArrayList<Member>();
        ElementReference referent = self.getReferent();
        if (referent != null) {
            Member importedMember = ImportedMemberImpl.makeImportedMember(referent);
            importedMember.setVisibility(this.getSelf().getVisibility());
            String alias = this.getSelf().getAlias();
            if (alias != null && alias !="") {
                importedMember.setName(alias);
            } else {
                QualifiedName referentName = self.getReferentName();
                // This handles the proper naming of an import of an alias.
                if (referentName != null) {
                    importedMember.setName(referentName.getUnqualifiedName().getName());
                }
            }
            members.add(importedMember);
        }
        return members;
    }
    
    @Override
    public boolean equals(Object other) {
        return (other instanceof ElementImportReference || other instanceof ElementImportReferenceImpl) &&
            super.equals(other);
    }
    
} // ElementImportReferenceImpl

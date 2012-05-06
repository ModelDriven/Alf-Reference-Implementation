
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReferenceImpl extends SyntaxElementImpl {

	private String visibility = "";
	private QualifiedName referentName = null;
	private UnitDefinition unit = null;
	private ElementReference referent = null; // DERIVED

	public ImportReferenceImpl(ImportReference self) {
		super(self);
	}

	@Override
	public ImportReference getSelf() {
		return (ImportReference) this.self;
	}

	public String getVisibility() {
		return this.visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public QualifiedName getReferentName() {
		return this.referentName;
	}

	public void setReferentName(QualifiedName referentName) {
		this.referentName = referentName;
	}

	public UnitDefinition getUnit() {
		return this.unit;
	}

	public void setUnit(UnitDefinition unit) {
		this.unit = unit;
	}

	public ElementReference getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

    /**
     * The referent of an import reference is the element denoted by the
     * referent name.
     **/
	protected ElementReference deriveReferent() {
	    Collection<ElementReference> referents = this.getReferents();
	    return referents.size() == 0? null:
		            (ElementReference)referents.toArray()[0];
	}

	/*
	 * Derivations
	 */
	
	public boolean importReferenceReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The referent name of an import reference must resolve to a single element
	 * with public or empty visibility.
	 **/
	public boolean importReferenceReferent() {
		if (this.getReferents().size() != 1) {
		    return false;
		} else {
		    ElementReference referent = this.getSelf().getReferent();
		    String visibility = referent.getImpl().getVisibility();
		    return visibility == null || 
		            visibility.equals("") || visibility.equals("public");
		}
	}
	
	/*
	 * Helper Methods
	 */
	
	protected Collection<ElementReference> getReferents() {
	    QualifiedName referentName = this.getSelf().getReferentName();
	    if (referentName == null) {
	        return new ArrayList<ElementReference>();
	    } else {
            referentName.getImpl().setCurrentScope(RootNamespace.getRootScope());
    	    return referentName.getReferent();
	    }
	}
	
	public abstract ArrayList<Member> getImportedMembers();
	
    @Override
    public boolean equals(Object other) {
        if (other instanceof ImportReference) {
            return ((ImportReference)other).getReferentName().equals(this.getSelf().getReferentName());
        } else if (other instanceof PackageImportReferenceImpl) {
            return ((ImportReferenceImpl)other).getSelf().getReferentName().equals(this.getSelf().getReferentName());
        } else {
            return false;
        }
    }

} // ImportReferenceImpl

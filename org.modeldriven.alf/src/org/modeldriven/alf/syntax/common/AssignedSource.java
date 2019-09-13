/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;

import java.util.Collection;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;

/**
 * An assignment of a source element that gives the value of a local name or
 * input parameter, along with a record of the defined type (if any) and
 * multiplicity of the name.
 **/
public class AssignedSource extends ParsedElement {

	protected AssignedSourceImpl impl;

	public AssignedSource() {
		this.impl = new AssignedSourceImpl(this);
	}

	public AssignedSource(Parser parser) {
		this();
		this.init(parser);
	}

	public AssignedSource(ParsedElement element) {
		this();
        this.init(element);
	}

	public AssignedSourceImpl getImpl() {
		return (AssignedSourceImpl) this.impl;
	}

 	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public ElementReference getSource() {
		return this.getImpl().getSource();
	}

    public void setSource(ElementReference source) {
        this.getImpl().setSource(source);
    }

    public void setSource(SyntaxElement source) {
        InternalElementReference elementReference = new InternalElementReference();
        elementReference.setElement(source);
        this.setSource(elementReference);
    }

	public Integer getUpper() {
		return this.getImpl().getUpper();
	}

	public void setUpper(Integer upper) {
		this.getImpl().setUpper(upper);
	}

	public Integer getLower() {
		return this.getImpl().getLower();
	}

	public void setLower(Integer lower) {
		this.getImpl().setLower(lower);
	}

    public ElementReference getType() {
        return this.getImpl().getType();
    }

    public void setType(ElementReference type) {
        this.getImpl().setType(type);
    }

    public ElementReference getSubtype() {
        return this.getImpl().getSubtype();
    }

    public void setSubtype(ElementReference subtype) {
        this.getImpl().setSubtype(subtype);
    }

    public ElementReference getKnownType() {
        return this.getImpl().getKnownType();
    }

    public void setKnownType(ElementReference knownType) {
        this.getImpl().setKnownType(knownType);
    }
    
    /**
     * If an assigned source has a subtype set, then this is the known type
     * for the assigned source. Otherwise the type of the assigned source is
     * also the known type.
     */
    public boolean assignedSourceKnownTypeDerivation() {
        return this.getImpl().assignedSourceKnownTypeDerivation();
    }

	public void deriveAll() {
		this.getImpl().deriveAll();
	}

	public void _deriveAll() {
	    this.getKnownType();
		ElementReference type = this.getType();
		if (type != null) {
			type.deriveAll();
		}
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
        if (!this.assignedSourceKnownTypeDerivation()) {
            violations.add(new ConstraintViolation(
                    "assignedSourceKnownTypeDerivation", this));
        }
		ElementReference type = this.getType();
		if (type != null) {
			type.checkConstraints(violations);
		}
	}

	public String getId() {
		return Integer.toHexString(this.hashCode());
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.getId() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		s.append(" name:");
		s.append(this.getName());
		s.append(" upper:");
		s.append(this.getUpper());
		s.append(" lower:");
		s.append(this.getLower());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		System.out.println(prefix + "[" + this.getId() + "]"
				+ this._toString(includeDerived));
		ElementReference source = this.getSource();
		if (source != null) {
			System.out.println(prefix + " source:"
					+ source.toString(includeDerived));
		}
		ElementReference type = this.getType();
		if (type != null) {
			System.out.println(prefix + " type:");
			type.print(prefix + "  ", includeDerived);
		}
	}
} // AssignedSource

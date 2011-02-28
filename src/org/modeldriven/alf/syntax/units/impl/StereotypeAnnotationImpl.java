
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.Stereotype;

import java.util.ArrayList;

/**
 * An annotation of a member definition indicating the application of a
 * stereotype (or one of a small number of special-case annotations).
 **/

public class StereotypeAnnotationImpl extends SyntaxElementImpl {

	public StereotypeAnnotationImpl(StereotypeAnnotation self) {
		super(self);
	}

	@Override
	public StereotypeAnnotation getSelf() {
		return (StereotypeAnnotation) this.self;
	}

    /**
     * Unless the stereotype name is "apply", "primitive" or "external" then the
     * stereotype for a stereotype annotation is the stereotype denoted by the
     * stereotype name.
     **/
	public Stereotype deriveStereotype() {
	    // TODO Allow unqualified stereotype names for standard profiles or
	    //      if there is only one applied profile.
	    Stereotype stereotype = null;
	    QualifiedName stereotypeName = this.getSelf().getStereotypeName();
	    if (stereotypeName != null && 
	            !stereotypeName.getImpl().equals("apply") &&
	            !stereotypeName.getImpl().equals("primitive") &&
	            !stereotypeName.getImpl().equals("external")) {
	        stereotypeName.getImpl().setCurrentScope(RootNamespace.getRootScope());
	        ElementReference stereotypeReferent = stereotypeName.getImpl().getStereotypeReferent();
	        if (stereotypeReferent != null) {
	            stereotype = (Stereotype)stereotypeReferent.getImpl().getUml();
	        }
	    }
		return stereotype;
	}
	
	/*
	 * Derivations
	 */

	public boolean stereotypeAnnotationStereotypeDerivation() {
		this.getSelf().getStereotype();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The stereotype name of a stereotype annotation must either be one of
	 * "apply", "primitive" or "external", or it must denote a single stereotype
	 * from a profile applied to an enclosing package. The stereotype name does
	 * not need to be qualified if there is only one applied profile with a
	 * stereotype of that name or if the there is a standard UML profile with
	 * the name.
	 **/
	public boolean stereotypeAnnotationStereotypeName() {
		return this.getSelf().getStereotype() != null;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "apply", then it
	 * must have a name list and all of the names in the list must resolve to
	 * profiles.
	 **/
	public boolean stereotypeAnnotationApply() {
	    StereotypeAnnotation self = this.getSelf();
	    if (self.getStereotypeName().equals("apply")) {
	        QualifiedNameList names = self.getNames();
	        if (names == null) {
	            return false;
	        } else {
	            for (QualifiedName name: names.getName()) {
	                name.getImpl().setCurrentScope(RootNamespace.getRootScope());
	                if (!name.getImpl().isProfileReferent()) {
	                    return false;
	                }
	            }
	        }
	    }
		return true;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "primitive", then it
	 * may not have tagged values or names.
	 **/
	public boolean stereotypeAnnotationPrimitive() {
        StereotypeAnnotation self = this.getSelf();
		return !self.getStereotypeName().equals("primitive") ||
		            self.getNames() == null && self.getTaggedValues() == null;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "external", then it
	 * may optionally have a single tagged value with the name "file" and no
	 * operator.
	 **/
	public boolean stereotypeAnnotationExternal() {
        StereotypeAnnotation self = this.getSelf();
        TaggedValueList taggedValueList = self.getTaggedValues();
        if (!self.getStereotypeName().equals("external")) {
            return true;
        } else {
            if (self.getNames() != null) {
                return false;
            } else if (taggedValueList != null) {
                ArrayList<TaggedValue> taggedValues = taggedValueList.getTaggedValue();
                if (taggedValues == null || taggedValues.size() > 1) {
                    return false;
                } else {
                    TaggedValue taggedValue = taggedValues.get(0);
                    return  taggedValue.getName().equals("file") &&
                            taggedValue.getOperator() == null;
                }
            } else {
                return true;
            }
        }
	}

	/**
	 * If a stereotype annotation has a stereotype and tagged values, then the
	 * each tagged value must have the name of an attribute of the stereotype
	 * and a value that is legally interpretable for the type of that attribute.
	 **/
	public boolean stereotypeAnnotationTaggedValues() {
	    // TODO Check names and types of tagged values.
		return true;
	}

	/**
	 * If a stereotype annotation has a stereotype and a list of names, then all
	 * the names in the list must resolve to visible model elements and the
	 * stereotype must have a single attribute with a (metaclass) type and
	 * multiplicity that are consistent with the types and number of the
	 * elements denoted by the given names.
	 **/
	public boolean stereotypeAnnotationNames() {
	    // TODO Check validity of names in a stereotype name list.
		return true;
	}

} // StereotypeAnnotationImpl

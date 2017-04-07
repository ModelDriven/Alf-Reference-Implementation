/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.ClassifierTemplateParameterImpl;

/**
 * The definition of a classifier template parameter, which acts as a classifier
 * within the definition of the template.
 **/

public class ClassifierTemplateParameter extends ClassifierDefinition {

	public ClassifierTemplateParameter() {
		this.impl = new ClassifierTemplateParameterImpl(this);
	}

	public ClassifierTemplateParameter(Parser parser) {
		this();
		this.init(parser);
	}

	public ClassifierTemplateParameter(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ClassifierTemplateParameterImpl getImpl() {
		return (ClassifierTemplateParameterImpl) this.impl;
	}

	/**
	 * Annotations are not allowed on classifier template parameters.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Returns false. (Classifier template parameters cannot be stubs.)
	 **/
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * Return true if the given member is a classifier template parameter.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // ClassifierTemplateParameter

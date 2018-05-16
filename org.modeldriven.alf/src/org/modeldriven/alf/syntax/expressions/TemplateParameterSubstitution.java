
/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.impl.TemplateParameterSubstitutionImpl;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitution extends SyntaxElement {

	public TemplateParameterSubstitution() {
		this.impl = new TemplateParameterSubstitutionImpl(this);
	}

	public TemplateParameterSubstitution(Parser parser) {
		this();
		this.init(parser);
	}

	public TemplateParameterSubstitution(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public TemplateParameterSubstitutionImpl getImpl() {
		return (TemplateParameterSubstitutionImpl) this.impl;
	}

	public String getParameterName() {
		return this.getImpl().getParameterName();
	}

	public void setParameterName(String parameterName) {
		this.getImpl().setParameterName(parameterName);
	}

	public QualifiedName getArgumentName() {
		return this.getImpl().getArgumentName();
	}

	public void setArgumentName(QualifiedName argumentName) {
		this.getImpl().setArgumentName(argumentName);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getArgumentName());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			argumentName.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			argumentName.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" parameterName:");
		s.append(this.getParameterName());
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
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			System.out.println(prefix + " argumentName:");
			argumentName.print(prefix + "  ", includeDerived);
		}
	}
} // TemplateParameterSubstitution

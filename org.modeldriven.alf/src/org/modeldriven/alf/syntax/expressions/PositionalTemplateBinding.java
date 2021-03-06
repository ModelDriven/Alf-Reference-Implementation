
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.syntax.expressions.impl.PositionalTemplateBindingImpl;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters in order by position.
 **/

public class PositionalTemplateBinding extends TemplateBinding {

	public PositionalTemplateBinding() {
		this.impl = new PositionalTemplateBindingImpl(this);
	}

	public PositionalTemplateBinding(Parser parser) {
		this();
		this.init(parser);
	}

	public PositionalTemplateBinding(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public PositionalTemplateBindingImpl getImpl() {
		return (PositionalTemplateBindingImpl) this.impl;
	}

	public Collection<QualifiedName> getArgumentName() {
		return this.getImpl().getArgumentName();
	}

	public void setArgumentName(Collection<QualifiedName> argumentName) {
		this.getImpl().setArgumentName(argumentName);
	}

	public void addArgumentName(QualifiedName argumentName) {
		this.getImpl().addArgumentName(argumentName);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getArgumentName());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<QualifiedName> argumentName = this.getArgumentName();
		if (argumentName != null) {
			for (Object _argumentName : argumentName.toArray()) {
				((QualifiedName) _argumentName).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<QualifiedName> argumentName = this.getArgumentName();
		if (argumentName != null) {
			for (Object _argumentName : argumentName.toArray()) {
				((QualifiedName) _argumentName).checkConstraints(violations);
			}
		}
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
		Collection<QualifiedName> argumentName = this.getArgumentName();
		if (argumentName != null && argumentName.size() > 0) {
			System.out.println(prefix + " argumentName:");
			for (Object _object : argumentName.toArray()) {
				QualifiedName _argumentName = (QualifiedName) _object;
				if (_argumentName != null) {
					_argumentName.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // PositionalTemplateBinding

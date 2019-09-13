
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
import org.modeldriven.alf.syntax.expressions.impl.NamedTemplateBindingImpl;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBinding extends TemplateBinding {

	public NamedTemplateBinding() {
		this.impl = new NamedTemplateBindingImpl(this);
	}

	public NamedTemplateBinding(Parser parser) {
		this();
		this.init(parser);
	}

	public NamedTemplateBinding(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public NamedTemplateBindingImpl getImpl() {
		return (NamedTemplateBindingImpl) this.impl;
	}

	public Collection<TemplateParameterSubstitution> getSubstitution() {
		return this.getImpl().getSubstitution();
	}

	public void setSubstitution(
			Collection<TemplateParameterSubstitution> substitution) {
		this.getImpl().setSubstitution(substitution);
	}

	public void addSubstitution(TemplateParameterSubstitution substitution) {
		this.getImpl().addSubstitution(substitution);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getSubstitution());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null) {
			for (Object _substitution : substitution.toArray()) {
				((TemplateParameterSubstitution) _substitution).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null) {
			for (Object _substitution : substitution.toArray()) {
				((TemplateParameterSubstitution) _substitution)
						.checkConstraints(violations);
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
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null && substitution.size() > 0) {
			System.out.println(prefix + " substitution:");
			for (Object _object : substitution.toArray()) {
				TemplateParameterSubstitution _substitution = (TemplateParameterSubstitution) _object;
				if (_substitution != null) {
					_substitution.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // NamedTemplateBinding

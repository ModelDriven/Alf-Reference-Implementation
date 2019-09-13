
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
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.impl.NameBindingImpl;

/**
 * An unqualified name, optionally with a template binding.
 **/

public class NameBinding extends SyntaxElement {

	public NameBinding() {
		this.impl = new NameBindingImpl(this);
	}

	public NameBinding(Parser parser) {
		this();
		this.init(parser);
	}

	public NameBinding(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public NameBindingImpl getImpl() {
		return (NameBindingImpl) this.impl;
	}

	public TemplateBinding getBinding() {
		return this.getImpl().getBinding();
	}

	public void setBinding(TemplateBinding binding) {
		this.getImpl().setBinding(binding);
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getBinding());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			binding.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			binding.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
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
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			System.out.println(prefix + " binding:");
			binding.print(prefix + "  ", includeDerived);
		}
	}
} // NameBinding

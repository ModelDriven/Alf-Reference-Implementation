
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
import java.util.List;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.NamedTupleImpl;

/**
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTuple extends Tuple {

	public NamedTuple() {
		this.impl = new NamedTupleImpl(this);
	}

	public NamedTuple(Parser parser) {
		this();
		this.init(parser);
	}

	public NamedTuple(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public NamedTupleImpl getImpl() {
		return (NamedTupleImpl) this.impl;
	}

	public List<NamedExpression> getNamedExpression() {
		return this.getImpl().getNamedExpression();
	}

	public void setNamedExpression(List<NamedExpression> namedExpression) {
		this.getImpl().setNamedExpression(namedExpression);
	}

	public void addNamedExpression(NamedExpression namedExpression) {
		this.getImpl().addNamedExpression(namedExpression);
	}

	/**
	 * The name of a named expression of a named tuple must be the name of a
	 * parameter of the invocation the tuple is for. No two named expressions
	 * may have the same name.
	 **/
	public boolean namedTupleArgumentNames() {
		return this.getImpl().namedTupleArgumentNames();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getNamedExpression());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<NamedExpression> namedExpression = this.getNamedExpression();
		if (namedExpression != null) {
			for (Object _namedExpression : namedExpression.toArray()) {
				((NamedExpression) _namedExpression).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.namedTupleArgumentNames()) {
			violations.add(new ConstraintViolation("namedTupleArgumentNames",
					this));
		}
		Collection<NamedExpression> namedExpression = this.getNamedExpression();
		if (namedExpression != null) {
			for (Object _namedExpression : namedExpression.toArray()) {
				((NamedExpression) _namedExpression)
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
		List<NamedExpression> namedExpression = this.getNamedExpression();
		if (namedExpression != null && namedExpression.size() > 0) {
			System.out.println(prefix + " namedExpression:");
			for (Object _object : namedExpression.toArray()) {
				NamedExpression _namedExpression = (NamedExpression) _object;
				if (_namedExpression != null) {
					_namedExpression.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // NamedTuple

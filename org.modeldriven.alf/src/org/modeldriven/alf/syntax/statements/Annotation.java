/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.AnnotationImpl;

/**
 * An identified modification to the behavior of an annotated statement.
 **/

public class Annotation extends SyntaxElement {

	public Annotation() {
		this.impl = new AnnotationImpl(this);
	}

	public Annotation(Parser parser) {
		this();
		this.init(parser);
	}

	public Annotation(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public AnnotationImpl getImpl() {
		return (AnnotationImpl) this.impl;
	}

	public String getIdentifier() {
		return this.getImpl().getIdentifier();
	}

	public void setIdentifier(String identifier) {
		this.getImpl().setIdentifier(identifier);
	}

	public Collection<String> getArgument() {
		return this.getImpl().getArgument();
	}

	public void setArgument(Collection<String> argument) {
		this.getImpl().setArgument(argument);
	}

	public void addArgument(String argument) {
		this.getImpl().addArgument(argument);
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
		s.append(" identifier:");
		s.append(this.getIdentifier());
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
		Collection<String> argument = this.getArgument();
		if (argument != null && argument.size() > 0) {
			System.out.println(prefix + " argument:");
			for (Object _object : argument.toArray()) {
				String _argument = (String) _object;
				System.out.println(prefix + "  " + _argument);
			}
		}
	}
} // Annotation

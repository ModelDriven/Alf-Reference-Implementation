
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.AcceptBlockImpl;

/**
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlock extends SyntaxElement {

	public AcceptBlock() {
		this.impl = new AcceptBlockImpl(this);
	}

	public AcceptBlock(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public AcceptBlock(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public AcceptBlockImpl getImpl() {
		return (AcceptBlockImpl) this.impl;
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public Block getBlock() {
		return this.getImpl().getBlock();
	}

	public void setBlock(Block block) {
		this.getImpl().setBlock(block);
	}

	public QualifiedNameList getSignalNames() {
		return this.getImpl().getSignalNames();
	}

	public void setSignalNames(QualifiedNameList signalNames) {
		this.getImpl().setSignalNames(signalNames);
	}

	public Collection<ElementReference> getSignal() {
		return this.getImpl().getSignal();
	}

	public void setSignal(Collection<ElementReference> signal) {
		this.getImpl().setSignal(signal);
	}

	public void addSignal(ElementReference signal) {
		this.getImpl().addSignal(signal);
	}

	/**
	 * The signals of an accept block are the referents of the signal names of
	 * the accept block.
	 **/
	public boolean acceptBlockSignalDerivation() {
		return this.getImpl().acceptBlockSignalDerivation();
	}

	/**
	 * All signal names in an accept block must resolve to signals.
	 **/
	public boolean acceptBlockSignalNames() {
		return this.getImpl().acceptBlockSignalNames();
	}

	public void _deriveAll() {
		this.getSignal();
		super._deriveAll();
		Block block = this.getBlock();
		if (block != null) {
			block.deriveAll();
		}
		QualifiedNameList signalNames = this.getSignalNames();
		if (signalNames != null) {
			signalNames.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.acceptBlockSignalDerivation()) {
			violations.add(new ConstraintViolation(
					"acceptBlockSignalDerivation", this));
		}
		if (!this.acceptBlockSignalNames()) {
			violations.add(new ConstraintViolation("acceptBlockSignalNames",
					this));
		}
		Block block = this.getBlock();
		if (block != null) {
			block.checkConstraints(violations);
		}
		QualifiedNameList signalNames = this.getSignalNames();
		if (signalNames != null) {
			signalNames.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Block block = this.getBlock();
		if (block != null) {
			System.out.println(prefix + " block:");
			block.print(prefix + "  ", includeDerived);
		}
		QualifiedNameList signalNames = this.getSignalNames();
		if (signalNames != null) {
			System.out.println(prefix + " signalNames:");
			signalNames.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Collection<ElementReference> signal = this.getSignal();
			if (signal != null && signal.size() > 0) {
				System.out.println(prefix + " /signal:");
				for (Object _object : signal.toArray()) {
					ElementReference _signal = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _signal.toString(includeDerived));
				}
			}
		}
	}
} // AcceptBlock

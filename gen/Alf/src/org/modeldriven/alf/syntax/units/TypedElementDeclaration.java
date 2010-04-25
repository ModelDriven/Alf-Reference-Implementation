
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class TypedElementDeclaration extends SyntaxNode {

	private QualifiedName type = null;
	private String lowerBound = "";
	private String upperBound = "";
	private boolean isOrdered = false;
	private boolean isNonunique = false;

	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	} // setLowerBound

	public String getLowerBound() {
		return this.lowerBound;
	} // getLowerBound

	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	} // setUpperBound

	public String getUpperBound() {
		return this.upperBound;
	} // getUpperBound

	public void setIsOrdered() {
		this.isOrdered = true;
	} // setIsOrdered

	public boolean isOrdered() {
		return this.isOrdered;
	} // isOrdered

	public void setIsNonunique() {
		this.isNonunique = true;
	} // setIsNonunique

	public boolean isNonunique() {
		return this.isNonunique;
	} // isNonunique

	public void setType(QualifiedName type) {
		this.type = type;
	} // setType

	public QualifiedName getType() {
		return this.type;
	} // getType

	public String toString() {
		return super.toString() + " lowerBound:" + this.getLowerBound()
				+ " upperBound:" + this.getUpperBound() + " isOrdered:"
				+ this.isOrdered() + " isNonunique:" + this.isNonunique();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (this.getType() != null) {
			this.getType().printChild(prefix);
		}
	} // print

	public boolean equals(TypedElementDeclaration other,
			NamespaceDefinition context) {
		Member thisClassifier = this.getClassifier(context);
		Member otherClassifier = other.getClassifier(context);

		// System.out.println("thisClassifier = " + thisClassifier);
		// System.out.println("otherClassifier = " + otherClassifier);

		return thisClassifier == otherClassifier
				&& this.getLower() == other.getLower()
				&& this.getUpper() == other.getUpper()
				&& this.isOrdered() == other.isOrdered()
				&& this.isNonunique() == other.isNonunique();
	} // equals

	public int getLower() {
		String lower = this.getLowerBound();

		if (lower != null && !lower.equals("")) {
			return Integer.valueOf(lower);
		} else {
			int upper = this.getUpper();
			return (upper == -1) ? 0 : upper;
		}

	} // getLower

	public int getUpper() {
		String upper = this.getUpperBound();

		if (upper == null || upper.equals("")) {
			return 1;
		} else if (upper.equals("*")) {
			return -1;
		} else {
			return Integer.valueOf(upper);
		}
	} // getUpper

	public Member getClassifier(NamespaceDefinition context) {
		// System.out.println("getClassifier: this = " + this + " context = " +
		// context);

		QualifiedName type = this.getType();

		if (type == null) {
			return null;
		} else {
			Member classifier = type.getClassifier(context);
			if (classifier.isError()) {
				classifier = new ErrorMember(this, (ErrorMember) classifier);
			}

			// classifier.print("  ");

			return classifier;
		}
	} // getClassifier

} // TypedElementDeclaration


/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class TypedElementDeclaration extends Node {

	private QualifiedName type = null;
	private String lowerBound = "";
	private String upperBound = "";
	private boolean isOrdered = false;
	private boolean isNonunique = false;
	private String collection = "";

	public void setType(QualifiedName type) {
		this.type = type;
	} // setType

	public QualifiedName getType() {
		return this.type;
	} // getType

	public void setIsOrdered() {
		this.isOrdered = true;
	} // setIsOrdered

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

	public boolean isOrdered() {
		return this.isOrdered;
	} // isOrdered

	public void setIsNonunique() {
		this.isNonunique = true;
	} // setIsNonunique

	public boolean isNonunique() {
		return this.isNonunique;
	} // isNonunique

	public void setCollection(String collection) {
		this.collection = collection;
	} // setCollection

	public String getCollection() {
		return this.collection;
	} // getCollection

	public String toString() {
		return super.toString() + " lowerBound:" + this.getLowerBound()
				+ " upperBound:" + this.getUpperBound() + " isOrdered:"
				+ this.isOrdered() + " isNonunique:" + this.isNonunique()
				+ " collection:" + this.getCollection();
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
				&& (this.getCollection() == null
						&& other.getCollection() == null
						&& this.getLower() == other.getLower()
						&& this.getUpper() == other.getUpper()
						&& this.isOrdered() == other.isOrdered()
						&& this.isNonunique() == other.isNonunique() || this
						.getCollection().equals(other.getCollection()));
	} // equals

	public int getLower() {
		String lower = this.getLowerBound();

		if (lower != null && !lower.equals("")) {
			return Integer.valueOf(lower);
		} else {
			int upper = this.getUpper();
			if (upper == -1) {
				return 0;
			} else {
				return upper;
			}
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
		QualifiedName type = this.getType();

		if (type == null) {
			return null;
		} else {
			Member classifier = type.getClassifier(context);
			if (classifier.isError()) {
				classifier = new ErrorMember(this, (ErrorMember) classifier);
			}
			return classifier;
		}
	} // getClassifier

} // TypedElementDeclaration

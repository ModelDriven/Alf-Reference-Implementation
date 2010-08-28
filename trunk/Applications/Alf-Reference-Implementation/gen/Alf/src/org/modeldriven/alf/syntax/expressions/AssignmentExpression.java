
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpression extends Expression {

	private String operator = "";
	private LeftHandSide leftHandSide = null;
	private Expression rightHandSide = null;
	private AssignedSource assignment = null; // DERIVED
	private ElementReference feature = null; // DERIVED
	private boolean isIndexed = false; // DERIVED
	private boolean isArithmetic = false; // DERIVED
	private boolean isDefinition = false; // DERIVED
	private boolean isSimple = false; // DERIVED
	private Expression expression = null; // DERIVED
	private boolean isFeature = false; // DERIVED
	private boolean isDataValueUpdate = false; // DERIVED
	private boolean isCollectionConversion = false; // DERIVED
	private boolean isBitStringConversion = false; // DERIVED

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public LeftHandSide getLeftHandSide() {
		return this.leftHandSide;
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public Expression getRightHandSide() {
		return this.rightHandSide;
	}

	public void setRightHandSide(Expression rightHandSide) {
		this.rightHandSide = rightHandSide;
	}

	public AssignedSource getAssignment() {
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public ElementReference getFeature() {
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	public boolean getIsIndexed() {
		return this.isIndexed;
	}

	public void setIsIndexed(boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public boolean getIsArithmetic() {
		return this.isArithmetic;
	}

	public void setIsArithmetic(boolean isArithmetic) {
		this.isArithmetic = isArithmetic;
	}

	public boolean getIsDefinition() {
		return this.isDefinition;
	}

	public void setIsDefinition(boolean isDefinition) {
		this.isDefinition = isDefinition;
	}

	public boolean getIsSimple() {
		return this.isSimple;
	}

	public void setIsSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public boolean getIsFeature() {
		return this.isFeature;
	}

	public void setIsFeature(boolean isFeature) {
		this.isFeature = isFeature;
	}

	public boolean getIsDataValueUpdate() {
		return this.isDataValueUpdate;
	}

	public void setIsDataValueUpdate(boolean isDataValueUpdate) {
		this.isDataValueUpdate = isDataValueUpdate;
	}

	public boolean getIsCollectionConversion() {
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public boolean getIsBitStringConversion() {
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after an assignment expression are the assignments
		 * after the left-hand side, updated by the assignment from the
		 * assignment statement, if any.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.operator);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.leftHandSide != null) {
			this.leftHandSide.print(prefix + " ");
		}
		if (this.rightHandSide != null) {
			this.rightHandSide.print(prefix + " ");
		}
	}
} // AssignmentExpression

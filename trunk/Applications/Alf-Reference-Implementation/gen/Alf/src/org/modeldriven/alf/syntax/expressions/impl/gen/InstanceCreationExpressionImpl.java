
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

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

/**
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	private Boolean isConstructorless = null; // DERIVED
	private Boolean isObjectCreation = null; // DERIVED
	private QualifiedName constructor = null;

	public InstanceCreationExpressionImpl(InstanceCreationExpression self) {
		super(self);
	}

	public InstanceCreationExpression getSelf() {
		return (InstanceCreationExpression) this.self;
	}

	public Boolean getIsConstructorless() {
		if (this.isConstructorless == null) {
			this.setIsConstructorless(this.deriveIsConstructorless());
		}
		return this.isConstructorless;
	}

	public void setIsConstructorless(Boolean isConstructorless) {
		this.isConstructorless = isConstructorless;
	}

	public Boolean getIsObjectCreation() {
		if (this.isObjectCreation == null) {
			this.setIsObjectCreation(this.deriveIsObjectCreation());
		}
		return this.isObjectCreation;
	}

	public void setIsObjectCreation(Boolean isObjectCreation) {
		this.isObjectCreation = isObjectCreation;
	}

	public QualifiedName getConstructor() {
		return this.constructor;
	}

	public void setConstructor(QualifiedName constructor) {
		this.constructor = constructor;
	}

	protected Boolean deriveIsConstructorless() {
		return null; // STUB
	}

	protected Boolean deriveIsObjectCreation() {
		return null; // STUB
	}

	/**
	 * An instance creation expression is an object creation if its referent is
	 * not a data type.
	 **/
	public boolean instanceCreationExpressionIsObjectCreationDerivation() {
		this.getSelf().getIsObjectCreation();
		return true;
	}

	/**
	 * An instance creation expression is constructorless if its referent is a
	 * class.
	 **/
	public boolean instanceCreationExpressionIsConstructorlessDerivation() {
		this.getSelf().getIsConstructorless();
		return true;
	}

	/**
	 * The referent of an instance creation expression is the constructor
	 * operation, class or data type to which the constructor name resolves.
	 **/
	public boolean instanceCreationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * There is no feature for an instance creation expression.
	 **/
	public boolean instanceCreationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * The constructor name must resolve to a constructor operation (that is
	 * compatible with the tuple argument expressions), a class or a data type,
	 * but not both a class and a data type.
	 **/
	public boolean instanceCreationExpressionConstructor() {
		return true;
	}

	/**
	 * If the expression is constructorless, then its tuple must be empty.
	 **/
	public boolean instanceCreationExpressionTuple() {
		return true;
	}

	/**
	 * If an instance creation expression is a data value creation (not an
	 * object creation), then the tuple argument expressions are matched with
	 * the attributes of the named type.
	 **/
	public boolean instanceCreationExpressionDataTypeCompatibility() {
		return true;
	}

	/**
	 * Returns the parameters of a constructor operation or the attributes of a
	 * data type, or an empty set for a constructorless instance creation.
	 **/
	public Collection<ElementReference> parameterElements() {
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

} // InstanceCreationExpressionImpl

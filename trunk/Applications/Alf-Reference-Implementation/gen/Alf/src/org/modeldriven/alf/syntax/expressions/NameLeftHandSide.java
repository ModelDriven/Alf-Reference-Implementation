
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class NameLeftHandSide extends LeftHandSide {

	private QualifiedName target = null;

	public NameLeftHandSide(QualifiedName target, Expression index) {
		super(index);
		this.target = target;
	} // NameLeftHandSide

	public QualifiedName getTarget() {
		return this.target;
	} // getTarget

	public void print(String prefix) {
		super.print(prefix);
		this.getTarget().printChild(prefix);

		if (this.getIndex() != null) {
			this.getIndex().printChild(prefix);
		}
	} // print

} // NameLeftHandSide

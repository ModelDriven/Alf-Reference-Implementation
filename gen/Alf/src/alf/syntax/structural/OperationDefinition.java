
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

public class OperationDefinition extends BehaviorDefinition {

	private QualifiedNameList redefinition = null;
	private boolean isAbstract = false;

	public void setRedefinition(QualifiedNameList redefinition) {
		this.redefinition = redefinition;
	} // setRedefinition

	public QualifiedNameList getRedefinition() {
		return this.redefinition;
	} // getRedefinition

	public void setIsAbstract() {
		this.isAbstract = true;
	} // setIsAbstract

	public boolean isAbstract() {
		return this.isAbstract;
	} // isAbstract

	public String toString() {
		return super.toString() + " isAbstract:" + this.isAbstract();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (this.getRedefinition() != null) {
			this.getRedefinition().printChild(prefix);
		}

		if (this.getBody() != null) {
			this.getBody().printChild(prefix);
		}
	} // print

} // OperationDefinition

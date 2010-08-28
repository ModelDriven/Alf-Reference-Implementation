
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A reference to a model element, either directly or via its Alf abstract
 * syntax representation. (NOTE: The definitions of all the helper operations of
 * ElementReference are specific to its subclasses.)
 **/

public abstract class ElementReference {

	public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}

	public void print(String prefix) {
		System.out.println(prefix + this.toString());
	}
} // ElementReference

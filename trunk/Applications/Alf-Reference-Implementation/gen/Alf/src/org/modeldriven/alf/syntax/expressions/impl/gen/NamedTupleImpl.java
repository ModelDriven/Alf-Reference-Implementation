
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTupleImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.TupleImpl {

	public NamedTupleImpl(NamedTuple self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.NamedTuple getSelf() {
		return (NamedTuple) this.self;
	}

} // NamedTupleImpl

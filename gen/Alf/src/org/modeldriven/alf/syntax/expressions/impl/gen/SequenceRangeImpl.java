
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
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRangeImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceElementsImpl {

	public SequenceRangeImpl(SequenceRange self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.SequenceRange getSelf() {
		return (SequenceRange) this.self;
	}

	/**
	 * The multiplicity lower bound of a sequence range is 0.
	 **/
	public boolean sequenceRangeLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The multiplicity uper bound of a sequence range is * (since it is not
	 * possible, in general, to statically determine a more constrained upper
	 * bound).
	 **/
	public boolean sequenceRangeUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // SequenceRangeImpl

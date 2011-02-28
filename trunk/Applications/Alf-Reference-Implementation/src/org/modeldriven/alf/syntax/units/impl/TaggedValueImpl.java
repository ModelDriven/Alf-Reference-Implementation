
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.units.TaggedValue;

/**
 * An assignment of a value to an attribute of an applied stereotype.
 **/

public class TaggedValueImpl extends SyntaxElementImpl {

	public TaggedValueImpl(TaggedValue self) {
		super(self);
	}

	@Override
	public TaggedValue getSelf() {
		return (TaggedValue) this.self;
	}

} // TaggedValueImpl

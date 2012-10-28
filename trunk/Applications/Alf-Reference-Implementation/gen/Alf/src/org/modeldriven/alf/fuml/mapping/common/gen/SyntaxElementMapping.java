
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.common.gen;

import org.modeldriven.alf.fuml.mapping.FumlMapping;

import org.modeldriven.alf.syntax.common.SyntaxElement;

public abstract class SyntaxElementMapping extends FumlMapping {

	public SyntaxElement getSyntaxElement() {
		return (SyntaxElement) this.getSource();
	}

} // SyntaxElementMapping


/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units.gen;

import org.modeldriven.alf.mapping.fuml.common.gen.DocumentedElementMapping;

import org.modeldriven.alf.syntax.units.Member;

public abstract class MemberMapping extends DocumentedElementMapping {

	public Member getMember() {
		return (Member) this.getSource();
	}

} // MemberMapping


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

public class ReceptionDefinition extends Member {

	private QualifiedName signal = null;

	public ReceptionDefinition(QualifiedName signal) {
		this.signal = signal;

		ArrayList<String> names = signal.getNames();
		this.setName(names.get(names.size() - 1));
	} // ReceptionDefinition

	public QualifiedName getSignal() {
		return this.signal;
	} // getSignal

	public void print(String prefix) {
		super.print(prefix);
		this.getSignal().printChild(prefix);
	} // print

} // ReceptionDefinition


/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class AcceptStatement extends Statement {

	private ArrayList<AcceptBlock> acceptBlocks = new ArrayList<AcceptBlock>();

	public void addAcceptBlock(AcceptBlock acceptBlock) {
		this.acceptBlocks.add(acceptBlock);
	} // addAcceptBlock

	public ArrayList<AcceptBlock> getAcceptBlocks() {
		return this.acceptBlocks;
	} // getAcceptBlocks

	public void print(String prefix) {
		super.print(prefix);

		for (AcceptBlock block : this.getAcceptBlocks()) {
			block.printChild(prefix);
		}
	} // print

} // AcceptStatement

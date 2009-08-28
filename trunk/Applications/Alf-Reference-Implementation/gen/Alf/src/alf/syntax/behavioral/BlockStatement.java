
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class BlockStatement extends Statement {

	private Block block = null;

	public BlockStatement(Block block) {
		this.block = block;
	} // BlockStatement

	public Block getBlock() {
		return this.block;
	} // getBlock

	public void print(String prefix) {
		super.print(prefix);
		this.getBlock().printChild(prefix);
	} // print

} // BlockStatement

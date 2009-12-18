
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class AcceptBlock extends SyntaxNode {

	private String name = "";
	private QualifiedNameList signals = null;
	private Block block = null;

	public AcceptBlock(String name, QualifiedNameList signals) {
		this.name = name;
		this.signals = signals;
	} // AcceptBlock

	public String getName() {
		return this.name;
	} // getName

	public QualifiedNameList getSignals() {
		return this.signals;
	} // getSignals

	public void setBlock(Block block) {
		this.block = block;
	} // setBlock

	public Block getBlock() {
		return this.block;
	} // getBlock

	public String toString() {
		return super.toString() + " name:" + this.getName();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getSignals().printChild(prefix);

		Block block = this.getBlock();
		if (block != null) {
			block.printChild(prefix);
		}

	} // print

} // AcceptBlock

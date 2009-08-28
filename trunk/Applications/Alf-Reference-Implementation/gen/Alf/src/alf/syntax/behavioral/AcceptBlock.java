
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

public class AcceptBlock extends Node {

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

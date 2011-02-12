
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A clause in a switch statement with a set of cases and a sequence of
 * statements that may be executed if one of the cases matches the switch value.
 **/

public class SwitchClause extends SyntaxElement implements ISwitchClause {

	private ArrayList<IExpression> case_ = new ArrayList<IExpression>();
	private IBlock block = null;

	public ArrayList<IExpression> getCase() {
		return this.case_;
	}

	public void setCase(ArrayList<IExpression> case_) {
		this.case_ = case_;
	}

	public void addCase(IExpression case_) {
		this.case_.add(case_);
	}

	public IBlock getBlock() {
		return this.block;
	}

	public void setBlock(IBlock block) {
		this.block = block;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<IExpression> case_ = this.getCase();
		if (case_ != null) {
			for (IExpression item : this.getCase()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
		IBlock block = this.getBlock();
		if (block != null) {
			block.print(prefix + " ");
		}
	}
} // SwitchClause


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

public class InLineStatement extends Statement {

	private String identifier = "";
	private String language = "";
	private String code = "";

	public InLineStatement(String identifier, String language, String code) {
		this.identifier = identifier;
		this.language = language;
		this.code = code;
	} // InLineStatement

	public String getIdentifier() {
		return this.identifier;
	} // getIdentifier

	public String getLanguage() {
		return this.language;
	} // getLanguage

	public String getCode() {
		return this.code;
	} // getCode

	public String toString() {
		return super.toString() + " identifier:" + getIdentifier()
				+ " language:" + this.getLanguage();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		System.out.println(this.getCode());
	} // print

} // InLineStatement

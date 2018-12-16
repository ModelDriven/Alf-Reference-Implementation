
/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.interactive.execution;

import java.io.StringReader;
import java.util.Scanner;

import org.modeldriven.alf.fuml.impl.execution.Executor;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.ParserImpl;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ReturnStatement;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Element;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class AlfInteractive extends org.modeldriven.alf.fuml.impl.execution.Alf {
	
	protected String libraryDirectory;
	protected String modelDirectory;
	
	protected int counter = 0;
	protected ValueList result = null;
	
	public AlfInteractive(String libraryDirectory, String modelDirectory) {
		super();
		this.libraryDirectory = libraryDirectory;
		this.modelDirectory = modelDirectory;
		this.setLibraryDirectory(libraryDirectory);
		this.setModelDirectory(modelDirectory);
	}
	
	public ValueList getResult() {
		return this.result;
	}
	
	protected UnitDefinition makeUnit(Expression expression) {
		FormalParameter result = new FormalParameter();
		result.setName("result");
		result.setDirection("return");
		result.setLower(0);
		result.setUpper(-1);
		result.setType(ElementReferenceImpl.any);
		
		ReturnStatement statement = new ReturnStatement();
		statement.setExpression(expression);
		
		Block body = new Block();
		body.addStatement(statement);

		ActivityDefinition activity = new ActivityDefinition();
		activity.getImpl().setExactName("Activity_" + counter);	
		activity.addOwnedMember(result);
		activity.setBody(body);
		
		UnitDefinition unit = new UnitDefinition();
		unit.setDefinition(activity);
		unit.getImpl().addImplicitImports();
		activity.setUnit(unit);
		
		return unit;
	}
	
	protected ValueList execute(Behavior behavior) {
		ParameterValueList output = ((Executor)this.getLocus().getExecutor()).getBase().execute(
				((org.modeldriven.alf.fuml.impl.uml.Behavior)behavior).getBase(), null,
                new ParameterValueList());
		return output.isEmpty()? null: output.get(0).values;
	}
	
	@Override
	public UnitDefinition execute(UnitDefinition unit) {
		if (unit != null) {
			NamespaceDefinition definition = unit.getDefinition();
			Mapping elementMapping = definition.getImpl().getMapping();
			if (elementMapping == null) {
				this.println(definition.getName() + " is unmapped.");
				return null;
			} else {
				Element element = ((FumlMapping)elementMapping).getElement();
				this.result = this.execute((Behavior)element);
			}
		}
		return unit;
	}
	
	protected void reset() {
		this.getRootScopeImpl().resetModelNamespace();
    	this.setLibraryDirectory(this.libraryDirectory);
    	this.setModelDirectory(this.modelDirectory);
	}
	
	public ValueList eval(String input) {
		this.result = null;
		Parser parser = new ParserImpl(new StringReader(input));
		parser.setFileName(this.counter + "");
		try {
			this.process(this.makeUnit(parser.ExpressionEOF()));
		} catch (Throwable e) {
            System.out.println(e.getMessage());
		}
		reset();
		return this.result;
	}
	
	public void printResult() {
		ValueList result = this.getResult();
		if (result != null) {
			if (result.isEmpty()) {
				System.out.println("null");
			} else {
				for (Value value: result) {
					System.out.print(value + " ");
				}
				System.out.println();
			}
		}
		System.out.println();
	}
	
	@Override
    public void run(String[] args) {
        System.out.println("Alf Reference Implementation v" + ALF_VERSION);
        System.out.println("Initializing...");
    	this.setLibraryDirectory(args[0]);
    	this.setModelDirectory(args[1]);
        this.loadResources();
        this.eval("null");
        try (Scanner in = new Scanner(System.in)) {
	        do {
	        	System.out.print(++this.counter + "> ");
	        	String input = in.nextLine().trim();
	        	if (!input.isEmpty()) {
	        		if (input.equals("@")) {
	        			break;
	        		} else {
	        			this.eval(input);
	        			this.printResult();
	        		}
	        	}
	        } while(true);
        }
    }
	
    public static void main(String[] args) {
        if (args.length < 2) {
        	System.out.println("Usage: alfi library-directory model-directory");
        	return;
        }
        new AlfInteractive(args[0], args[1]).run(args);
    }
}

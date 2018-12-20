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
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeSet;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.units.NamespaceDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.ParserImpl;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Element;
import fUML.Semantics.Classes.Kernel.Reference;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;

public class AlfInteractive extends org.modeldriven.alf.fuml.impl.execution.Alf {
	
	protected int counter = 0;
	protected boolean isRedirectErr = false;
	protected boolean isRun = false;
	protected ValueList result = null;
	
	protected AlfWorkspace workspace = new AlfWorkspace(this);

	public AlfInteractive() {
        super();
	}
	
	public AlfInteractive(String libraryDirectory, String modelDirectory) {
		this();
		this.setLibraryDirectory(libraryDirectory);
		this.setModelDirectory(modelDirectory);
		this.initialize();
	}
	
	public AlfInteractive(String libraryDirectory, String modelDirectory, boolean isRedirectErr) {
		this(libraryDirectory, modelDirectory);
		this.setIsRedirectErr(isRedirectErr);
	}
	
	public void initialize() {
        this.loadResources();
        this.eval(";");
 	}
	
	public boolean isRedirectErr() {
		return this.isRedirectErr;
	}
	
	public void setIsRedirectErr(boolean isRedirectErr) {
		this.isRedirectErr = isRedirectErr;
	}
	
	public boolean isRun() {
		return this.isRun;
	}
	
	public void setIsRun(boolean isRun) {
		this.isRun = isRun;
	}
	
	public ValueList getResult() {
		return this.result;
	}
	
	public AlfWorkspace getWorkspace() {
		return this.workspace;
	}
	
	protected void printErr(String message) {
		if (this.isRedirectErr) {
			System.out.println(message);
		} else { 
			System.err.println(message);
		}
	}
	
	@Override
	protected void println(String message) {
		this.printErr(message);
	}
	
	@Override
	public Collection<ConstraintViolation> check(UnitDefinition unit) {
		if (unit == null) {
			return null;
		} else {
			if (this.counter == 0) {
				return super.check(unit);
			} else {
				NamespaceDefinition modelScope = RootNamespace.getModelScope(unit);
				modelScope.deriveAll();
				Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
				for (Member member: AlfInteractiveUtil.getUnmappedMembers(modelScope)) {
					violations.addAll(member.checkConstraints());
				}
				if (!violations.isEmpty()) {
					this.printConstraintViolations(violations);
				}
				return violations;
			}
		}
	}
	
	@Override
	public UnitDefinition process(UnitDefinition unit) {
		if (unit != null) {
			unit.getImpl().addImplicitImports();
			if (this.counter == 0) {
				unit = super.process(unit);
			} else {
				Collection<ConstraintViolation> violations = this.check(unit);
				if (violations.isEmpty()) {
					NamespaceDefinition modelScope = RootNamespace.getModelScope(unit);
					for (Member member: AlfInteractiveUtil.getUnmappedMembers(modelScope)) {
						if (member instanceof NamespaceDefinition) {
							NamespaceDefinitionMapping mapping = 
									(NamespaceDefinitionMapping)this.map((NamespaceDefinition)member);
							if (mapping == null) {
								return null;
							} else {
								try {
									mapping.mapBody();
								} catch (MappingError e) {
					                this.printErr("Mapping failed.");
					                this.printErr(e.getMapping().toString());                  
					                this.printErr(" error: " + e.getMessage());
					                return null;
								}
							}
							
						}
					}
					return this.execute(unit);
				}
			}
		}
		return null;
	}

	public UnitDefinition process(UnitDefinition unit, boolean isRun) {
		this.setIsRun(isRun);
		unit = this.process(unit);
		this.setIsRun(false);
		return unit;
	}
	
	@Override
	public UnitDefinition execute(UnitDefinition unit) {
		this.result = null;
		if (unit != null && this.isRun()) {
			NamespaceDefinition definition = unit.getDefinition();
			Mapping elementMapping = definition.getImpl().getMapping();
			if (elementMapping == null) {
				this.printErr(definition.getName() + " is unmapped.");
				return null;
			} else {
				Element element = ((FumlMapping)elementMapping).getElement();
				if (element instanceof Behavior) {
					this.result = this.workspace.execute((Behavior)element);
				} else {
					this.printErr(definition.getName() + " is not a behavior.");
				}
			}
		}
		return unit;
	}
	
	protected void reset() {
		ModelNamespace modelScope = this.rootScopeImpl.getModelNamespace();
		modelScope.setOwnedMember(AlfInteractiveUtil.getMappedMembers(modelScope));
		modelScope.setMember(null);
		this.counter++;
	}
	
	protected Parser createParser(String input) {
		Parser parser = new ParserImpl(new StringReader(input));
		parser.setFileName(this.counter + "");
		return parser;
	}
	
	public ValueList eval(String input) {
		this.result = null;
		Parser parser = this.createParser(input);
		try {
			String unitName = "_" + this.counter;
			try {
				this.process(this.workspace.makeUnit(unitName, parser.ExpressionEOF()), true);
			} catch (ParseException e) {
				parser = this.createParser(input);
				try {
					this.process(this.workspace.makeUnit(unitName, parser.StatementEOF()), true);
				} catch (ParseException e1) {
					parser = this.createParser(input);
					try {
						this.process(parser.UnitDefinitionEOF(), false);
					} catch (ParseException e2) {
						throw e1.getBeginColumn() > e2.getBeginColumn()? e1: e2;
					}
				}
			}
		} catch (ParseException | TokenMgrError e) {
			this.printErr(e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
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
					System.out.print(value);
					if (value instanceof Reference) {
						System.out.println();
					} else {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public void run(String input) {
		if (input != null && !input.isEmpty()) {
			this.eval(input);
			this.printResult();
		}
	}
	
	public void run() {
        try (Scanner in = new Scanner(System.in)) {
	        do {
	        	System.out.print(this.counter + "> ");
	        	String input = in.nextLine().trim();
	        	if ("@exit".equals(input)) {
	        		break;
	        	} else {
	        		run(input);
	        	}
	        } while(true);
        }
    }
	
	@Override
	public void run(String[] args) {
		this.setLibraryDirectory(args[0]);
		this.setModelDirectory(args[1]);
		this.run();
	}
		 
   public static void main(String[] args) {
        if (args.length < 2) {
        	System.out.println("Usage: alfi library-directory model-directory");
        	return;
        }
        System.out.println("Alf Reference Implementation v" + ALF_VERSION);
        System.out.println("Initializing...");
        new AlfInteractive(args[0], args[1], true).run();
    }
}

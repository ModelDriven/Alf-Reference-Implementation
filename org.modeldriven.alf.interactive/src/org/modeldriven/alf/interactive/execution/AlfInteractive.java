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

import org.apache.log4j.helpers.LogLog;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.units.NamespaceDefinitionMapping;
import org.modeldriven.alf.interactive.parser.InteractiveParserImpl;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.units.ImportReference;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.syntax.units.impl.BoundClassifierImpl;
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
		this.counter = 0;
        this.loadResources();        
        if (this.process(AlfWorkspace.INSTANCE.getUnit(), false) == null) {
        	throw new Error("Initialization failed.");
        }
        this.counter = 1;
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
				NamespaceDefinition modelScope = this.getRootScopeImpl().getModelNamespace();
				modelScope.deriveAll();
				Collection<ConstraintViolation> violations = unit.checkConstraints();
				NamespaceDefinition definition = unit.getDefinition();
				if (!definition.getImpl().isDistinguishableFromAll(AlfWorkspace.INSTANCE.getOwnedMembers())) {
					violations.add(new ConstraintViolation(
							"namespaceDefinitionMemberDistinguishability", definition));
				}
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
	public FumlMapping map(NamespaceDefinition definition) {
		FumlMapping mapping = super.map(definition);
		if (mapping != null) {
			try {
				((NamespaceDefinitionMapping)mapping).mapBody();
			} catch (MappingError e) {
                this.printErr("Mapping failed.");
                this.printErr(e.getMapping().toString());                  
                this.printErr(" error: " + e.getMessage());
                mapping = null;
			}
		}
		return mapping;
	}
	
	@Override
	public UnitDefinition process(UnitDefinition unit) {
		if (unit == null || unit instanceof MissingUnit) {
			return null;
		} else {
			unit.getImpl().addImplicitImports();
			if (this.counter == 0) {
				return super.process(unit);
			} else {
				NamespaceDefinition definition = unit.getDefinition();
				Collection<ConstraintViolation> violations = this.check(unit);
				if (!violations.isEmpty()) {
					AlfWorkspace.INSTANCE.removeOwnedMember(definition);
					return null;
				} else {
					NamespaceDefinition modelScope = this.getRootScopeImpl().getModelNamespace();
					for (Member member: AlfInteractiveUtil.getUnmappedMembers(modelScope)) {
						if (member instanceof NamespaceDefinition) {
							if (this.map((NamespaceDefinition)member) == null) {
								AlfWorkspace.INSTANCE.removeOwnedMember(definition);
								return null;
							}
						}
					}
					if (this.map(definition) == null) {
						AlfWorkspace.INSTANCE.removeOwnedMember(definition);
						return null;
					}
					return this.execute(unit);
				}
			}
		}
	}

	public UnitDefinition process(UnitDefinition unit, boolean isRun) {
		boolean wasRun = this.isRun();
		this.setIsRun(isRun);
		unit = this.process(unit);
		this.setIsRun(wasRun);
		return unit;
	}
	
	public UnitDefinition process(ImportReference importReference) {
		UnitDefinition unit = AlfWorkspace.INSTANCE.addImport(importReference);
		Collection<ConstraintViolation> violations = this.check(unit);
		if (violations.isEmpty()) {
			return unit;
		} else {
			AlfWorkspace.INSTANCE.removeImport(importReference);
			return null;
		}
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
					this.result = AlfWorkspace.INSTANCE.execute((Behavior)element, this.getLocus());
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
		modelScope.getMember();
		this.counter++;
	}
	
	protected InteractiveParserImpl createParser(String input) {
		InteractiveParserImpl parser = new InteractiveParserImpl(new StringReader(input));
		parser.setFileName(this.counter + "");
		return parser;
	}
	
	public ValueList eval(String input) {
        BoundClassifierImpl.clearBoundClassifiers();
		this.result = null;
		InteractiveParserImpl parser = this.createParser(input);
		try {
			try {
				this.process(parser.PublicImportDeclaration());
			} catch (ParseException e1) {
				if (e1.getBeginColumn() > 1) {
					throw e1;
				} else {
					parser = this.createParser(input);
					try {
						this.process(AlfInteractiveUtil.makeUnit(parser.NamespaceDefinitionEOF()), false);
					} catch (ParseException e2) {
						if (e2.getBeginColumn() > 1) {
							throw e2;
						} else {
							parser = this.createParser(input + ";");						
							String unitName = "_" + this.counter;
							this.process(AlfInteractiveUtil.makeUnit(unitName, parser.StatementSequenceEOF()), true);
						}
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
	
	@Override
	public void configure() {
		LogLog.setQuietMode(true);
		super.configure();
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

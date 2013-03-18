/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.execution;

import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.FirstChoiceStrategy;

import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

import org.modeldriven.alf.eclipse.papyrus.library.channel.StandardInputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.channel.StandardOutputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.library.libraryclass.ImplementationObject;
import org.modeldriven.alf.eclipse.units.RootNamespaceImpl;

import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.fuml.execution.Object_;

import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.OpaqueBehavior;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.PrimitiveType;

public class Fuml {
	
	private String umlDirectory = ".";
	private boolean isVerbose = false;
	
	private final RootNamespaceImpl rootScopeImpl = new RootNamespaceImpl();

	private org.modeldriven.alf.eclipse.papyrus.execution.Locus locus;
	
	public org.modeldriven.alf.eclipse.papyrus.execution.Locus getLocus() {
		return this.locus;
	}

    public static void setDebugLevel(Level level) {
        Logger logger = Logger.getLogger(org.eclipse.papyrus.moka.fuml.debug.Debug.class);
        if (logger != null) {
        	logger.setLevel(level);
        }
    }
    
    public void setUmlLibraryDirectory(String umlLibraryDirectory) {
    	this.rootScopeImpl.setLibraryDirectory(umlLibraryDirectory);
    }
    
    public void setUmlDirectory(String umlDirectory) {
    	this.umlDirectory = umlDirectory;
    }
    
    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
        this.rootScopeImpl.setIsVerbose(this.isVerbose);
    }
    
    private void createLocus() {
        this.locus = new org.modeldriven.alf.eclipse.papyrus.execution.Locus();
        org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ExecutionFactory factory = locus.getFactory().getBase(); 
        factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        factory.setStrategy(new FIFOGetNextEventStrategy());
        factory.setStrategy(new FirstChoiceStrategy());       
    }
    
    private void addPrimitiveTypes(ResourceSet resourceSet) {
    	try {
	    	Package primitiveTypes = this.getPackage(
	    			"Alf::Library::PrimitiveTypes");
	    	for (NamedElement element: primitiveTypes.getMember()) {
	    		if (element instanceof PrimitiveType) {
	    			this.locus.getFactory().addBuiltInType((PrimitiveType)element);
	    			this.printVerbose("Added " + element.getQualifiedName());
	    		}
	    	}
    	} catch (ElementResolutionError e) {
    		this.println(e.getMessage());
    	}
    }
    
    private void addPrimitiveBehaviorPrototypes(ResourceSet resourceSet) {
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Or", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Or.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Xor", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Xor.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::And", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.And.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Not", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Not.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::Implies", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.Implies.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::ToString", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.ToString.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::BooleanFunctions::ToBoolean", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.boolean_.ToBoolean.class);
    
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Neg", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Neg.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Abs", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Abs.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::+", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Add.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::-", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Minus.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::*", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Times.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Div", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Div.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Mod", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Mod.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Max", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Max.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::Min", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Min.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::<", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Lower.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::>", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.Greater.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::<=", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.LowerOrEqual.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::>=", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.GreaterOrEqual.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::ToString", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.ToString.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::ToUnlimitedNatural", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.ToUnlimitedNatural.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::IntegerFunctions::ToInteger", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.integer.ToInteger.class);
    	
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::ListFunctions::ListSize", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.list.ListSize.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::ListFunctions::ListGet", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.list.ListGet.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::ListFunctions::ListConcat", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.list.ListConcat.class);
    	
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::StringFunctions::Concat", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.string.Concat.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::StringFunctions::Size", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.string.Size.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::StringFunctions::Substring", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.string.Substring.class);
    	
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::Max", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Max.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::Min", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Min.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::<", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Lower.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::>", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.Greater.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::<=", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.LowerOrEqual.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::>=", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.GreaterOrEqual.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::ToString", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.ToString.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::ToInteger", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.ToInteger.class);
    	this.createPrimitiveBehaviorPrototype(
    			"FoundationalModelLibrary::PrimitiveBehaviors::UnlimitedNaturalFunctions::ToUnlimitedNatural", 
    			org.eclipse.papyrus.moka.fuml.standardlibrary.library.unlimitednatural.ToUnlimitedNatural.class);
    	
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::IsSet", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringIsSetFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::BitLength", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringBitLengthFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToBitString", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringToBitStringFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToInteger", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringToIntegerFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToHexString", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringToHexStringFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::ToOctalString", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringToOctalStringFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::~", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringComplementFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::|", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringOrFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::^", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringXorFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::&", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringAndFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::<<", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringShiftLeftFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::>>", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringShiftRightFunctionBehaviorExecution.class);
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::BitStringFunctions::>>>", 
    			org.modeldriven.alf.eclipse.papyrus.library.bitstringfunctions.BitStringUnsignedShiftRightFunctionBehaviorExecution.class);
    	
    	this.createPrimitiveBehaviorPrototype(
    			"Alf::Library::PrimitiveBehaviors::IntegerFunctions::ToNatural", 
    			org.modeldriven.alf.eclipse.papyrus.library.integerfunctions.IntegerToNaturalFunctionBehaviorExecution.class);
}
    
    private void createPrimitiveBehaviorPrototype(
    		String behaviorName, 
    		Class<? extends org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution> prototypeClass) {
		try {
			Classifier behavior = this.getClassifier(behaviorName);
			if (behavior instanceof OpaqueBehavior) {
		        OpaqueBehaviorExecution execution = null;		        
		        try {
		            execution = new org.modeldriven.alf.eclipse.papyrus.execution.OpaqueBehaviorExecution(
		                prototypeClass.newInstance());
		        } catch (Exception e) {
		        	execution = new UnimplementedBehaviorExecution();
		        }
		        execution.addType((OpaqueBehavior)behavior);
		        
		        this.locus.getFactory().addPrimitiveBehaviorPrototype(execution);
		        this.printVerbose("Added " + behavior.getQualifiedName());
			}
    	} catch (ElementResolutionError e) {
    		this.println(e.getMessage());
    	}
    }
        
    protected void createSystemServices(ResourceSet resourceSet) {
    	String basicInputOutput = "Alf::Library::BasicInputOutput";
    	
    	try {
	    	Classifier standardOutputChannel = this.getClassifier(
	    			basicInputOutput + "::StandardOutputChannel");
	        this.createSystemService
	            (standardOutputChannel, new StandardOutputChannelObject());
    	} catch (ElementResolutionError e) {
    		this.println(e.getMessage());
    	}
        
    	try {
	        Classifier standardInputChannel = this.getClassifier(
	        		basicInputOutput + "::StandardInputChannel");
	        this.createSystemService
	            (standardInputChannel, new StandardInputChannelObject());
    	} catch (ElementResolutionError e) {
    		this.println(e.getMessage());
    	}
        
    	try {
	        Classifier statusType = this.getClassifier(
	        		basicInputOutput + "::Status");
	        if (statusType instanceof DataType) {
	            Status.setStatusType(((org.modeldriven.alf.eclipse.uml.DataType)statusType).getBase());
	        } else {
	            this.println("Cannot find Status datatype.");
	        }
    	} catch (ElementResolutionError e) {
    		this.println(e.getMessage());
    	}
    }
    
    private void createSystemService (
            Classifier type,
            ImplementationObject object) {
        if (type instanceof Class_) {
            org.eclipse.uml2.uml.Class class_ = 
                    ((org.modeldriven.alf.eclipse.uml.Class_)type).getBase();
            object.types.add(class_);
            this.locus.add(object);
            this.printVerbose("Instantiated " + type.getQualifiedName() + 
                    " as " + object.getClass().getName());
        }
    }
    
    public class ElementResolutionError extends Exception {

		private static final long serialVersionUID = 1L;
		
		public ElementResolutionError(String message) {
			super(message);
		}
    	
    }
    
    public Package getPackage(String qualifiedName) throws ElementResolutionError {
    	Element element = this.getElement(qualifiedName);
    	if (!(element instanceof Package)) {
    		throw new ElementResolutionError(qualifiedName + " is not a Package.");
    	} else {
    		return (Package)element;
    	}
    }
    
    public Classifier getClassifier(String qualifiedName) throws ElementResolutionError {
    	Element element = this.getElement(qualifiedName);
    	if (!(element instanceof Classifier)) {
    		throw new ElementResolutionError(qualifiedName + " is not a Classifier.");
    	} else {
    		return (Classifier)element;
    	}
    }
    
    public Element getElement(String qualifiedName) throws ElementResolutionError {
    	Element element = null;
    	Collection<org.eclipse.uml2.uml.NamedElement> elements = 
    			UMLUtil.findNamedElements(
    					this.rootScopeImpl.getResourceSet(), qualifiedName);
    	if (elements.size() == 0) {
    		throw new ElementResolutionError("Cannot find " + qualifiedName);
    	} else if (elements.size() > 1) {
    		throw new ElementResolutionError("More than one " + qualifiedName);
    	} else {
    		element = org.modeldriven.alf.eclipse.uml.Element.
    				wrap((org.eclipse.uml2.uml.Element)elements.toArray()[0]);
    	}
    	return element;    	
    }
    
    public String parseArgs(String[] args) {
        Logger logger = Logger.getLogger(org.eclipse.papyrus.moka.fuml.debug.Debug.class);
        Level level = logger.getLevel();

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (arg.charAt(0) != '-') {
                break;
            }
            String option = arg.substring(1);
            i++;
            if (i < args.length) {
                if (option.equals("v")) {
                    this.setIsVerbose(true);
                 } else if (option.matches("[dlu]")) {
                    arg = args[i];
                    if (arg.length() > 0 && arg.charAt(0) == '-') {
                        return null;
                    }
                    i++;
                    if (option.equals("d")) {
                    	setDebugLevel(Level.toLevel(arg, level));
                    	level = logger.getLevel();
                    } else if (option.equals("l")) {
                    	this.setUmlLibraryDirectory(arg);
                    } else if (option.equals("u")) {
                    	this.setUmlDirectory(arg);
                    }
                } else {
                    return null;
                }
            }
        }
        
        return i == args.length - 1? args[i]: null;
    }
    
    public static Operation getInitializationOperation(Class_ class_) {
    	Operation operation = null;
    	String initializerName = class_.getName() + "$initialization$";
    	int n = initializerName.length();
    	
    	for (Operation ownedOperation: class_.getOwnedOperation()) {
    		String operationName = ownedOperation.getName();
    		if (operationName != null && 
    				operationName.length() > n &&
    				operationName.substring(0, n).equals(initializerName) &&
    				operationName.substring(n).matches("[0-9]+")) {
    			operation = ownedOperation;
    		}
    	}
    	
    	return operation;
    }
    
    public void initializeEnvironment() {
        this.rootScopeImpl.initialize();	
        
        ResourceSet resourceSet = this.rootScopeImpl.getResourceSet();
        
    	this.createLocus();
    	this.addPrimitiveTypes(resourceSet);
    	this.addPrimitiveBehaviorPrototypes(resourceSet);
    	this.createSystemServices(resourceSet);
    }
    
    public Resource getResource(String name) {
    	Resource resource = 
    			this.rootScopeImpl.getResource(this.umlDirectory, name);
    	if (resource != null) {
			Map<URI, URI> map = resource.getResourceSet().getURIConverter().getURIMap();
			map.put(URI.createURI(""), resource.getURI());
    	}
    	return resource;	
    }
    
    public void execute(Classifier element) {
        if (element instanceof Behavior && 
        		((Behavior)element).getOwnedParameter().isEmpty() ||
        		element instanceof Class_ && 
        		((Class_)element).getIsActive() && 
        		!((Class_)element).getIsAbstract() && 
        		((Class_)element).getClassifierBehavior() != null) {

        	this.printVerbose("Executing...");
        	if (element instanceof Behavior) {
        		this.locus.getExecutor().execute((Behavior)element, null);
        	} else {
        		// Instantiate active class.
        		Class_ class_ = (Class_)element;
        		Object_ object = locus.instantiate(class_);

        		// Initialize the object.
        		Operation initializer = getInitializationOperation(class_);        		
        		if (initializer != null) {
        			this.locus.getExecutor().execute(
        					((Behavior)initializer.getMethod().get(0)), 
        					object);
        		}

        		// Execute the classifier behavior.
        		object.startBehavior(class_);
        	}

        } else if (element instanceof Behavior) {
        	this.println("Cannot execute a behavior with parameters.");
        } else if (element instanceof Class_) {
        	Class_ class_ = (Class_)element;
        	if (!class_.getIsActive()) {
        		this.println("Cannot execute a class that is not active.");
        	} else if (class_.getIsAbstract()) {
        		this.println("Cannot execute an abstract class.");
        	} else {
        		this.println("Cannot execute a class without a classifier behavior.");
        	}
        } else {
        	this.println("Unit not executable.");
        }
    }
    
   public void execute(String name) {
	   Resource resource = null;
	   try {
		   resource = this.getResource(name);
	   } catch (Exception e) {
		   this.println(e.getMessage());
	   }
	   if (resource != null) {
		   try {
			   this.initializeEnvironment();
			   Classifier element = this.getClassifier("Model::" + name);
			   this.execute(element);
		   } catch (ElementResolutionError e) {
			   this.println(e.getMessage());
		   }
	   }
   }
    
   protected void printVerbose(String message) {
	   if (this.isVerbose) {
		   this.println(message);
	   }
   }

   protected void println(String message) {
	   System.out.println(message);
   }

   public Fuml() {
	   Logger logger = Logger.getLogger(org.eclipse.papyrus.moka.fuml.debug.Debug.class);
	   if (logger != null) {
		   PropertyConfigurator.configure("log4j.properties");
	   }

	   this.setUmlLibraryDirectory("Libraries");
   }

   public Fuml(String[] args) {
	   this();

	   String name = this.parseArgs(args);

	   if (name != null) {

		   int l1 = name.length();
		   int l2 = UMLResource.FILE_EXTENSION.length() + 1;
		   if (l1 > l2 && name.substring(l1 - l2).
				   equals("." + UMLResource.FILE_EXTENSION)) {
			   name = name.substring(0, l1 - l2);
		   }

		   this.execute(name);

	   } else {
		   this.println("Usage is");
		   this.println("  fuml [options] file");
		   this.println("where file is the name of an executable unit and");
		   this.println("allowable options are:");
		   this.println("  -d OFF|FATAL|ERROR|WARN|INFO|DEBUG|ALL");
		   this.println("            Set debug logging level (default is as configured)");
		   this.println("  -l path   Set UML library directory (default is \"Libraries\"");
		   this.println("  -u path   Set UML directory path (default is \".\"");
		   this.println("  -v        Set verbose mode");
	   }         
   }

   public static void main(String[] args) {
	   new Fuml(args);
   }

}

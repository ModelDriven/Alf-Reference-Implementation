package org.modeldriven.alf.eclipse.papyrus.execution;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.FirstChoiceStrategy;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionUtils;

import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

import org.modeldriven.alf.eclipse.papyrus.library.channel.StandardInputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.channel.StandardOutputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.library.libraryclass.ImplementationObject;
import org.modeldriven.alf.eclipse.units.RootNamespaceImpl;

import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.fuml.execution.Object_;

import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

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
	
	private String libraryDirectory = "Libraries";
	private String umlDirectory = ".";
	private boolean isVerbose = false;
	
	private final RootNamespaceImpl rootScopeImpl = new RootNamespaceImpl();

	private org.modeldriven.alf.eclipse.papyrus.execution.Locus locus;

    public static void setDebugLevel(Level level) {
        Logger logger = Logger.getLogger(org.eclipse.papyrus.moka.fuml.debug.Debug.class);
        logger.setLevel(level);
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
    	this.libraryDirectory = libraryDirectory;
    }
    
    public void setUmlDirectory(String umlDirectory) {
    	this.umlDirectory = umlDirectory;
    }
    
    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }
    
    private void createLocus() {
        this.locus = new org.modeldriven.alf.eclipse.papyrus.execution.Locus();
        org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ExecutionFactory factory = locus.getFactory().getBase(); 
        factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        factory.setStrategy(new FIFOGetNextEventStrategy());
        factory.setStrategy(new FirstChoiceStrategy());       
    }
    
    private void addPrimitiveTypes(Element scope) {
    	Package primitiveTypes = this.getPackage(
    			scope, "Alf::Library::PrimitiveTypes");
    	for (NamedElement element: primitiveTypes.getMember()) {
    		if (element instanceof PrimitiveType) {
    			this.locus.getFactory().addBuiltInType((PrimitiveType)element);
    			this.printVerbose("Added " + element.getQualifiedName());
    		}
    	}
    }
    
    private void addPrimitiveBehaviorPrototypes(Element scope) {
    	QualifiedName qualifiedName = new QualifiedName().getImpl().
    			addName("Alf").getImpl().
    			addName("Library").getImpl().
    			addName("PrimitiveBehaviors");
    	this.createPrimitiveBehaviorPrototypes(scope, qualifiedName);
    	
    	qualifiedName = new QualifiedName().getImpl().
    			addName("FoundationalModelLibrary").getImpl().
    			addName("PrimitiveBehaviors");
    	this.createPrimitiveBehaviorPrototypes(scope, qualifiedName);
    }
    
    private void createPrimitiveBehaviorPrototypes(Element scope, QualifiedName qualifiedName) {
    	String pathName = qualifiedName.getImpl().getPathName();
    	UnitDefinition unit = this.rootScopeImpl.resolveUnit(qualifiedName);
    	NamespaceDefinition definition = unit.getDefinition();
    	if (definition != null) {
	    	for (Member packageDefinition: definition.getOwnedMember()) {
	    		if (packageDefinition instanceof PackageDefinition) {
		    		if (packageDefinition.getIsStub()) {
		    			packageDefinition = packageDefinition.getSubunit().getDefinition();
		     		}
		    		if (packageDefinition != null) {
		    			String packageName = pathName + "::" + packageDefinition.getName();
		    			for (Member member: ((PackageDefinition) packageDefinition).getOwnedMember()) {
		    				if (member instanceof ActivityDefinition && member.getIsPrimitive()) {
		    					Classifier behavior = this.getClassifier(
		    							scope, 
		    							packageName + "::" + member.getName());
		    					if (behavior instanceof OpaqueBehavior) {
		    						OpaqueBehaviorExecution execution = 
		    								instantiatePrimitiveBehaviorPrototype(
		    										(ActivityDefinition)member, 
		    										(OpaqueBehavior)behavior);
		    						if (execution != null) {
		    							this.locus.getFactory().addPrimitiveBehaviorPrototype(execution);
		    							this.printVerbose("Added " + behavior.getQualifiedName());
		    						}
		    					}
		    				}
		    			}
		    		}
	    		}
	    	}
    	}
    }
    
    private static OpaqueBehaviorExecution instantiatePrimitiveBehaviorPrototype(
    		ActivityDefinition definition, OpaqueBehavior behavior) {
        OpaqueBehaviorExecution execution = new UnimplementedBehaviorExecution();
        try {
            execution =  new org.modeldriven.alf.eclipse.papyrus.execution.OpaqueBehaviorExecution(
            		(org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution)
                Class.forName(definition.getImpl().getPrimitiveBehaviorPrototypeName()).
                        newInstance());
        } catch (Exception e) {
        }
        execution.addType(behavior);
        return execution;
    }
    
    protected void createSystemServices(Element scope) {
    	String basicInputOutput = "Alf::Library::BasicInputOutput";
    	Classifier standardOutputChannel = this.getClassifier(
    			scope, basicInputOutput + "::StandardOutputChannel");
        this.createSystemService
            (standardOutputChannel, new StandardOutputChannelObject());
        
        Classifier standardInputChannel = this.getClassifier(
        		scope, basicInputOutput + "::StandardInputChannel");
        this.createSystemService
            (standardInputChannel, new StandardInputChannelObject());
        
        Classifier statusType = this.getClassifier(
        		scope, basicInputOutput + "::Status");
        if (statusType instanceof DataType) {
            Status.setStatusType(((org.modeldriven.alf.eclipse.uml.DataType)statusType).getBase());
        } else {
            System.out.println("Cannot find Status datatype.");
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
    
    private Package getPackage(Element scope, String qualifiedName) {
    	Element element = this.getElement(scope, qualifiedName);
    	if (!(element instanceof Package)) {
    		this.println(qualifiedName + " is not a Package.");
    		return null;
    	} else {
    		return (Package)element;
    	}
    }
    
    private Classifier getClassifier(Element scope, String qualifiedName) {
    	Element element = this.getElement(scope, qualifiedName);
    	if (!(element instanceof Classifier)) {
    		this.println(qualifiedName + " is not a Classifier.");
    		return null;
    	} else {
    		return (Classifier)element;
    	}
    }
    
    private Classifier getClassifier(Resource resource, String qualifiedName) {
    	Element element = this.getElement(resource, qualifiedName);
    	if (!(element instanceof Classifier)) {
    		this.println(qualifiedName + " is not a Classifier.");
    		return null;
    	} else {
    		return (Classifier)element;
    	}
    }
    
    private Element getElement(Element scope, String qualifiedName) {
    	Element element = null;
    	Collection<org.eclipse.uml2.uml.NamedElement> elements = 
    			NameResolutionUtils.getNamedElements(
    					qualifiedName, 
    					((org.modeldriven.alf.eclipse.uml.Element)scope).getBase(), 
    					null);
    	if (elements.size() == 0) {
    		this.println("Cannot find " + qualifiedName);
    	} else if (elements.size() > 1) {
    		this.println("More than one " + qualifiedName);
    	} else {
    		element = org.modeldriven.alf.eclipse.uml.Element.
    				wrap((org.eclipse.uml2.uml.Element)elements.toArray()[0]);
    	}
    	return element;    	
    }
    
    private Element getElement(Resource resource, String qualifiedName) {
    	Element element = null;
    	Collection<org.eclipse.uml2.uml.NamedElement> elements = 
    			UMLUtil.findNamedElements(resource, qualifiedName);
    	if (elements.size() == 0) {
    		this.println("Cannot find " + qualifiedName);
    	} else if (elements.size() > 1) {
    		this.println("More than one " + qualifiedName);
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
                    	this.setLibraryDirectory(arg);
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
    
    public void execute(String name) {
    	this.rootScopeImpl.setModelDirectory(this.libraryDirectory);
    	this.rootScopeImpl.setLibraryDirectory(this.umlDirectory);
        this.rootScopeImpl.setIsVerbose(this.isVerbose);
        // this.rootScopeImpl.initialize();	

    	Resource resource = 
    			this.rootScopeImpl.getResource(this.umlDirectory, name);
    	
    	if (resource != null) {
    		Classifier element = this.getClassifier(resource, "Model::" + name);
    		
    		if (element instanceof Behavior && 
    				((Behavior)element).getOwnedParameter().isEmpty() ||
    				element instanceof Class_ && 
    				((Class_)element).getIsActive() && 
    				!((Class_)element).getIsAbstract() && 
    				((Class_)element).getClassifierBehavior() != null) {

    			resource.getResourceSet().getURIConverter().getURIMap().
    				put(URI.createURI(""), resource.getURI());

    			this.createLocus();
    			this.addPrimitiveTypes(element);
    			this.addPrimitiveBehaviorPrototypes(element);
    			this.createSystemServices(element);

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
        PropertyConfigurator.configure("log4j.properties");
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
        	this.println("  -l path   Set Alf library directory (default is \"Libraries\"");
        	this.println("  -u path   Set UML directory path (default is \".\"");
        	this.println("  -v        Set verbose mode");
        }         
    }
    
	public static void main(String[] args) {
		new Fuml(args);
	}

}

package org.modeldriven.alf.mapping.fuml;

import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Semantics.Loci.LociL1.ExecutionFactory;
import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ControlFlow;
import fUML.Syntax.Activities.IntermediateActivities.ObjectFlow;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class FumlMapping extends Mapping {
    
    static private FumlMappingFactory fumlFactory = new FumlMappingFactory();
    static private ExecutionFactory executionFactory = null;
    static private SyntaxElement parsedElement = null;
    
    public FumlMapping() {
        this.setFactory(fumlFactory);
    }
    
    public static ExecutionFactory getExecutionFactory() {
        return executionFactory;
    }

    public static void setExecutionFactory(ExecutionFactory executionFactory) {
        FumlMapping.executionFactory = executionFactory;
    }
    
    public static SyntaxElement getParsedElement() {
        return parsedElement;
    }
    
    public static void addTo(
            StructuredActivityNode node, 
            List<Element> nestedElements,
            List<Element> outerElements) {
        for (Element element: nestedElements) {
            if (element instanceof ActivityNode) {
                node.addNode((ActivityNode)element);
            } else if (element instanceof ControlFlow) {
                node.addEdge((ActivityEdge)element);
            }
        }
        for (Element element: nestedElements) {
            if (element instanceof ObjectFlow) {
                ActivityEdge edge = (ActivityEdge)element;
                if (edge.source.inStructuredNode != node ||
                        edge.target.inStructuredNode != node) {
                    outerElements.add(edge);
                } else {
                    node.addEdge(edge);
                }
            }
        }
    }
    
    public Element getElement() {
        return null;
    }
    
    public abstract List<Element> getModelElements() throws MappingError;
    
    public FumlMapping fumlMap(Object source) {
        return (FumlMapping)this.map(source);
    }
    
    public void mapTo(Element element) throws MappingError {
        /*
        System.out.println("[mapTo] " + element);
        Object source = this.getSource();
        if (source instanceof SyntaxElement) {
            ((SyntaxElement) source).print("***", true);
        } else {
            System.out.println("***" + source);
        }
        */
    }
    
    protected void throwError(String errorMessage) throws MappingError {
        this.setErrorMessage(errorMessage);
        throw new MappingError(this, errorMessage);
    }
    
    public static FumlMapping getMapping(Object source) {
        return (FumlMapping) fumlFactory.getMapping(source);
    }

    public static FumlMapping parseAndMap(String fileName) {
        FumlMapping mapping = null;
        
        AlfParser.parseOptions("ucd");
        parsedElement = AlfParser.parse(fileName);
        
        if (parsedElement != null) {
            if (!AlfParser.constraintsCheck()) {
                AlfParser.printElement();
            } else {
                RootNamespace.getRootScope().checkConstraints();
                // RootNamespace.getRootScope().print("", true);
                mapping = getMapping(RootNamespace.getRootScope());
            }
        }
        
        return mapping;
    }

    public static void main(String[] args) {
        String fileName = null;
        
        if (args.length > 0) {
            fileName = args[0];
        }
        
        setExecutionFactory(new ExecutionFactoryL3());       
        FumlMapping mapping = parseAndMap(fileName);
        
        if (mapping != null) {
            try {
                mapping.getModelElements();
                System.out.println("Mapped successfully.");
            } catch (MappingError e) {
                System.out.println("Mapping failed.");
                System.out.println(e.getMapping());                    
                System.out.println(" error: " + e.getMessage());
            }
            mapping.print();
        }
    }
}

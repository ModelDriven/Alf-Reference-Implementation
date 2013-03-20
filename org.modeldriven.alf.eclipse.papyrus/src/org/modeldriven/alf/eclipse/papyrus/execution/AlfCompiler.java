package org.modeldriven.alf.eclipse.papyrus.execution;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.alf.compiler.IAlfCompiler;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.modeldriven.alf.eclipse.papyrus.units.RootNamespaceImpl;
import org.modeldriven.alf.eclipse.uml.ElementFactory;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.fuml.mapping.units.MemberMapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class AlfCompiler implements IAlfCompiler  {
	
	private static RootNamespaceImpl rootScopeImpl = null;

	public AlfCompiler() {
		if (rootScopeImpl == null) {
			rootScopeImpl = new RootNamespaceImpl();
            FumlMapping.setFumlFactory(new FumlMappingFactory());
            FumlMapping.setElementFactory(new ElementFactory());
		}
	}

	public UnitDefinition parse(
			Element contextElement, String textualRepresentation, Object[] args) {
		rootScopeImpl.setContext(contextElement);

		Parser parser = new Parser(new StringReader(textualRepresentation));
		if (contextElement instanceof NamedElement) {
			parser.setFileName(((NamedElement)contextElement).getName());
		}		
		try {
			UnitDefinition unit = parser.UnitDefinition();
			
			ModelNamespace modelScope = 
					(ModelNamespace)RootNamespace.getModelScope(unit);
            modelScope.deriveAll();
            
            Collection<ConstraintViolation> violations = modelScope.checkConstraints();
            if (violations.isEmpty()) {
            	return unit;
            } else {
                for (ConstraintViolation violation: violations) {
                    System.err.println(violation);
                }
                return null;
            }
        } catch (TokenMgrError e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
		return null;
	}
	
    public FumlMapping map(NamespaceDefinition definition) {
        try {
            FumlMapping mapping = FumlMapping.getMapping(definition);
        	mapping.getModelElements();
        	((MemberMapping)mapping).mapBody();
        	return mapping;
        } catch (MappingError e) {
        	System.err.println(e.getMapping().toString());                  
        	System.err.println(" error: " + e.getMessage());
        	return null;
        }
    }
    
	@Override
	public boolean validate(
			Element contextElement, String textualRepresentation, Object[] args) {
		return this.parse(contextElement, textualRepresentation, args) != null;
	}
	
	@Override
	public boolean compile(
			Element contextElement, String textualRepresentation, Object[] args) {
		UnitDefinition unit = this.parse(contextElement, textualRepresentation, args);
		if (unit != null) {
			FumlMapping mapping = this.map(unit.getDefinition());
			if (mapping != null) {
				org.modeldriven.alf.uml.Element element = mapping.getElement();
	            ElementReferenceImpl.replaceTemplateBindingsIn(element);
	            for (org.modeldriven.alf.uml.Element additionalElement: 
	            	RootNamespace.getAdditionalElements()) {
	            	ElementReferenceImpl.replaceTemplateBindingsIn(additionalElement);
	            }
				return this.update(contextElement, 
						((org.modeldriven.alf.eclipse.uml.Element)element).getBase());
			}
		}
		return false;
	}
	
	private boolean update(Element targetElement, Element sourceElement) {
		if (targetElement instanceof Activity && sourceElement instanceof Activity) {
			Activity targetActivity = (Activity)targetElement;
			Activity sourceActivity = (Activity)sourceElement;
			
			targetActivity.getNodes().clear();
			targetActivity.getGroups().clear();
			
			List<ActivityNode> ownedNodes = targetActivity.getOwnedNodes();
			ownedNodes.clear();
			ownedNodes.addAll(sourceActivity.getOwnedNodes());
			
			List<StructuredActivityNode> structuredNodes = 
					targetActivity.getStructuredNodes();
			structuredNodes.clear();
			structuredNodes.addAll(sourceActivity.getStructuredNodes());
			
			List<ActivityEdge> edges = targetActivity.getEdges();
			edges.clear();
			edges.addAll(sourceActivity.getEdges());
			
			List<Parameter> targetParameters = targetActivity.getOwnedParameters();
			List<Parameter> sourceParameters = sourceActivity.getOwnedParameters();
			for (ActivityNode node: ownedNodes) {
				if (node instanceof ActivityParameterNode) {
					ActivityParameterNode parameterNode = (ActivityParameterNode)node;
					int i = sourceParameters.indexOf(parameterNode.getParameter());
					if (i < targetParameters.size()) {
						parameterNode.setParameter(targetParameters.get(i));
					}
				}
			}
			
			return true;
			
		} else {
			System.err.println(
					"Cannot update " + targetElement.getClass().getSimpleName() + 
					" from " + sourceElement.getClass().getSimpleName() + ".");
			return false;
		}
	}

}

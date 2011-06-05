
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedNameImpl extends SyntaxElementImpl {
    
    private Boolean isAmbiguous = false;
    private String pathName = null; // DERIVED
    private Boolean isFeatureReference = null; // DERIVED
    private QualifiedName qualification = null; // DERIVED
    private FeatureReference disambiguation = null; // DERIVED
    private List<NameBinding> nameBinding = new ArrayList<NameBinding>();
    private Collection<ElementReference> referent = null; // DERIVED
    private NameBinding unqualifiedName = null; // DERIVED
    private QualifiedName templateName = null; // DERIVED

    private NamespaceDefinition currentScope = null;
    private Expression containingExpression = null;

    public QualifiedNameImpl(QualifiedName self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.QualifiedName getSelf() {
		return (QualifiedName) this.self;
	}
	
    public Boolean getIsAmbiguous() {
        return this.isAmbiguous;
    }

    public void setIsAmbiguous(Boolean isAmbiguous) {
        this.isAmbiguous = isAmbiguous;
    }

    public String getPathName() {
        if (this.pathName == null) {
            this.setPathName(this.derivePathName());
        }
        return this.pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public Boolean getIsFeatureReference() {
        if (this.isFeatureReference == null) {
            this.setIsFeatureReference(this.deriveIsFeatureReference());
        }
        return this.isFeatureReference;
    }

    public void setIsFeatureReference(Boolean isFeatureReference) {
        this.isFeatureReference = isFeatureReference;
    }

    public QualifiedName getQualification() {
        if (this.qualification == null) {
            this.setQualification(this.deriveQualification());
        }
        return this.qualification;
    }

    public void setQualification(QualifiedName qualification) {
        this.qualification = qualification;
    }

    public FeatureReference getDisambiguation() {
        if (this.disambiguation == null) {
            this.setDisambiguation(this.deriveDisambiguation());
        }
        return this.disambiguation;
    }

    public void setDisambiguation(FeatureReference disambiguation) {
        this.disambiguation = disambiguation;
    }

    public List<NameBinding> getNameBinding() {
        return this.nameBinding;
    }

    public void setNameBinding(List<NameBinding> nameBinding) {
        this.nameBinding = nameBinding;
    }

    public void addNameBinding(NameBinding nameBinding) {
        this.nameBinding.add(nameBinding);
    }

    public Collection<ElementReference> getReferent() {
        if (this.referent == null) {
            this.setReferent(this.deriveReferent());
        }
        return this.referent;
    }

    public void setReferent(Collection<ElementReference> referent) {
        this.referent = referent;
    }

    public void addReferent(ElementReference referent) {
        this.referent.add(referent);
    }

    public NameBinding getUnqualifiedName() {
        if (this.unqualifiedName == null) {
            this.setUnqualifiedName(this.deriveUnqualifiedName());
        }
        return this.unqualifiedName;
    }

    public void setUnqualifiedName(NameBinding unqualifiedName) {
        this.unqualifiedName = unqualifiedName;
    }

    public QualifiedName getTemplateName() {
        if (this.templateName == null) {
            this.setTemplateName(this.deriveTemplateName());
        }
        return this.templateName;
    }

    public void setTemplateName(QualifiedName templateName) {
        this.templateName = templateName;
    }

	public NamespaceDefinition getCurrentScope() {
	    return this.currentScope;
	}
	
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    this.currentScope = currentScope;
	}

	public Expression getContainingExpression() {
        return containingExpression;
    }

    public void setContainingExpression(Expression containingExpression) {
        this.containingExpression = containingExpression;
    }

    /**
     * The path name for a qualified name consists of the string representation
     * of each of the name bindings, separated by "::" punctuation. The string
     * representation of a name binding is its name followed by the
     * representation of its template binding, if it has one. The string
     * representation of a positional template binding consists of an ordered
     * list of the path names of its argument qualified names separated by
     * commas, all surrounded by the angle brackets "<" and ">". The string
     * representation of a named template binding consists of an ordered list of
     * its template parameter substitutions, each consisting of the formal
     * parameter name followed by "=>" followed by the path name of the argument
     * qualified name, separated by commas, all surrounded by the angle brackets
     * "<" and ">".
     **/
	protected String derivePathName() {
	    QualifiedName self = this.getSelf();
	    StringBuffer pathName = new StringBuffer();
	    String separator = "";
	    for(NameBinding nameBinding: self.getNameBinding()){
	        pathName.append(separator);
	        pathName.append(nameBinding.getImpl());
	        separator = "::";
	    }
		return pathName.toString();
	}

    /**
     * A qualified name is a feature reference if its disambiguation is not
     * empty.
     **/
	protected Boolean deriveIsFeatureReference() {
		return this.getSelf().getDisambiguation() != null;
	}

    /**
     * The qualification of a qualified name is empty if the qualified name
     * has only one name binding. Otherwise it is the qualified name consisting
     * of all the name bindings of the original qualified name except for the
     * last one. The qualification of a qualified name is considered ambiguous
     * if the qualified name is ambiguous and has more than two name bindings.
     **/
	protected QualifiedName deriveQualification() {
	    QualifiedName qualification = null;
	    QualifiedName self = this.getSelf();
	    List<NameBinding> bindings = self.getNameBinding();
	    int n = bindings.size()-1;
	    if (n > 0) {
	        qualification = new QualifiedName();
	        for (int i=0; i<n; i++) {
	            qualification.addNameBinding(bindings.get(i));
	        }
	        qualification.setIsAmbiguous(n > 1 && self.getIsAmbiguous());
	        qualification.getImpl().setContainingExpression(this.getContainingExpression());
	        qualification.getImpl().setCurrentScope(this.getCurrentScope());
	    }
		return qualification;
	}

    /**
     * If a qualified name is not ambiguous or it has a qualification that 
     * resolves to a namespace, then it is has no disambiguation. Otherwise, its 
     * disambiguation is a feature reference with a name given by the 
     * unqualified name of the qualified name and a target expression determined 
     * by the disambiguation of the qualification of the qualified name.
     **/
	protected FeatureReference deriveDisambiguation() {
	    FeatureReference disambiguation = null;
	    QualifiedName self = this.getSelf();
        QualifiedName qualification = self.getQualification();
	    if (self.getIsAmbiguous() && 
	            (qualification == null || !qualification.getImpl().isNamespaceReferent())) {
	        disambiguation = new FeatureReference();
	        disambiguation.setNameBinding(self.getUnqualifiedName());
	        FeatureReference featureReference = qualification.getDisambiguation();
	        NamespaceDefinition currentScope = this.getCurrentScope();
            Expression containingExpression = this.getContainingExpression();
            Map<String, AssignedSource> assignments = 
                containingExpression == null? new HashMap<String, AssignedSource>(): 
                    containingExpression.getImpl().getAssignmentBeforeMap();
	        if (featureReference==null) {
	            NameExpression nameExpression = new NameExpression();
	            nameExpression.setName(qualification);
	            nameExpression.getImpl().setCurrentScope(currentScope);
	            nameExpression.getImpl().setAssignmentBefore(assignments);
	            disambiguation.setExpression(nameExpression);
	        } else {
	            PropertyAccessExpression featureExpression = new PropertyAccessExpression();
	            featureExpression.setFeatureReference(featureReference);
	            featureExpression.getImpl().setCurrentScope(currentScope);
	            featureExpression.getImpl().setAssignmentBefore(assignments);
	            disambiguation.setExpression(featureExpression);
	        }
	    }
		return disambiguation;
	}
	
    /**
     * The referents of a qualified name are the elements to which the name may
     * resolve in the current scope, according to the UML rules for namespaces
     * and named elements.
     **/
	protected Collection<ElementReference> deriveReferent() {
	    ArrayList<ElementReference> referents = new ArrayList<ElementReference>();    
	    QualifiedName self = this.getSelf();
	    
	    if (!self.getIsFeatureReference()) {
	        QualifiedName templateName = self.getTemplateName();
	        if (templateName != null) {
	            // Resolve as a bound element
	            ElementReference boundElement = this.getBoundElement();
	            if (boundElement != null) {
	                referents.add(boundElement);
	            }
	        } else {
        	    NamespaceDefinition currentScope = this.getCurrentScope();
        	    int n = self.getNameBinding().size();
        	    
        	    if (n == 1) {
        	        SyntaxElement source = this.getLocalSource();
        	        if (source != null) {
        	            // Resolve as a local name
        	            InternalElementReference sourceReference = new InternalElementReference();
        	            sourceReference.setElement(source);
        	            referents.add(sourceReference);
        	        } else if (currentScope != null) {
        	            // Resolve as an unqualified name
        	            this.addReferentsTo(referents, currentScope.getImpl().resolve(self.getUnqualifiedName().getName()));
        	        }
        	    } else if (n > 0) {
        	        // Resolve as a qualified name
        	        ElementReference namespaceReference = self.getQualification().getImpl().getNamespaceReferent();
        	        if (namespaceReference != null) {
        	            NamespaceDefinition namespace = namespaceReference.getImpl().asNamespace();
        	            if (namespace != null) {
        	                this.addReferentsTo(referents, 
                                namespace.getImpl().resolveVisible(self.getUnqualifiedName().getName(),
                                    this.getCurrentScope()));
        	            }
        	        }
        	    }
	        }
	    }
	    
	    return referents;
	}
	
    /**
     * The unqualified name of a qualified name is the last name binding.
     **/
	protected NameBinding deriveUnqualifiedName() {
	    List<NameBinding> bindings = this.getSelf().getNameBinding();
		return bindings.size() == 0? null: bindings.get(bindings.size()-1);
	}

    /**
     * If the last name binding in a qualified name has a template binding, then
     * the template name is a qualified name with the same template bindings as
     * the original qualified name, but with the template binding removed on the
     * last name binding.
     **/
	public QualifiedName deriveTemplateName() {
        QualifiedName self = this.getSelf();
        if (self.getUnqualifiedName().getBinding() == null) {
            return null;
        } else {
    	    QualifiedName templateName = new QualifiedName();
    	    List<NameBinding> bindings = self.getNameBinding();
    	    int n = bindings.size();
    	    for (int i=0; i<n; i++) {
    	        NameBinding binding = bindings.get(i);
    	        if (i == n-1) {
    	            NameBinding last = new NameBinding();
    	            last.setName(binding.getName());
    	            binding = last;
    	        }
    	        templateName.addNameBinding(binding);
    	    }
    	    return templateName;
        }
	}
	
	/*
	 *  Derivations
	 */

	public boolean qualifiedNameUnqualifiedNameDerivation() {
		this.getSelf().getUnqualifiedName();
		return true;
	}

	public boolean qualifiedNamePathNameDerivation() {
		this.getSelf().getPathName();
		return true;
	}

	public boolean qualifiedNameIsFeatureReferenceDerivation() {
		this.getSelf().getIsFeatureReference();
		return true;
	}

	public boolean qualifiedNameQualificationDerivation() {
		this.getSelf().getQualification();
		return true;
	}

	public boolean qualifiedNameDisambiguationDerivation() {
		this.getSelf().getDisambiguation();
		return true;
	}

	public boolean qualifiedNameReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}
	
    public boolean qualifiedNameTemplateNameDerivation() {
        this.getSelf().getTemplateName();
        return true;
    }

	/*
	 *  Constraints
	 */

	/**
	 * If a qualified name is a local name, then the reference must be within
	 * the same local scope as the definition of the named element.
	 **/
	public boolean qualifiedNameLocalName() {
	    // NOTE: This is handled by deriveReferent.
		return true;
	}

	/**
	 * If a qualified name is an unqualified, non-local name, then it must be
	 * visible in the current scope of the use of the name.
	 **/
	public boolean qualifiedNameNonLocalUnqualifiedName() {
        // NOTE: This is handled by deriveReferent.
        return true;
	}

	/**
	 * If a qualified name has a qualification, then its unqualified name must
	 * name an element of the namespace named by the qualification, where the
	 * first name in the qualification must name an element of the current
	 * scope.
	 **/
	public boolean qualifiedNameQualifiedResolution() {
        QualifiedName self = this.getSelf();
		return self.getQualification() == null || self.getIsFeatureReference() ||
		            self.getReferent().size() > 0;
	}

	/**
	 * If the unqualified name of a qualified name has a template binding, then
	 * the template name must resolve to a template. The template binding must
	 * have an argument name for each of the template parameters and each
	 * argument name must resolve to a classifier. If the template parameter has
	 * constraining classifiers, then the referent of the corresponding argument
	 * name must conform to all those constraining classifiers.
	 **/
	public boolean qualifiedNameTemplateBinding() {
	    QualifiedName self = this.getSelf();
	    QualifiedName templateName = self.getTemplateName();
	    if (templateName == null) {
	        return true;
	    } else {
	        ElementReference templateReferent = templateName.getImpl().getTemplateReferent();
	        if (templateReferent == null || !templateReferent.getImpl().isTemplate()) {
	            return false;
	        } else {
	            List<ElementReference> templateParameters = 
	                templateReferent.getImpl().getTemplateParameters();
	            TemplateBinding templateBinding = self.getUnqualifiedName().getBinding();
	            
                // Note: getArgumentReferents only returns classifier referents
	            List<ElementReference> templateArguments = 
	                templateBinding.getImpl().getArgumentReferents(templateParameters);
	            
	            if (templateArguments == null || 
	                    templateArguments.size() != templateParameters.size()) {
	                return false;
	            } else {
	                for (int i = 0; i < templateParameters.size(); i++) {
	                    ElementReference templateParameter = 
	                        templateParameters.get(i);
	                    ElementReference templateArgument = templateArguments.get(i);
	                    if (!templateParameter.getImpl().isClassifierTemplateParameter()) {
	                        return false;
	                    } else {
	                        for (ElementReference constrainingClassifier:
	                            templateParameter.getImpl().getConstrainingClassifiers()) {
	                            if (!templateArgument.getImpl().conformsTo(constrainingClassifier)) {
	                                return false;
	                            }
	                        }
	                    }
	                }
	                return true;
	            }
	        }
	        
	    }
	}
	
	/* 
	 * Helper Methods
	 */

    private SyntaxElement getLocalSource() {
        Expression containingExpression = this.getContainingExpression();
        SyntaxElement source = null;
        if (containingExpression != null) {
            source = containingExpression.getImpl().resolve(getSelf().getUnqualifiedName().getName());
        }
        return source;
    }
    
    public ElementReference getNonTemplateClassifierReferent() {
        ElementReference classifier = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isClassifier() && 
                    !referent.getImpl().isTemplate()) {
                if (classifier != null) {
                    return null;
                }
                classifier = referent;
            }
        }
        return classifier;
    }

    public ElementReference getNamespaceReferent() {
        ElementReference namespace = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isNamespace()) {
                if (namespace != null) {
                    return null;
                }
                namespace = referent;
            }
        }
        return namespace;
    }

    public ElementReference getOperationReferent() {
        ElementReference operation = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isOperation()) {
                if (operation != null) {
                    return null;
                }
                operation = referent;
            }
        }
        return operation;
    }

    public ElementReference getClassifierReferent() {
        ElementReference classifier = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isClassifier()) {
                if (classifier != null) {
                    return null;
                }
                classifier = referent;
            }
        }
        return classifier;
    }

    public ElementReference getClassReferent() {
        ElementReference class_ = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isClass()) {
                if (class_ != null) {
                    return null;
                }
                class_ = referent;
            }
        }
        return class_;
    }

    public ElementReference getSignalReferent() {
        ElementReference signal = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isSignal()) {
                if (signal != null) {
                    return null;
                }
                signal = referent;
            }
        }
        return signal;
    }

    public ElementReference getDataTypeReferent() {
        ElementReference dataType = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isDataType()) {
                if (dataType != null) {
                    return null;
                }
                dataType = referent;
            }
        }
        return dataType;
    }

    public ElementReference getStereotypeReferent() {
        ElementReference stereotype = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isStereotype()) {
                if (stereotype != null) {
                    return null;
                }
                stereotype = referent;
            }
        }
        return stereotype;
    }

    public ElementReference getAssociationReferent() {
        ElementReference association = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isAssociation()) {
                if (association != null) {
                    return null;
                }
                association = referent;
            }
        }
        return association;
    }

    public ElementReference getBehaviorReferent() {
        ElementReference behavior = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isBehavior()) {
                if (behavior != null) {
                    return null;
                }
                behavior = referent;
            }
        }
        return behavior;
    }

    public ElementReference getPropertyReferent() {
        ElementReference property = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isProperty()) {
                if (property != null) {
                    return null;
                }
                property = referent;
            }
        }
        return property;
    }

    public ElementReference getEnumerationLiteralReferent() {
        ElementReference literal = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isEnumerationLiteral()) {
                if (literal != null) {
                    return null;
                }
                literal = referent;
            }
        }
        return literal;
    }

    public ElementReference getParameterReferent() {
        ElementReference parameter = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isParameter()) {
                if (parameter != null) {
                    return null;
                }
                parameter = referent;
            }
        }
        return parameter;
    }

    public ElementReference getTemplateReferent() {
        ElementReference template = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isTemplate()) {
                if (template != null) {
                    return null;
                }
                template = referent;
            }
        }
        return template;
    }

    public boolean isNamespaceReferent() {
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isNamespace()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isProfileReferent() {
        for (ElementReference referent: this.getSelf().getReferent()) {
            if (referent.getImpl().isProfile()) {
                return true;
            }
        }
        return false;
    }
    
    private void addReferentsTo(List<ElementReference> referents, 
            Collection<Member> members) {
        for (Member member: members) {
            if (!(member instanceof MissingMember)) {
                referents.add(member.getImpl().getReferent());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof String) {
            return this.getSelf().getPathName().equals(other);
        } else if (other instanceof QualifiedName) {
            return this.equals(((QualifiedName)other).getImpl());
        } else {
            List<NameBinding> myNameBindings = this.getSelf().getNameBinding();
            List<NameBinding> otherNameBindings = ((QualifiedNameImpl)other).getSelf().getNameBinding();
            if (myNameBindings.size() != otherNameBindings.size()) {
                return false;
            } else {
                for (int i=0; i<myNameBindings.size(); i++) {
                    if (!processNameBinding(myNameBindings.get(i)).
                            equals(processNameBinding(otherNameBindings.get(i)))) {
                        return false;
                    }
                }
                return true;
            }
        }
    }
    
    public static String processNameBinding(NameBinding nameBinding) {
        // TODO: Handle template bindings.
      return nameBinding.getName();
    }
    
    public QualifiedName addName(String name) {
        NameBinding nameBinding = new NameBinding();
        nameBinding.setName(name);
        QualifiedName self = this.getSelf();
        self.addNameBinding(nameBinding);
        return self;
    }

    public QualifiedNameImpl copy() {
        QualifiedName qualifiedName = new QualifiedName();
        for (NameBinding nameBinding: this.getSelf().getNameBinding()) {
            qualifiedName.addNameBinding(nameBinding);
        }
        QualifiedNameImpl qualifiedNameImpl = qualifiedName.getImpl();
        qualifiedNameImpl.setContainingExpression(this.getContainingExpression());
        qualifiedNameImpl.setCurrentScope(this.getCurrentScope());
        return qualifiedNameImpl;
    }
    
    public ElementReference getBoundElement() {
        QualifiedName self = this.getSelf();
        ElementReference templateReferent = 
            self.getTemplateName().getImpl().getClassifierReferent();
        // TODO: Handle bindings of non-classifier templates.
        if (templateReferent == null || !templateReferent.getImpl().isClassifier()) {
            return null;
        } else {
            List<ElementReference> templateParameters = 
                templateReferent.getImpl().getTemplateParameters();
            List<ElementReference> templateArguments = 
                self.getUnqualifiedName().getBinding().getImpl().getArgumentReferents
                    (templateParameters);
            return this.getBoundElement(templateReferent, templateParameters, templateArguments);
        }
    }
    
    public ElementReference getBoundElement(ElementReference templateReferent,
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        String name = this.makeBoundElementName(templateArguments);
        NamespaceDefinition templateNamespace = templateReferent.getImpl().
            getNamespace().getImpl().asNamespace();
        Collection<Member> members = templateNamespace.getImpl().resolve(name);
        if (!members.isEmpty()) {
            return ((Member)members.toArray()[0]).getImpl().getReferent();
        } else {
            Member boundElement = templateReferent.getImpl().asNamespace().
                getImpl().bind(name, templateNamespace, 
                        templateParameters, templateArguments);
            if (boundElement == null) {
                return null;
            } else {
                templateNamespace.addOwnedMember(boundElement);
                templateNamespace.addMember(boundElement);
                return boundElement.getImpl().getReferent();
            }
        }
    }
    
    private String makeBoundElementName(List<ElementReference> templateArguments) {
        QualifiedName self = this.getSelf();
        StringBuffer name = new StringBuffer("$$");
        name.append(self.getUnqualifiedName().getName());
        name.append("__");
        for (ElementReference argument: templateArguments) {
            String argumentName = argument == null? "any":
                argument.getImpl().asNamespace().getImpl().getQualifiedName().getPathName();
            name.append(argumentName.replace("::", "$"));
            name.append("_");
        }
        name.append("_");
        return name.toString();
    }

    public QualifiedName updateBindings(
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        QualifiedName qualifiedName = new QualifiedName();
        for (NameBinding nameBinding: this.getSelf().getNameBinding()) {
            qualifiedName.addNameBinding(nameBinding.getImpl().
                    updateBinding(templateParameters, templateArguments));
        }
        qualifiedName.getImpl().setContainingExpression(this.getContainingExpression());
        qualifiedName.getImpl().setCurrentScope(this.getCurrentScope());
        return qualifiedName;
    }
    
    public QualifiedName updateForBinding(
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        QualifiedName qualifiedName = null;
        // TODO: Allow template arguments other classifiers.
        ElementReference referent = this.getClassifierReferent();
        if (referent != null) {
            for (int i = 0; i < templateParameters.size(); i++) {
                if (referent.getImpl().equals(templateParameters.get(i))) {
                    Collection<ElementReference> referents = new ArrayList<ElementReference>(1);
                    ElementReference templateArgument = templateArguments.get(i);
                    qualifiedName = new QualifiedName();
                    if (templateArgument != null) {
                        qualifiedName.setNameBinding(templateArgument.getImpl().
                                asNamespace().getImpl().getQualifiedName().getNameBinding());
                        referents.add(templateArguments.get(i));
                    }
                    qualifiedName.setReferent(referents);
                    break;
                }
            }
        }
        if (qualifiedName == null) {
            qualifiedName = this.updateBindings(templateParameters, templateArguments);
        }
        return qualifiedName;
    }
    
} // QualifiedNameImpl

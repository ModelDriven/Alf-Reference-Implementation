
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.NamedElement;
import org.omg.uml.Namespace;
import org.omg.uml.Package_;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedNameImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {
    
    private NamespaceDefinition currentScope = null;
    private Expression containingExpression = null;

    public QualifiedNameImpl(QualifiedName self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.QualifiedName getSelf() {
		return (QualifiedName) this.self;
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
	public String derivePathName() {
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
	public Boolean deriveIsFeatureReference() {
		return this.getSelf().getDisambiguation() != null;
	}

    /**
     * The qualification of a qualified name is empty if the qualified name
     * has only one name binding. Otherwise it is the qualified name consisting
     * of all the name bindings of the original qualified name except for the
     * last one. The qualification of a qualified name is considered ambiguous
     * if the qualified name is ambiguous and has more than two name bindings.
     **/
	public QualifiedName deriveQualification() {
	    QualifiedName qualification = null;
	    QualifiedName self = this.getSelf();
	    ArrayList<NameBinding> bindings = self.getNameBinding();
	    int n = bindings.size()-1;
	    if (n > 0) {
	        qualification = new QualifiedName();
	        for (int i=0; i<n; i++) {
	            qualification.addNameBinding(bindings.get(i));
	        }
	        qualification.getImpl().setContainingExpression(this.getContainingExpression());
	        qualification.getImpl().setCurrentScope(this.getCurrentScope());
	    }
		return qualification;
	}

    /**
     * If a qualified name is not ambiguous or it resolves to a namespace, then
     * it is has no disambiguation. Otherwise, its disambiguation is a feature
     * reference with a name given by the unqualified name of the qualified name
     * and a target expression determined by the disambiguation of the
     * qualification of the qualified name.
     **/
	public FeatureReference deriveDisambiguation() {
	    FeatureReference disambiguation = null;
	    QualifiedName self = this.getSelf();
	    if (self.getIsAmbiguous() && !this.isPackageReferent()) {
	        disambiguation = new FeatureReference();
	        disambiguation.setNameBinding(self.getUnqualifiedName());
	        QualifiedName qualification = self.getQualification();
	        FeatureReference featureReference = qualification.getDisambiguation();
	        if (featureReference==null) {
	            NameExpression nameExpression = new NameExpression();
	            nameExpression.setName(qualification);
	            disambiguation.setExpression(nameExpression);
	        } else {
	            PropertyAccessExpression featureExpression = new PropertyAccessExpression();
	            featureExpression.setFeatureReference(featureReference);
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
	public ArrayList<ElementReference> deriveReferent() {
	    ArrayList<ElementReference> referents = new ArrayList<ElementReference>();
	    
	    QualifiedName self = this.getSelf();
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
	            NamespaceDefinition namespace = (NamespaceDefinition)namespaceReference.getImpl().getAlf();
	            if (namespace != null) {
	                this.addReferentsTo(referents, 
                        namespace.getImpl().resolveVisible(self.getUnqualifiedName().getName(),
                            this.getCurrentScope()));
	            } else {
	                this.addReferentsTo(referents, 
                        this.resolveExternal((Namespace)namespaceReference.getImpl().getUml(), 
	                        self.getUnqualifiedName().getName()));
	            }
	        }
	    }
	    
	    return referents;
	}
	
    /**
     * The unqualified name of a qualified name is the last name binding.
     **/
	public NameBinding deriveUnqualifiedName() {
	    ArrayList<NameBinding> bindings = this.getSelf().getNameBinding();
		return bindings.get(bindings.size()-1);
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
    	    ArrayList<NameBinding> bindings = self.getNameBinding();
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
		return this.getCurrentScope() != null || this.getLocalSource() != null;
	}

	/**
	 * If a qualified name is an unqualified, non-local name, then it must be
	 * visible in the current scope of the use of the name.
	 **/
	public boolean qualifiedNameNonLocalUnqualifiedName() {
		return this.getLocalSource() != null || 
		    this.getSelf().getReferent().size() > 0;
	}

	/**
	 * If a qualified name has a qualification, then its unqualified name must
	 * name an element of the namespace named by the qualification, where the
	 * first name in the qualification must name an element of the current
	 * scope.
	 **/
	public boolean qualifiedNameQualifiedResolution() {
		return this.getSelf().getQualification() == null || 
		    this.getSelf().getReferent().size() > 0;
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
	    // TODO: Support qualified name template binding.
		return this.getSelf().getTemplateName() == null;
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
    
    public ElementReference getNamespaceReferent() {
        ElementReference namespace = null;
        for (ElementReference referent: this.getSelf().getReferent()) {
            SyntaxElement element = referent.getImpl().getAlf();
            if ((element != null && element instanceof NamespaceDefinition) ||
                    referent.getImpl().getUml() instanceof Namespace) {
                if (namespace != null) {
                    return null;
                }
                namespace = referent;
            }
        }
        return namespace;
    }

    private boolean isPackageReferent() {
        for (ElementReference referent: this.getSelf().getReferent()) {
            if ((referent.getImpl().getAlf()!=null && 
                    referent.getImpl().getAlf() instanceof PackageDefinition) ||
                    (referent.getImpl().getUml() instanceof Package_)) {
                return true;
            }
        }
        return false;
    }
    
    private void addReferentsTo(List<ElementReference> referents, 
            List<Member> members) {
        for (Member member: members) {
            referents.add(member.getImpl().getReferent());
        }
    }

    private void addReferentsTo(ArrayList<ElementReference> referents,
            List<NamedElement> elements) {
        for (NamedElement element: elements) {
            ExternalElementReference referent = new ExternalElementReference();
            referent.setElement(element);
            referents.add(referent);
        }
    }

    private List<NamedElement> resolveExternal(Namespace namespace, String name) {
        List<NamedElement> elements = new ArrayList<NamedElement>();
        // TODO: Handle name resolution for external namespaces.
        return elements;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof QualifiedNameImpl)) {
            return false;
        } else {
            ArrayList<NameBinding> myNameBindings = this.getSelf().getNameBinding();
            ArrayList<NameBinding> otherNameBindings = ((QualifiedNameImpl)other).getSelf().getNameBinding();
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
      return processName(nameBinding.getName());
    }
    
    public static String processName(String name) {
        if (name != null && name.length() > 0 && name.charAt(0) == '\'') {
            return replaceEscapes(name.substring(1,name.length()-1));
        } else {
            return name;
        }
    }

    private static String replaceEscapes(String original) {
        String s = new String(original);

        int i = s.indexOf("\\");

        while (i > -1 && i < s.length()-1) {

          char escape = s.charAt(i+1);
          String replacement;

          if (escape == 'b') {
            replacement = "\b";
          } else if (escape == 'f') {
            replacement = "\f";
          } else if (escape == 'n') {
            replacement = "\n";
          } else if (escape == 't') {
            replacement = "\t";
          } else {
            replacement = Character.toString(escape);
          }

          s = s.substring(0, i) + replacement + s.substring(i+1,s.length());
          i = s.indexOf("\\", i+1);

        }

        return s;
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

} // QualifiedNameImpl

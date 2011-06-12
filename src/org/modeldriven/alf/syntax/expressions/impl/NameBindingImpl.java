
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An unqualified name, optionally with a template binding.
 **/

public class NameBindingImpl extends SyntaxElementImpl {

    private TemplateBinding binding = null;
    private String name = "";

	public NameBindingImpl(NameBinding self) {
		super(self);
	}

	@Override
	public NameBinding getSelf() {
		return (NameBinding) this.self;
	}
	
	@Override
	public String toString() {
	    NameBinding self = this.getSelf();
	    StringBuffer s = new StringBuffer(self.getName());
	    TemplateBinding b = self.getBinding();
	    if (b!=null) {
	        s.append(b.getImpl());
	    }
	    return s.toString();
	}
	
    public TemplateBinding getBinding() {
        return this.binding;
    }

    public void setBinding(TemplateBinding binding) {
        this.binding = binding;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = processName(name);
    }
    
    /*
     * Helper Methods
     */

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

    public void setCurrentScope(NamespaceDefinition currentScope) {
        TemplateBinding binding = this.getSelf().getBinding();
        if (binding != null) {
            binding.getImpl().setCurrentScope(currentScope);
        }
    }

    public NameBinding updateBinding(
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        NameBinding self = this.getSelf();
        NameBinding nameBinding = new NameBinding();
        nameBinding.setName(self.getName());
        TemplateBinding binding = self.getBinding();
        if (binding != null) {
            nameBinding.setBinding(binding.getImpl().
                    update(templateParameters, templateArguments));
        }
        return nameBinding;
    }

} // NameBindingImpl

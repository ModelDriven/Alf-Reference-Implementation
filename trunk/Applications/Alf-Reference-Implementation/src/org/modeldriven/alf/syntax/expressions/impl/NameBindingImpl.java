
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
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
	public String toString(boolean includesDerived) {
	    NameBinding self = this.getSelf();
	    TemplateBinding b = self.getBinding();
	    return self.getName() + (b == null? "": b.getImpl());
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

    public static String replaceEscapes(String original) {
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

          s = s.substring(0, i) + replacement + s.substring(i+2,s.length());
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
        return (NameBinding)this.bind(templateParameters, templateArguments);
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NameBinding) {
            NameBinding self = this.getSelf();
            NameBinding baseNameBinding = (NameBinding)base;
            TemplateBinding binding = baseNameBinding.getBinding();
            self.setName(baseNameBinding.getName());
            if (binding != null) {
                self.setBinding(binding.getImpl().
                        update(templateParameters, templateArguments));
            }
        }
    }

} // NameBindingImpl

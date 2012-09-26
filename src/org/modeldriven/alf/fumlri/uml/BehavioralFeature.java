/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class BehavioralFeature extends Feature implements
		org.modeldriven.alf.uml.BehavioralFeature {

	public BehavioralFeature(fUML.Syntax.Classes.Kernel.BehavioralFeature base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.BehavioralFeature getBase() {
		return (fUML.Syntax.Classes.Kernel.BehavioralFeature) this.base;
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (fUML.Syntax.Classes.Kernel.Parameter element : this.getBase().ownedParameter) {
			list.add(new Parameter(element));
		}
		return list;
	}

	public void addOwnedParameter(org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase()
				.addOwnedParameter(ownedParameter==null? null: ((Parameter) ownedParameter).getBase());
	}

	public boolean getIsAbstract() {
		return this.getBase().isAbstract;
	}

	public void setIsAbstract(boolean isAbstract) {
		this.getBase().setIsAbstract(isAbstract);
	}

	public List<org.modeldriven.alf.uml.Behavior> getMethod() {
		List<org.modeldriven.alf.uml.Behavior> list = new ArrayList<org.modeldriven.alf.uml.Behavior>();
		for (fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior element : this
				.getBase().method) {
			list.add((Behavior)this.wrap(element));
		}
		return list;
	}

	public void addMethod(org.modeldriven.alf.uml.Behavior method) {
		this.getBase().addMethod(method==null? null: ((Behavior) method).getBase());
	}

	public String getConcurrency() {
		return this.getBase().concurrency.toString();
	}

	public void setConcurrency(String concurrency) {
		this.getBase().concurrency =
				fUML.Syntax.CommonBehaviors.BasicBehaviors.CallConcurrencyKind
						.valueOf(concurrency);
	}

    @Override
    public boolean isDistinguishableFrom(org.modeldriven.alf.uml.NamedElement otherElement,
            org.modeldriven.alf.uml.Namespace namespace) {
        if (super.isDistinguishableFrom(otherElement,namespace)) {
            return true;
        } else {
            fUML.Syntax.Classes.Kernel.ParameterList myParameters = 
                this.getBase().ownedParameter;
            fUML.Syntax.Classes.Kernel.ParameterList otherParameters = 
                ((fUML.Syntax.Classes.Kernel.BehavioralFeature)
                        ((Element)otherElement).getBase()).ownedParameter;
            if (myParameters.size() != otherParameters.size()) {
                return true;
            } else {
                for (int i = 0; i < myParameters.size(); i++) {
                    if (myParameters.get(i).type != otherParameters.get(i).type) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}

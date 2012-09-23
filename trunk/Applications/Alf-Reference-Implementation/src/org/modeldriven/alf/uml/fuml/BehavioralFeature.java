package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Behavior;
import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.Feature;
import org.modeldriven.uml.fuml.Parameter;

public class BehavioralFeature extends Feature implements
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
				.addOwnedParameter(((Parameter) ownedParameter).getBase());
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
			list.add(new Behavior(element));
		}
		return list;
	}

	public void addMethod(org.modeldriven.alf.uml.Behavior method) {
		this.getBase().addMethod(((Behavior) method).getBase());
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

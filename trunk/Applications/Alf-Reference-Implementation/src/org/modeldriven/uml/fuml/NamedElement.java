package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.Namespace;

public class NamedElement extends Element implements
		org.modeldriven.alf.uml.NamedElement {

	public NamedElement(fUML.Syntax.Classes.Kernel.NamedElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.NamedElement getBase() {
		return (fUML.Syntax.Classes.Kernel.NamedElement) this.base;
	}

	public String getName() {
		return this.getBase().name;
	}

	public void setName(String name) {
		this.getBase().setName(name);
	}

	public String getVisibility() {
		return this.getBase().visibility.toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility));
	}

	public String getQualifiedName() {
		return this.getBase().qualifiedName;
	}

	public org.modeldriven.alf.uml.Namespace getNamespace() {
		return new Namespace(this.getBase().namespace);
	}

    @Override
    public boolean isDistinguishableFrom(org.modeldriven.alf.uml.NamedElement otherElement,
            org.modeldriven.alf.uml.Namespace namespace) {
        if (this.getClass().isAssignableFrom(otherElement.getClass()) || 
                otherElement.getClass().isAssignableFrom(this.getClass())) {
            List<String> names = namespace.getNamesOfMember(this);
            names.removeAll(namespace.getNamesOfMember(otherElement));
            return names.isEmpty();
        } else {
            return true;
        }
    }

}

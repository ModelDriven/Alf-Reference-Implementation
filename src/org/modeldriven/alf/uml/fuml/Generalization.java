package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Classifier;
import org.modeldriven.uml.fuml.Element;

public class Generalization extends Element implements
		org.modeldriven.alf.uml.Generalization {
	public Generalization() {
		this(new fUML.Syntax.Classes.Kernel.Generalization());
	}

	public Generalization(fUML.Syntax.Classes.Kernel.Generalization base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Generalization getBase() {
		return (fUML.Syntax.Classes.Kernel.Generalization) this.base;
	}

	public boolean getIsSubstitutable() {
		return this.getBase().isSubstitutable;
	}

	public void setIsSubstitutable(boolean isSubstitutable) {
		this.getBase().setIsSubstitutable(isSubstitutable);
	}

	public org.modeldriven.alf.uml.Classifier getSpecific() {
		return new Classifier(this.getBase().specific);
	}

	public org.modeldriven.alf.uml.Classifier getGeneral() {
		return new Classifier(this.getBase().general);
	}

	public void setGeneral(org.modeldriven.alf.uml.Classifier general) {
		this.getBase().setGeneral(((Classifier) general).getBase());
	}

}

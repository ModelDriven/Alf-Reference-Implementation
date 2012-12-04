package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class OpaqueBehavior extends Behavior implements
		org.modeldriven.alf.uml.OpaqueBehavior {
	public OpaqueBehavior() {
		this(UMLFactory.eINSTANCE.createOpaqueBehavior());
	}

	public OpaqueBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior base) {
		super(base);
	}

	public org.eclipse.uml2.uml.OpaqueBehavior getBase() {
		return (org.eclipse.uml2.uml.OpaqueBehavior) this.base;
	}

	public List<String> getBody() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().getBody()) {
			list.add(element);
		}
		return list;
	}

	public void addBody(String body) {
		this.getBase().addBody(body);
	}

	public List<String> getLanguage() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().getLanguage()) {
			list.add(element);
		}
		return list;
	}

	public void addLanguage(String language) {
		this.getBase().addLanguage(language);
	}

}

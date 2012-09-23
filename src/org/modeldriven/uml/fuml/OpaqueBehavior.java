package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Behavior;

public class OpaqueBehavior extends Behavior implements
		org.modeldriven.alf.uml.OpaqueBehavior {
	public OpaqueBehavior() {
		this(new fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior());
	}

	public OpaqueBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior) this.base;
	}

	public List<String> getBody() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().body) {
			list.add(element);
		}
		return list;
	}

	public void addBody(String body) {
		this.getBase().body.add(body);
	}

	public List<String> getLanguage() {
		List<String> list = new ArrayList<String>();
		for (String element : this.getBase().language) {
			list.add(element);
		}
		return list;
	}

	public void addLanguage(String language) {
		this.getBase().language.add(language);
	}

}

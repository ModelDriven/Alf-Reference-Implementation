
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.namespaces;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;

public abstract class NamespaceDefinitionMapping extends MemberMapping {

	public void mapTo(NamedElement namespace) {
		super.mapTo(namespace);

		outer: for (Member member : this.getNamespaceDefinition().getMembers()) {
			if (member.isError()) {
				this.setError(((ErrorMember) member).getError());
				break;
			} else {
				MemberMapping mapping = (MemberMapping) this.map(member);
				ArrayList<Element> elements = mapping.getModelElements();

				for (Element element : elements) {
					this.addMemberTo(element, namespace);
					if (this.isError()) {
						break outer;
					}
				}
			}
		}
	} // mapTo

	public abstract void addMemberTo(Element element, NamedElement namespace);

	public NamespaceDefinition getNamespaceDefinition() {
		return (NamespaceDefinition) this.getSource();
	} // getNamespaceDefinition

} // NamespaceDefinitionMapping


/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.namespaces;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;

public abstract class NamespaceDefinitionMapping extends MemberMapping {

	public void mapTo(NamedElement namespace) {
		super.mapTo(namespace);

		NamespaceDefinition definition = this.getNamespaceDefinition();
		Member completion = definition.completeStub();

		if (completion != null && completion.isError()) {
			this.setError(((ErrorMember) completion).getError());
		} else {
			ArrayList<Member> ownedMembers = definition.getOwnedMembers();

			// Note: For the root and model namespaces, owned members
			// may be added during the course of the following loop.
			for (int i = 0; i < ownedMembers.size(); i++) {
				Member member = ownedMembers.get(i);
				if (member.isError()) {
					this.setError(((ErrorMember) member).getError());
					return;
				} else {
					MemberMapping mapping = (MemberMapping) this.map(member);
					ArrayList<Element> elements = mapping.getModelElements();

					if (mapping.isError()) {
						this.setError(mapping.getError());
						return;
					}

					for (Element element : elements) {
						this.addMemberTo(element, namespace);
						if (this.isError()) {
							return;
						}
					}
				}
			}
		}
	} // mapTo

	public abstract void addMemberTo(Element element, NamedElement namespace);

	public NamespaceDefinition getNamespaceDefinition() {
		return (NamespaceDefinition) this.getSourceNode();
	} // getNamespaceDefinition

} // NamespaceDefinitionMapping

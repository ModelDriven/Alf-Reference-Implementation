/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.interactive.execution;

import java.util.List;
import java.util.stream.Collectors;

import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

public class AlfInteractiveUtil {

	protected static List<Member> filterMembers(NamespaceDefinition namespace, boolean isMapped) {
		return namespace.getOwnedMember().stream().
				filter(member->(member.getImpl().getMapping() != null) == isMapped).
				collect(Collectors.toList());
	}

	protected static List<Member> getMappedMembers(NamespaceDefinition namespace) {
		return filterMembers(namespace, true);
	}

	protected static List<Member> getUnmappedMembers(NamespaceDefinition namespace) {
		return filterMembers(namespace, false);
	}

}

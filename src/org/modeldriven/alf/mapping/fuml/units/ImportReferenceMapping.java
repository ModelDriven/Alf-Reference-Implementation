
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.units.ImportReference;

public abstract class ImportReferenceMapping extends SyntaxElementMapping {

	public ImportReference getImportReference() {
		return (ImportReference) this.getSource();
	}

} // ImportReferenceMapping

package org.modeldriven.alf.interactive.units;

import org.modeldriven.alf.syntax.units.ModelNamespace;

public class ModelNamespaceImpl extends org.modeldriven.alf.fuml.units.ModelNamespaceImpl {

	public ModelNamespaceImpl(ModelNamespace self) {
		super(self);
	}
	
	public void clearParsedUnitCache() {
		this.parsedUnitCache.clear();
	}

}

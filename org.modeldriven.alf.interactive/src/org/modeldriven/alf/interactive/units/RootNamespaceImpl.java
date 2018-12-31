package org.modeldriven.alf.interactive.units;

import org.modeldriven.alf.interactive.units.ModelNamespaceImpl;
import org.modeldriven.alf.syntax.units.ModelNamespace;

public class RootNamespaceImpl extends org.modeldriven.alf.fuml.units.RootNamespaceImpl {

	protected ModelNamespaceImpl createModelNamespaceImpl(ModelNamespace modelNamespace) {
        return new ModelNamespaceImpl(modelNamespace);
    }
	
}

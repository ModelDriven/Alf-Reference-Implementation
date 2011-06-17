package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.expressions.QualifiedName;

public class MissingUnit extends UnitDefinition {
    
    private String pathName;
    
    public MissingUnit(QualifiedName qualifiedName) {
        super();
        this.pathName = qualifiedName.getPathName();
        this.getImpl().setHasImplicitImports(true);
    }
    
    public String toString(boolean includeDerived) {
        return super.toString(includeDerived) + " pathName:" + pathName;
    }

}

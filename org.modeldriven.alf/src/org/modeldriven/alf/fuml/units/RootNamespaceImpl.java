/*******************************************************************************
 * Copyright 2013-2019 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.fuml.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.modeldriven.alf.parser.ParserFactory;
import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingMember;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class RootNamespaceImpl extends ModelNamespaceImpl {
    
    public static final String ROOT_NAMESPACE_NAME = "Root";
    public static final String MODEL_NAMESPACE_NAME = "Model";
    public static final String DEFAULT_LIBRARY_DIRECTORY = "Libraries";
    public static final String ALF_LIBRARY_SUBDIRECTORY = "alf";
    
    private ModelNamespace modelNamespace = null;
    private String libraryDirectory = null;
    
    private ParserFactory parserFactory;
    private Collection<SourceProblem> parsingErrors = new TreeSet<>();
    
    protected RootNamespaceImpl(RootNamespace self) {
        super(self);
        RootNamespace.setRootImpl(this);
    }
    
    public RootNamespaceImpl(ParserFactory parserFactory) {
        this(RootNamespace.getRootScope());
        this.setName(ROOT_NAMESPACE_NAME);
        this.setParserFactory(parserFactory);
        this.resetModelNamespace();
    }
    
    public RootNamespaceImpl() {
        this(ParserFactory.defaultImplementation());
    }
    
    public void setParserFactory(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }
    
    @Override
    public RootNamespace getSelf() {
        return (RootNamespace)this.self;
    }
    
    protected ModelNamespaceImpl createModelNamespaceImpl(ModelNamespace modelNamespace) {
        return new ModelNamespaceImpl(modelNamespace, this.parserFactory);
    }
    
    public void resetModelNamespace() {
        ModelNamespace modelNamespace = new ModelNamespace();
        modelNamespace.setImpl(this.createModelNamespaceImpl(modelNamespace));
        modelNamespace.setName(MODEL_NAMESPACE_NAME);               
        this.setModelNamespace(modelNamespace);
        this.setMapping(null);
    }
    
    protected void setModelNamespace(ModelNamespace modelNamespace) {
        RootNamespace self = this.getSelf();
        
        // NOTE: The following insures that there is always a non-null member list.
        Collection<Member> members = self.getMember();
        
        if (this.modelNamespace != null) {
            self.getOwnedMember().remove(this.modelNamespace);
            members.remove(this.modelNamespace);
        }        
        this.modelNamespace = modelNamespace;
        self.addOwnedMember(modelNamespace);
        self.addMember(modelNamespace);
        modelNamespace.setNamespace(self);
    }
    
    public void clearParsingErrors() {
        this.parsingErrors.clear();
    }
    
    public void addParsingErrors(Collection<SourceProblem> problems) {
        this.parsingErrors.addAll(problems);
    }
    
    public Collection<SourceProblem> getParsingErrors() {
        return this.parsingErrors;
    }

    public ModelNamespace getModelNamespace() {
        return this.modelNamespace;
    }
    
    public ModelNamespaceImpl getModelNamespaceImpl() {
        return (ModelNamespaceImpl)this.getModelNamespace().getImpl();
    }
    
    @Override
    public void setModelDirectory(String modelDirectory) {
        this.getModelNamespaceImpl().setModelDirectory(modelDirectory);
    }
    
    @Override
    public String getModelDirectory() {
        return this.getModelNamespaceImpl().getModelDirectory();
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        this.libraryDirectory = libraryDirectory;
        super.setModelDirectory(libraryDirectory + "/" + ALF_LIBRARY_SUBDIRECTORY);
    }
    
    public String getLibraryDirectory() {
        if (this.libraryDirectory == null) {
            this.setLibraryDirectory(DEFAULT_LIBRARY_DIRECTORY);
        }
        return this.libraryDirectory;
    }
    
    @Override
    public void setIsVerbose(boolean isVerbose) {
        this.getModelNamespaceImpl().setIsVerbose(isVerbose);
    }
    
    @Override
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        return this.getModelNamespace().getModelNamespace(unit);
    }
    
    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
        ModelNamespaceImpl modelScopeImpl = this.getModelNamespaceImpl();
        Collection<Member> members = modelScopeImpl.resolve(name, classifierOnly);
        if (members.isEmpty()) {
            members = super.resolveInScope(name, classifierOnly);
            if (members.isEmpty()) {
                QualifiedName qualifiedName = new QualifiedName().getImpl().addName(name);
                Member member;
                UnitDefinition unit = this.resolveModelUnit(qualifiedName);
                if (unit == null) {
                    member = new MissingMember(name);
                } else {
                    member = unit.getDefinition();
                    if (member == null) {
                        member = new MissingMember(name);
                    } else {
                        members.add(member);
                    }
                }
                NamespaceDefinition self = this.getSelf();
                self.addOwnedMember(member);
                self.addMember(member);
                member.setNamespace(self);
            } else if (members.toArray()[0] instanceof MissingMember) {
                members = new ArrayList<Member>();
            }
        }
        return members;
    }
    
    @Override
    public UnitDefinition resolveModelUnit(QualifiedName qualifiedName) {
        UnitDefinition unit = super.resolveModelUnit(qualifiedName);
        if (unit instanceof MissingUnit) {
            System.out.println("Unit not found: " + qualifiedName.getPathName());
        }
        return unit;
    }
    
    @Override
    public Collection<Member> resolveAsOuterScope(String name, boolean classifierOnly) {
        return new ArrayList<Member>();
    }
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return this.getModelNamespace().resolveUnit(qualifiedName);
    }

    protected RootNamespaceImpl getRootNamespaceImpl() {
        return this;
    }
}

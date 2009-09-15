package alf.execution;

import alf.mapping.AlfMapper;
import alf.mapping.behavioral.ActivityDefinitionMapping;
import alf.mapping.structural.ClassDefinitionMapping;
import alf.mapping.structural.PrimitiveTypeMapping;
import alf.parser.AlfParser;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;
import alf.syntax.behavioral.*;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Classes.Kernel.Class_;

import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;

import java.util.ArrayList;

public class Alf {

    private FumlEnvironment environment = new FumlEnvironment();
    private UnitDefinition unit;
    
    public Alf(String fileName) throws AlfExecutionError {

        this.unit = AlfParser.parse(fileName);
        this.unit.addImplicitImports();
        this.addPrimitiveBehaviorMappings();
        this.addBuiltInBehaviorMappings();

        new AlfMapper(this.unit).getModelElements();

        this.addBasicInputOutput();

    }

    private void addPrimitiveBehaviorMappings() throws AlfExecutionError {
        NamespaceDefinition definition = this.unit.getDefinition();
        
        QualifiedName packageName = new QualifiedName();
        packageName.setIsAbsolute();
        packageName.addName("UML");
        packageName.addName("AuxiliaryConstructs");
        packageName.addName("PrimitiveTypes");
        
        ArrayList<Member> members = packageName.resolve(definition);
        if (members.size() == 0) {
            throw new AlfExecutionError("Element not found: " + packageName);
        } else if (members.size() > 1) {
            throw new AlfExecutionError("Ambiguous reference: " + packageName);
        } else if (!(members.get(0) instanceof PackageDefinition)) {
            throw new AlfExecutionError("Not a package: " + packageName);
        } else {
            PackageDefinition typesPackage = (PackageDefinition)members.get(0); 
            Member completion = typesPackage.completeStub();
            if (completion != null && completion.isError()) {
                throw new AlfExecutionError(((ErrorMember)completion).toString());
            } else {
                for (Member member: typesPackage.getOwnedMembers()) {
                    if (member instanceof DataTypeDefinition) {
                        new PrimitiveTypeMapping(this.environment).setSourceNode(member);
                    }
                }              
            }
        }

    }

    private void addBasicInputOutput() throws AlfExecutionError {
        NamespaceDefinition definition = this.unit.getDefinition();
        
        QualifiedName packageName = new QualifiedName();
        packageName.setIsAbsolute();
        packageName.addName("FoundationalModelLibrary");
        packageName.addName("BasicInputOutput");
        
        QualifiedName className = packageName.copy();
        className.addName("StandardOutputChannel");
        
        ArrayList<Member> members = className.resolve(definition);
        if (members.size() == 0) {
            throw new AlfExecutionError("Element not found: " + className);
        } else if (members.size() > 1) {
            throw new AlfExecutionError("Ambiguous reference: " + className);
        } else if (!(members.get(0) instanceof ClassDefinition)) {
            throw new AlfExecutionError("Not a class: " + className);
        } else {
            ClassDefinition channelClassDefinition = (ClassDefinition)members.get(0); 
            Member completion = channelClassDefinition.completeStub();
            if (completion != null && completion.isError()) {
                throw new AlfExecutionError(((ErrorMember)completion).toString());
            } else {
                Class_ channelClass = (Class_)((ClassDefinitionMapping)(channelClassDefinition.getTargetNode())).getClassifier();
                StandardOutputChannelObject standardOutputChannel = new StandardOutputChannelObject();
                standardOutputChannel.types.addValue(channelClass);
                standardOutputChannel.open();
                this.environment.addInstance(standardOutputChannel);
            }
        }
    }

    private void addBuiltInBehaviorMappings() {
        // TODO Auto-generated method stub               
    }

    private ArrayList<ActivityDefinition> filterActivities(ArrayList<Member> members) {
        ArrayList<ActivityDefinition> activities = new ArrayList<ActivityDefinition>();
        for (Member member : members) {
            if (member instanceof ActivityDefinition) {
                activities.add((ActivityDefinition) member);
            }
        }
        return activities;
    }

    public void execute(ActivityDefinition definition) {
        Activity activity = ((ActivityDefinitionMapping) (definition.getTargetNode()))
                .getActivity();
        this.environment.execute(activity, new ParameterValueList());
    }

    public void execute() throws AlfExecutionError {
        NamespaceDefinition definition = unit.getDefinition();
        
        if (!(definition instanceof ActivityDefinition)) {
            throw new AlfExecutionError("Not an activity: " + definition.getQualifiedName());
        } else {
            this.execute((ActivityDefinition)definition);
        }
    }

    public void execute(String name) throws AlfExecutionError {
        NamespaceDefinition definition = unit.getDefinition();

        ArrayList<Member> members = definition.resolve(name);
        if (members.size() == 1 && members.get(0).isError()) {
            throw new AlfExecutionError(((ErrorMember) members.get(0)).getError().toString());
        }

        ArrayList<ActivityDefinition> activityDefinitions = this.filterActivities(members);

        if (activityDefinitions.size() == 0) {
            throw new AlfExecutionError("No activity " + name + " in "
                    + definition.getQualifiedName());
        } else if (activityDefinitions.size() > 1) {
            throw new AlfExecutionError("Ambiguous activity " + name + " in "
                    + definition.getQualifiedName());
        } else {
            this.execute(activityDefinitions.get(0));
        }
    }

    public static void main(String[] args) {
        System.out.println("Alf " + AlfParser.version);

        try {
            if (args.length >= 1 && args.length <=2) {
                Alf alf = new Alf(args[0]);
                if (args.length == 1) {
                    alf.execute();
                } else {
                    alf.execute(args[1]);
                }
            } else {
                System.out.println("Usage is");
                System.out.println("         java Alf inputfile");
                System.out.println("OR");
                System.out.println("         java Alf inputfile activityName");
            }
        } catch (AlfExecutionError e) {
            System.out.println("Execution error: " + e.getMessage());
        }
    }

}

package alf.mapping;

import alf.syntax.namespaces.UnitDefinition;
import alf.parser.AlfParser;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;

public class AlfMapper {
    
    private MappingFactory factory = new MappingFactory();
    private MappingNode mapping;
    
    public AlfMapper(UnitDefinition unit) {
        mapping = factory.getMapping(unit.getRootNamespace());
    }
    
    public ArrayList<Element> getModelElements() {
        return mapping.getModelElements();
    }
    
    public void print() {
        mapping.print();
    }
    
    public static void main(String[] args) {      
        System.out.println("Alf " + AlfParser.version + " Mapper");

        UnitDefinition unit;
        
        if (args.length == 0) {
            unit = AlfParser.parse(null);
          } else if (args.length == 1) {
            unit = AlfParser.parse(args[0]);
          } else {
            System.out.println("Usage is");
            System.out.println("         java AlfMapper < inputfile");
            System.out.println("OR");
            System.out.println("         java AlfMapper inputfile");
            return;
          }
        
        System.out.println("Mapping...");

        unit.addImplicitImports();
        AlfMapper mapper = new AlfMapper(unit);
        mapper.print();

    }

}

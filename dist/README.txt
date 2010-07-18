ACTION LANGUAGE FOR UML (Alf) PARSER
------------------------------------
Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)

Alf is an action language for UML being developed in response to the OMG Request
for Proposals "Concrete Syntax for a UML Action Language". The second revised
submission is currently due August 23, 2010. For a copy of the RFP and the 
latest draft specification, see

http://lib.modeldriven.org/MDLibrary/trunk/Applications/Alf-Reference-Implementation/doc/

The Alf parser is updated to reflect the latest draft specification. It is
implemented using JavaCC compiler-compiler technology. The parser can be run
using the alfp.bat batch file. The alf-parser.jar archive is required.

Usage is:
         alfp [ -complete ] < inputfile
OR
         alfp [ -complete ] inputfile
         
The parser prints an indented textual representation of the abstract syntax tree 
for the input file. The input file must contain a single Alf unit.

The "-complete" option results in the completion of all imports and stubs. With
this option, the parser prints the abstract syntax tree beginning at the root 
namespace, rather than the input unit.

Imported units are expected to be found in appropriate subdirectories of either
Root\Model (for model units) or Root\Library (for library units), where the Root
directory is found under the directory in which the parser is run. Subdirectory 
paths are constructed from the fully qualified unit name. Files must have the
".alf" extension. (For example, "MyModel::MyPackage::MyClass" is expected to be 
found in Root\Model\MyModel\MyPackage\MyClass.alf, while 
"FoundationalModelLibrary::BasicInputOutput" is expected to be found in
Root\Library\FoundationalModelLibrary\BasicInputOutput.alf). 

Some sample Alf code can be found in the tests.zip archive.

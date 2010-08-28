ACTION LANGUAGE FOR UML (Alf) PARSER
------------------------------------
Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)

Alf is an action language for UML developed in response to the OMG Request
for Proposals "Concrete Syntax for a UML Action Language". For a copy of the RFP
and the submitted specification, see

http://lib.modeldriven.org/MDLibrary/trunk/Applications/Alf-Reference-Implementation/doc/

The Alf parser is updated to reflect the latest draft specification. It is
implemented using JavaCC compiler-compiler technology. The parser can be run
using the alfp.bat batch file. The alf-parser.jar archive is required.

Usage is:
         alfp < inputfile
OR
         alfp inputfile
         
The parser prints an indented textual representation of the abstract syntax tree 
for the input file, following the normative abstract syntax model from the
specification. The input file must contain a single Alf unit.

Some sample Alf code can be found in the tests.zip archive.

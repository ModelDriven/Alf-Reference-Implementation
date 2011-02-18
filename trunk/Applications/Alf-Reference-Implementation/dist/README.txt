ACTION LANGUAGE FOR UML (Alf) PARSER
------------------------------------
Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)

Alf is an action language for UML developed in response to the OMG Request
for Proposals "Concrete Syntax for a UML Action Language". For a copy of the RFP
and the 1.0 Beta 1 specification, see

http://lib.modeldriven.org/MDLibrary/tags/Applications/Alf-Reference-Implementation/Alf-1-0-Beta-1/doc/

The Alf Parser reflects the Beta 1 specification. It is implemented using JavaCC
compiler-compiler technology and constructs an abstract syntax tree for the
parsed text. The source Java classes for the abstract syntax tree elements are
generated from the Alf abstract syntax metamodel

The parser source code and a compiled JAR file can be found at

http://lib.modeldriven.org/MDLibrary/tags/Applications/Alf-Reference-Implementation/Alf-1-0-Beta-1/dist/

The source code hierarchy is archived in the file alf-parser-src.zip and the
compiled classes are in the file alf-parser.jar. The entry class is
org.modeldriven.alf.parser.AlfParser. It can either be run as a main program
from the command line or used as an API via its methods.

Licensing
---------

Data Access Technology, Inc., is the copyright owner of all Alf Parser source
code and hereby licenses such software to you under the Academic Free License
version 3.0, which may be found at

http://www.opensource.org/licenses/afl-3.0.php


Command Line Execution
----------------------

The AlfParser can be run from the Windows command line using the alfp.bat batch 
file. The alf-parser.jar archive is required.

Usage is
         alfp [-use] < inputfile
OR
         alfp [-use] inputfile
Options:
  -u   Parse as a unit (default)
  -s   Parse as a statement sequence
  -e   Parse as an expression
         
The parser prints an indented textual representation of the abstract syntax tree 
for the input file, following the normative abstract syntax model from the
specification. The input file may contain either a single Alf unit, a sequence
of Alf statements or an Alf expression, depending on the option selected. (If
no explicit option is given, the file is assumed to contain a unit.)

Some sample Alf code can be found in the tests.zip archive.

Application Program Interface
-----------------------------

An AlfParser is constructed with an input stream that provides the text to be
parsed. For example, 

AlfParser parser = new AlfParser(System.in);

constructs a parser of text from the system input. The AlfParser class has a
method corresponding to each non-terminal in the Alf grammar (see Annex C of the
specification document). Calling one of these methods parses the input text
as the corresponding non-terminal. The most common uses are:

UnitDefinition unit = parser.UnitDefinition();
Block statements = parser.StatementSequence(); // Note: return type is Block
Expression expr = parser.Expression();

The result of a successful parse is an instance of the corresponding abstract 
syntax node class in the package org.modeldriven.alf.syntax. The abstract
syntax tree rooted in this node can be printed using the print method. For
example,

unit.print("");

An unsuccessful parse results in the throwing of org.modeldriven.alf.parser.
ParseException.

ACTION LANGUAGE FOR UML (Alf) PARSER
------------------------------------
Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)

Alf is an action language for UML developed in response to the OMG Request for
Proposals "Concrete Syntax for a UML Action Language". For a copy of the RFP and
the 1.0 Beta 1 specification, see

http://lib.modeldriven.org/MDLibrary/trunk/Applications/Alf-Reference-Implementation/doc/

The Alf Parser reflects the Beta 1 specification. The parser is implemented 
using JavaCC compiler-compiler technology and constructs an abstract syntax tree 
for the parsed text. Base source Java classes for the abstract syntax tree 
elements are generated from the Alf abstract syntax metamodel. Additional 
manually coded implementation classes associated with each generated syntax 
element class are used to implement static semantic checks.

Licensing
---------

Data Access Technology, Inc., is the copyright owner of all Alf Parser source
code and hereby licenses such software to you under the Academic Free License
version 3.0, which may be found at

http://www.opensource.org/licenses/afl-3.0.php

Installation
------------

The parser source code and a compiled JAR file can be found at

http://lib.modeldriven.org/MDLibrary/trunk/Applications/Alf-Reference-Implementation/dist/

The source code hierarchy is archived in the file alf-parser-src.zip and the
compiled classes are in the file alf-parser.jar. The entry class is
org.modeldriven.alf.parser.AlfParser. It can either be run as a main program
from the command line or used as an API via its methods.

The files alf-parser.jar and alfp.bat should be placed in the same directory.
The archive Root.zip should also be unzipped into this directory (establishing a
Root subdirectory).

Release Notes
-------------

The Alf Parser implements the full Alf syntax at the Extended compliance level, 
as given in the "Consolidated LL Grammar" annex to the Alf Specification.

Static semantic checking is directly based on validating the constraints
defined in Part III of the Alf Specification. However, as errors were
discovered in these definitions, or inconsistencies with the descriptions in
Part II were identified, these were corrected in the implementation. All such
issues are being reported to the Alf Finalization Task Force for correction in
the final specification.

The following Alf features are NOT currently implemented:

- Profile and stereotype application (other than application of the standard 
stereotypes ModelLibrary, Create and Destroy)

- Alternative constructor invocations

- Limitation of super constructor invocation to occur in an expression statement 
at the start of a constructor body

- Overloading resolution.

Unit Resolution
---------------

Since this implementation is not embedded in a UML tool, Alf units are managed
as files. During the static semantic analysis of a unit, if an element of
another unit is referenced, then that unit is parsed into memory on demand from
the file corresponding to the unit in order to continue the checking of the
original unit.

Each Alf unit is expected to be contained in a single file, and each such file
must contain exactly one Alf unit. The files are contained in a directory
structure based on the qualified names of the units. Currently, all unit files
must be contained in one of the two subdirectories of the Root directory.

The Root/Library directory contains Alf unit definitions for the fUML
Foundational Model Library and the Alf Standard Model Library. These units
should not be moved or changed.

For example, the standard package Alf::Library::PrimitiveBehaviors is found in
the file

Root/Library/Alf/Library/PrimitiveBehaviors.alf

Subunits of PrimitiveBehaviors are then found in the corresponding subdirectory

Root/Library/Alf/Library/PrimitiveBehaviors

Note that the file PrimitiveBehaviors.alf contains the Alf definition for the
PrimitiveBehaviors package, while the subdirectory PrimitiveBehaviors groups the
files for subunits of that package (i.e., BooleanFunctions.alf,
IntegerFunctions.alf, etc.).

All user unit files should be placed under the Root/Model directory. This can be
thought of as the equivalent of the "user model" that would normally be managed
in a UML tool.

For example, a unit with the qualified name

'Property Management'::'Data Model'::Properties::Property

is expected to be found in the file

Root/Model/Property Management/Data Model/Properties/Property.alf

[Note that all characters in an unrestricted name (but not the surrounding quote
characters) are currently carried over to the file path without change. Thus,
characters that cannot be supported in file names must be avoided in unit
names.]

All namespace declarations are currently expected to resolve to Alf units.
Therefore, only units without namespace declarations are considered to be model
units. However, the effective "model scope" for such units is empty. References
from one unit to elements of another unit always requires an import from the
other unit (implicitly or explicitly). Note, however, that any elements imported
into a unit are also available to all subunits without those subunits having to
also import them.

The the Root/Model directory in the Root.zip archive contains examples of Alf
units that should all parse successfully with no constrain violations.

Command Line Execution
----------------------

The AlfParser can be run from the Windows command line using the alfp.bat batch 
file. The alf-parser.jar archive is required.

Usage is
         alfp [-options] < inputfile
OR
         alfp [-options] inputfile
Options:
  u   Parse as a unit (default)
  s   Parse as a statement sequence
  e   Parse as an expression

Additional options for units:
  c   Perform static semantic checking.
  d   Include derived properties in syntax tree.
  
The input file may contain either a single Alf unit, a sequence of Alf
statements or an Alf expression, depending on the option selected. (If no
explicit option is given, the file is assumed to contain a unit.) If the parse
is successful, the parser prints an indented textual representation of the
abstract syntax tree for the input file, following the normative abstract syntax
model from the specification. If the "d" options is given, the derived
properties are included in the printed abstract syntax tree (with names
preceeded by a "/"). If the "c" option is given, static semantic checking is
performed and the violated constraints, if any, are listed, including for each
violation the name of the constraint (as given in the Alf metamodel) and a
reference to the syntax element for which the constraint was violated

[The constraint violation error reporting is currently not very convenient,
since it refers to an abstract syntax element rather than a position in the
text. To at least obtain some context for the referenced syntax element, note
that printed reference includes at the beginning the numeric hashcode for the
element. Search for this numeric code surrounded by square brackets (e.g.,
"[32389396]") to find the defining occurance of the element in the abstract
syntax tree.]

Some sample Alf code can be found in the tests.zip archive. These units should
all parse successfully with no constraint violations, assuming that the
Root/Model directory contains the units provided in the Root.zip archive.

Application Program Interface
-----------------------------

All AlfParser class are contained in subpackages of org.modeldriven.alf. In the
following, it is assumed that an import has been done at this level, with
explicit qualifications shown for any subpackages.

An AlfParser is constructed with an input stream that provides the text to be
parsed. For example, 

parser.AlfParser parser = new parser.AlfParser(System.in);

constructs a parser of text from the system input. The AlfParser class has a
method corresponding to each non-terminal in the Alf grammar (see Annex C of the
specification document). Calling one of these methods parses the input text
as the corresponding non-terminal. The most common uses are:

syntax.units.UnitDefinition unit = parser.UnitDefinition();
syntax.statements.Block statements = parser.StatementSequence();
syntax.expressions.Expression expr = parser.Expression();

(Note that the return type of StatementSequence() is Block.) An unsuccessful
parse results in the throwing of parser.ParseException.

The result of a successful parse is an instance of the corresponding abstract 
syntax node class in the package org.modeldriven.alf.syntax. The abstract
syntax tree rooted in this node can be printed using the print method. For
example,

element.print();

To include derived properties in the printed tree, use the alternate print
method that has a boolean includeDerived parameter. For example,

element.print(true);

Static semantic checking is done by calling the checkConstaint method:

Collection<syntax.common.ConstraintViolation> violations = 
    element.checkConstraints();

As shown, the method returns a collection of the constraint violations it has
detected (if any). The ConstraintViolation object records the name of the
violated constraint (as defined in the Alf metamodel) and the syntax element
that violated the constraint, which may be acessed using the getConstraintName
and getViolatingElement operations, respectively. If no constraints are
violated, then a collection is still returned, but it is empty.

In order to allow correct name resolution during static semantic checking, it is
necessary to first establish the current scope surrounding the element being
checked. For an Expression or Block (statement sequence), this can be done
directly using the setCurrentScope method:

element.getImpl().setCurrentScope(currentScope);

where "currentScope" must be an instance of syntax.units.NamespaceDefinition.

A Unit defines its own current scope, so this does not need to be set
explicitly. Instead, the following resolution needs to be carried out for a
unit:

// Get the subunit stub for this unit, if any.
syntax.units.Member stub = unit.getImpl().getStub();

if (stub != null) {
   // If the unit is a subunit, resolve its stub.
   stub.setSubunit(unit);
   
} else {
   // Otherwise establish required implicit imports.
   unit.getImpl().addImplicitImports();
   
}

(The Alf specification requires implicit imports NOT be added if the unit is a
model library. This is not checked for in the above code, because the
addImplicitImports methods checks for that itself. Note also that imports are
only added above for model units, since subunits can access the implicit imports
from their enclosing model unit.)

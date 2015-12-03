# Action Language for UML (Alf) Open Source Implementation
_Copyright &copy; 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)_

Alf is an action language for UML developed in response to the OMG <em>Concrete 
Syntax for a UML Action Language</em> Request for Proposals. The Alf specification 
document can be found at http://www.omg.org/spec/ALF.
(See also http://solitaire.omg.org/issues/task-force/ALF11
for the list of open issues currently being addressed by the Alf 1.1 Revision
Task Force.)

This implementation is based on the Alf 1.0.1 specification.  It compiles Alf source text to the executable 
Foundational UML (fUML) subset of UML. The compilation can target either of two fUML execution engine implementations:

* The fUML Reference Implementation execution engine (see http://fuml.modeldriven.org)  
[v1.1.4](https://github.com/ModelDriven/fUML-Reference-Implementation/releases/tag/v1.1.4)
(this version includes updates for all issue resolutions approved for fUML 1.2, as well
as an "urgent issue" resolution to be included in fUML 1.2.1).
* The fUML execution engine from the Moka framework for model execution in the Eclipse Papyrus tool
(see http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution), v1.0.0 or later.

## Licensing

Data Access Technology, Inc. is copyright owner of the source code for this implementation. 
For licensing terms, see `LICENSING.txt`.

## Installation

This file `alf.zip` unzips into the installation directory for Alf. This directory
includes Unix (BASH) shell scripts and DOS batch files for running Alf. By
default, the `Libraries` and `UML/Libraries` subdirectories are expected 
to be in the same directory as the scripts. If you move them, set the environment variables 
`ALF_LIB` and `UML_LIB`, respectively, to their paths.

The file `alf-src.zip` unzips into four (Eclipse)[http://www.eclipse.org] projects that, together,
include the full source of the implementation. Eclipse 4.4 or later is required.

* `org.modeldriven.alf` - This is the base project, including the
Alf parser, static semantic checking and generic mapping to fUML. However,
it is not dependent on any specific fUML implementation.
	
* `org.modeldriven.alf.fuml.impl` - This project extends the base
project to specialize the mapping to target the [fUML Reference Implementation](http://fuml.modeldriven.org)
and to allow compiled Alf text to be executed. It does not depend on the
fUML Reference Implementation project directly but, rather, uses an exported
`fuml.jar` file.
	
* `org.modeldriven.alf.eclipse` - This project extends the base
project to specialize the mapping to target the [Eclipse UML2](https://projects.eclipse.org/projects/modeling.mdt.uml2)
metamodel implementation (v5.0.1 or later). It depends on Eclipse plugins and must be built in an Eclipse environment.
	
* `org.modeldriven.alf.eclipse.papyrus` - This project extends the
`org.modeldriven.alf.eclipse` project to allow compiled Alf text to be executed
using the fUML execution engine from the [Moke framework] (http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution)
for model execution in the [Eclipse Papyrus](http://www.eclipse.org/papyrus) modeling tool (v1.0.0 or later).
It depends on Eclipse plugins and must be built in an Eclipse environment.

## Unit Resolution

Since this implementation is not embedded in a UML tool, Alf units are managed
as files. During the static semantic analysis of a unit, if an element of
another unit is referenced, then that unit is parsed into memory on demand from
the file corresponding to the unit in order to continue the checking of the
original unit.

Each Alf unit is expected to be contained in a single file, and each such file
must contain exactly one Alf unit. The files are contained in a directory
structure based on the qualified names of the units.

### The Model Directory

All user unit files corresponding to a single "user model" should be placed in
a <em>model directory</em> structured as described above. By default, the model directory
is the <tt>Models</tt> subdirectory of the installation directory, but this
may be overridden using a command line option.

For example, with the default location for the model directory, a package with the 
qualified name

`'Property Management'::'Data Model'::Properties`

would be expected to be found in the file

`Models/Property Management/Data Model/Properties.alf`

Subunits of `Properties` are then found in the corresponding subdirectory

`Models/Property Management/Data Model/Properties`

The _file_ `Properties.alf` contains the Alf definition for the `Properties` package, while the 
_subdirectory_ `Properties` groups the files for subunits  of that package (e.g., the class 
`Properties::Property` is found in the file `Property.alf` in the `Properties` directory.).

_Note that all characters in an unrestricted name (but not the surrounding 
quote characters) are currently carried over to the file path without change. 
Thus, characters that cannot be supported in file names must be avoided in unit
names._

All namespace declarations are currently expected to resolve to Alf units.
Therefore, only units without namespace declarations are considered to be model
units. The model scope for such units is all the model units within the same
model directory (i.e., "in the same model"). Thus, one model unit may refer to 
another model unit located in the same model directory, without the need for
explicitly importing the other unit. References to any other elements of another
unit requires an import from the other unit (which is done implicitly for
units from the Alf library). Note, however, that any elements imported
into a unit are also available to all subunits without those subunits having to
also import them.

### The Library Directory

The _library directory_ contains Alf unit definitions for the fUML
Foundational Model Library and the Alf Standard Model Library. These units
should not be moved from the library directory structure or changed. By default, 
the library directory is the `Libraries` subdirectory of the installation 
directory, but this may be changed using a command line option or by setting the 
`ALF_LIB` environment variable.

For example, with the default location for the library directory, the standard 
package `Alf::Library::PrimitiveBehaviors` is found in the file 

`Libraries/Alf/Library/PrimitiveBehaviors.alf`

Subunits of `PrimitiveBehaviors` are then found in the corresponding subdirectory

<tt>Libraries/Alf/Library/PrimitiveBehaviors</tt>

### The UML Library Directory

The _UML library directory_ contains compiled versions of the fUML Foundational
Model Library and Alf Standard Model Library implementations, as Eclipse UML2 files.
These files must be kept together in one directory. By default, the UML library
directory is the _UML/Libraries_ subdirectory of the installation
directory, but this may be changed using a command line option or by setting the
_UML_LIB_ environment variable.

## Unit Execution

To be executable, a unit must be the definition of either an activity with no 
parameters or a non-abstract active class with a classifier behavior. 
Execution of such a unit proceeds as follows:

1. The specified unit is parsed into an abstract syntax representation. Any units imported by 
the specified unit are also, recursively, parsed. If there is a syntax error, this is reported and 
processing ends.
_(Note: Since units cannot be "pre-compiled" when using the fUML Reference Implementation,
the entire content of the library directory are parsed and loaded on
each execution. When using the Eclipse implementation, the pre-compiled library models in the
UML library directory are used instead.)_

2. All parsed units are checked for violations of the abstract syntax
constraints defined in the Alf specification. If there are any constraint
violations, they are reported and processing ends. Constraint violation messages 
take the form 

   _`constraintName`_ `in` _`fileName`_ `at line` _`m`_`, column` _`n`_

   The _`constraintName`_ is the name of the violated constraint, as given in
the Alf specification. _(Note 1: Some constraints have additional conditions
implemented over what is currently given in the specification. Note 2: If the
fileName has a suffix of the form `<...>`, then the constraint
violation happened in a template instantiation. This should only happen if the
error actually exists in the template being instantiated.)_

3. All parsed units are mapped to fUML. If the fUML Reference Implementation is being used,
this mapping is to an in-memory representation. If the Eclipse UML2 implementation is being
used, the fUML output is written to an Eclipse UML2 file.
_(Note: If a mapping error occurs, processing ends. However, if there are no constrain violations, 
mapping should always succeed, so a mapping failure indicates a system implementation error.)_

4. If the fUML Reference Implementation is being used, the unit is executed immediately after
compilation. If the Eclipse UML2 implementation is being used, then the output Eclipse UML2 file
can be executed after compilation. In either case, on execution, an execution environment is 
created at a fUML locus, and the unit mapping is executed in this environment. The console is used 
for standard input and output. Execution tracing is at the specified debug level, to the console and 
the file <tt>alf.log</tt> (unless this configuration is changed in `log4j.properties`). 
_(Note: Execution tracing is currently only available for the fUML Reference Implementation.)_

## Command Line Scripts
## `alf`: Model execution using the fUML Reference Implementation

A model can be executed from the command line using the `alf` shell 
script (for Unix) or the `alf.bat` batch file (for Windows/DOS). The model is
compiled to an in-memory representation and executed using the fUML Reference
Implementation. Usage is

`alf` _`[options] unitName`_

where _`unitName`_ is the fully qualified name of a unit to be executed.
The allowable _`options`_ are

`-d` _`level`_

Sets the debug level for trace output from the fUML execution engine. Useful
levels are:

* `OFF` turns off trace output.
* `ERROR` reports only serious errors (such as when a primitive
behavior implementation cannot be found during execution).
* `INFO` outputs basic trace information on the execution of activities
and actions.
* `DEBUG` outputs detailed trace information on activity execution.

The default is as configured in the `log4j.properties` file in the
installation directory.

`-f`

Treat the _`unitName`_ as a file name, rather than as a qualified
name. The named file is expected to be found directly in the model directory
and the unit must have the same name as the file name (with any `.alf`
extension removed).  

`-l` _`path`_

Sets the library directory location to _`path`_. If this option is not
given and the `ALF_LIB` environment variable is set, then the value of `ALF_LIB` is 
used as the library directory location. Otherwise, the default of `Libraries`
is used.

`-m` _`path`_

Sets the model directory location to _`path`_. Qualified name resolution to
unit file paths is relative to the root of the model directory. If this option
is not given, the default of `Models` is used.

`-p`

Parse and constraint check the identified unit, but do not execute it. This is
useful for syntactic and static semantic validation of units that are not
executable by themselves.

`-P`

Parse and constraint check, as for the `-p` option, and then print out the
resulting abstract syntax tree. Note that the print out will occur even if there
are constraint violations.

`-v`

Sets verbose mode, in which status messages are printed about parsing and other
processing steps leading up to execution. If this option is used alone without
specifying a unit name (i.e., `alf -v`), then just version information is
printed.

### `alfc`: Model compilation to an Eclipse UML2 file

A model can be compiled from the command line using the `alfc` shell 
script (for Unix) or the <tt>alfc.bat</tt> batch file (for Windows/DOS). The output is
an Eclipse UML2 file with the same name as the compiled unit and the extension
`.uml` appended. All non-library imported units and subunits are resolved at 
the source level, compiled and included in the output file. References to library units,
however, are linked to already compiled UML library files.
Usage is

`alfc` _`[options] unitName`_

where _`unitName`_ is the fully qualified name of a unit to be compiled.
The allowable _`options`_ are

`-f`

Treat the _`unitName`_ as a file name, rather than as a qualified
name. The named file is expected to be found directly in the model directory
and the unit must have the same name as the file name (with any `.alf`
extension removed).  

`-l` _`path`_

Sets the library directory location to _`path`_. If this option is not
given and the `UML_LIB` environment variable is set, then the value of `UML_LIB` is 
used as the library directory location. Otherwise, the default of `UML/Libraries`
is used.

`-m` _`path`_

Sets the model directory location to _`path`_. Qualified name resolution to
unit file paths is relative to the root of the model directory. If this option
is not given, the default of `Models` is used.

`-p`

Parse and constraint check the identified unit, but do not generate output UML for it. This is
useful for syntactic and static semantic validation of units that are not
executable by themselves.

`-P`

Parse and constraint check, as for the `-p` option, and then print out the
resulting abstract syntax tree. Note that the print out will occur even if there
are constraint violations.

`-u` _`path`_

Sets the UML output directory location to _`path`_. The output Eclipse UML2 file is
written to this directory. The file name is the unit name with `.uml` appended. If this
option is not given, the default of `UML` is used.

`-v`

Sets verbose mode, in which status messages are printed about parsing and other
steps in the compilation process. If this option is used alone without
specifying a unit name (i.e., `alfc -v`), then just version information is
printed.

### `fuml`: Model execution using Moka

An Eclipse UML2 file can be executed from the command line using the `fuml` shell 
script (for Unix) or the `fuml.bat` batch file (for Windows/DOS). The model is executed
using the Moka fUML execution engine. Usage is

`fuml` _`[options] fileName`_

where _`fileName`_ is the name of an Eclipse UML2 file to be executed. If the given
file name does not have a `.uml` extension, one is added.
The allowable _`options`_ are

`-l` _`path`_

Sets the library directory location to _`path`_. If this option is not
given and the `UML_LIB` environment variable is set, then the value of `UML_LIB` is 
used as the library directory location. Otherwise, the default of `UML/Libraries`
is used.

`-u` _`path`_

Sets the UML input directory location to _`path`_. The Eclipse UML2 file is
expected to be found in this directory. If this option is not given, the default of `UML` is used.

`-v`

Sets verbose mode, in which status messages are printed about parsing and other
processing steps leading up to execution.

## Sample Code

The installation `Models` directory contains a "hello world" example. A compiled version of this 
activity can be found in the installation `UML` directory. The distribution directory also includes zip archives
with more extensive samples, all of which are described further below.

### Hello World

The `Models` directory included in the installation archive contains an
Alf "Hello World" activity. To run this, use the command

`alf Hello`

from the installation directory.

The `UML` directory included in the installation archive contains a compiled version of this
activity. To run this, use the command

`fuml Hello`

from the installation directory.

To recompile the <tt>Hello</tt> activity from Alf to UML, use the command

`alfc Hello`

from the installation directory.

### Tests

The archive `tests-x.zip` unzips into a directory containing a set of simple 
execution tests. These can be run with a command of the form

`alf -m tests-x` _`testUnitName`_

Replace `tests-x` with the complete path to the directory, as
necessary. To run with trace output, use either the `-d INFO` or 
`-d DEBUG` option.

To run the entire suite of tests, use the command

`alf -m tests-x _RunTests`

The archive `tests-uml.zip` unzips into a directory containing compiled files
for all the tests found in `tests-x`. These can be run with a command of the
form

`fuml -u tests-uml` _`testUnitName`_

To run the entire suite of compoiled tests, use the command

`fuml -u tests-uml _RunTests`

A single test unit can be recompiled from Alf to UML using a command of the form

`alfc -m tests-x -u tests-uml` _`testUnitName`_

Note that the _`testUnitName`_ is the same as the file
name, but without the `.alf` extension. I.e., the file 
`Expressions_Assignment.alf` contains the unit named
`Expressions_Assignment`. However, the units `_TestRunner`, `AssertTrue`, 
`AssertFalse`, `AssertList` and `Write` are helper
activities, not tests, and are not individually executable.

### Property Management Example

The archive `PropertyManagementExample.zip` unzips into a directory
containing a complete Alf implementation of the Property Management example from
Annex B.3 of the Alf specification. This example defines a service, rather than
an application. However, the activity `Test` sends one of each of the service
requests, printing out the results. To run this activity, use the command

`alf -m PropertyManagementExample Test`

Replace `PropertyMangementExample` with the complete path to the model
directory, as necessary.  (This should be run without trace output, 
so use the `-d OFF` option if the debug level default has been 
changed from `OFF`.)

### Online Bookstore

The archive `OnlineBookstore.zip` unzips into a directory containing a
complete Alf implementation of the Online Bookstore example from Appendix B
of the book _Executable UML_ by Stephen Mellor and Marc Balcer. It also
includes a simple textual user interface for customer interaction. By default,
the inventory consists of a single book (the _Executable UML_ book,
actually). To run the application, use the command

`alf -m OnlineBookstore Main`

Replace `OnlineBookstore` with the complete path to the model directory,
as necessary. (This should be run without trace output, so use the `-d OFF`
option if the debug level default has been changed from `OFF`.)

The `tests-uml` archive described above also contains a compiled version
of the Online Bookstore example. To run this version, use the command

`fuml -u tests-uml Main`

Replace `tests-uml` with the complete path to the directory as necessary.


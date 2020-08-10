# Action Language for UML (Alf) <br> Open Source Reference Implementation
_Copyright &copy; 2011-2019 Model Driven Solutions, Inc._

Alf is an action language for UML developed in response to the OMG <em>Concrete 
Syntax for a UML Action Language</em> Request for Proposals. The Alf specification 
document can be found at http://www.omg.org/spec/ALF. (See also http://solitaire.omg.org/issues/task-force/ALF11
for the list of issues addressed by the Alf 1.1 Revision Task Force.)

This implementation is based on the Alf 1.1 specification. It compiles Alf source text to the executable Foundational UML (fUML) subset of UML. 
The compilation can target either of two fUML execution engine implementations:

* The fUML Reference Implementation execution engine (see http://fuml.modeldriven.org), 
[v1.3.0](https://github.com/ModelDriven/fUML-Reference-Implementation/releases/tag/v1.3.0)
(this version conforms to fUML 1.3).
* The fUML execution engine from the Moka framework for model execution in the Eclipse Papyrus tool
(see http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution), v3.1.0.

The implementation handles the full Alf syntax at the Extended compliance level, as given in Annex C Consolidated 
LL Grammar of the Alf Specification.

Static semantic checking is directly based on validating the constraints defined in the abstract syntax of the Alf Specification. 
However, as errors were discovered in these definitions, or inconsistencies with corresponding feature descriptions were identified, 
these were corrected in the implementation. All such issues have either been corrected in the latest version of the specification 
or have been reported to the Alf Revision Task Force for correction in a future version.

The latest version of the implementation is available at http://alf.modeldriven.org. 

## Licensing

Data Access Technology, Inc. is copyright owner of the source code for this implementation. For licensing terms, see 
the file [`LICENSING.txt`](https://github.com/ModelDriven/Alf-Reference-Implementation/blob/master/dist/LICENSING.txt).

## Installation

The latest packaged distribution of the implementation is available in the 
[`dist`](https://github.com/ModelDriven/Alf-Reference-Implementation/tree/master/dist)
directory.

Within this directory, the file `alf.zip` unzips into the installation directory for Alf. 
The installation directory includes Unix (BASH) shell scripts and DOS batch files for running Alf. 
By default, the `Libraries` subdirectory is expected  to be in the same directory as the scripts. 
If you move it, set the environment variable `ALF_LIB` to its path.

## Projects

The base implementation source is organized into the following [Eclipse](http://www.eclipse.org) projects. Eclipse 4.6 or later is required.

* `org.modeldriven.alf` - This is the base project, including the
Alf parser, static semantic checking and generic mapping to fUML.
The generic mapping is not dependent on any specific UML metamodel or fUML implementation, 
but it must be extended with a specific UML metamodel implementation in order to generate
actual UML model output.
	
* `org.modeldriven.alf.fuml.impl` - This project extends the base
project to specialize the mapping to target the [fUML Reference Implementation](http://fuml.modeldriven.org)
and to allow compiled Alf text to be executed. It does not depend on the
fUML Reference Implementation project directly but, rather, uses an exported
`fuml.jar` file.
	
* `org.modeldriven.alf.eclipse` - This project extends the base
project to specialize the mapping to target the [Eclipse UML2](https://projects.eclipse.org/projects/modeling.mdt.uml2)
metamodel implementation (v5.2.0 or later). It depends on Eclipse plugins and must be built in an Eclipse environment.
	
* `org.modeldriven.alf.eclipse.moka` - This project extends the 
`org.modeldriven.alf.eclipse` project to allow compiled Alf text to be executed
using the fUML execution engine from the [Moka framework](http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution)
for model execution in the [Eclipse Papyrus](http://www.eclipse.org/papyrus) modeling tool (v3.1.0 or later).
It depends on Eclipse plugins and must be built in an Eclipse environment. (Note: This project was called
`org.modeldriven.alf.eclipse.papyrus` in previous versions of the reference implementation.)

* `org.modeldriven.alf.tests` - This project provides automated tests.

## Building

### Maven project structure

The project structure under Maven has a structure that allows building the regular Java modules and the Eclipse-based modules (using [Tycho](https://www.eclipse.org/tycho/)).

#### Regular Java build

* `./master/pom.xml` - a module that provides standard configurationsto be shared by all modules, including the root module.
* `./pom.xml` - the parent module for the regular Java modules
* `./org.modeldriven.alf` - see [Projects](#Projects) above.
* `./org.modeldriven.alf.fuml.impl` - see [Projects](#Projects) above.
* `./org.modeldriven.alf.tests` - see [Projects](#Projects) above.
* `./org.modeldriven.alf.bundle` - produces a OSGi-bundle from org.modeldriven.alf
* `./org.modeldriven.alf.fuml.impl.bundle` - produces a OSGi-bundle from org.modeldriven.alf.fuml.impl

#### Tycho build

* `./tycho-pom.xml` - a convenience module that can be used for performing the Tycho (OSGi) aware part of the build. Equivalent to building `./tycho-parent/pom.xml`
* `./tycho-parent/pom.xml` - the parent module for the Tycho/Eclipse modules
* `./org.modeldriven.alf.eclipse` - see [Projects](#Projects) above.
* `./org.modeldriven.alf.eclipse.moka` - see [Projects](#Projects) above.

### Steps

To perform the regular Java build, execute from the root directory:

```
# Only once
mvn clean install -f master
# Regular build
mvn clean install
```

To perform the Tycho build, execute from the root directory:

```
mvn clean install -f tycho-pom.xml
```

## Importing the projects into Eclipse

TBD

## Further Information

For further information on using the implementation, see the [wiki](https://github.com/ModelDriven/Alf-Reference-Implementation/wiki/Home). 

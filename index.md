### Current Release
Base release: [Version 1.1.0](https://github.com/ModelDriven/Alf-Reference-Implementation/releases/tag/v1.1.0), conforming to [Alf 1.1](http://www.omg.org/spec/ALF/1.1)<br>
Latest release: [Version 1.1.0h](https://github.com/ModelDriven/Alf-Reference-Implementation/releases/tag/v1.1.0h)

### What is Alf?

Alf is a textual language for representing [Unified Modeling Language (UML)](http://www.uml.org) models. It was developed with the primary purpose of acting as the notation for specifying executable behaviors within an overall graphical UML model. However, it also provides an extended notation for structural modeling. This makes it possible to represent complete, executable models using Alf.

Like UML, Alf is standardized by the [Object Management Group (OMG)](http://www.omg.org), which maintains the [Alf specification](http://www.omg.org/spec/ALF). The underlying semantics of Alf are provided by [Foundational UML (fUML)](http://www.omg.org/spec/FUML), which specifies precise execution semantics for a subset of UML. Alf notation covers this fUML subset, which includes the typical structural modeling constructs of UML (classes, associations, data types and enumerations), as well as behavioral modeling using UML activities composed from a rich set of primitive actions.

### What is the Alf Reference Implementation?

The Alf Reference Implementation is a complete, open source implementation of the Alf language. It compiles Alf source text to fUML. The compilation can target either of two fUML execution engine implementations:

* The [fUML Reference Implementation](http://fuml.modeldriven.org) execution engine
* The fUML execution engine from the [Moka framework](http://wiki.eclipse.org/Papyrus/UserGuide/ModelExecution) for model execution in the [Eclipse Papyrus](http://www.eclipse.org/papyrus) tool.

The implementation handles the full Alf syntax at the Extended conformance level, as given in Annex C (*Consolidated LL Grammar*) of the [Alf Specification](http://www.omg.org/spec/ALF/Current).

Static semantic checking is directly based on validating the constraints defined in the Alf abstract syntax metamodel, as given in the [Alf Specification](http://www.omg.org/spec/ALF/Current). However, as errors were discovered in these definitions, or inconsistencies with the informal semantic descriptions were identified, these were corrected in the implementation. All such issues have either been corrected in the latest version of the specification or have been reported to the [Alf Revision Task Force](http://issues.omg.org/issues/task-force/ALF11) for correction in a future version.

### Further Information

For further information, see the [Alf Reference Implementation Wiki](https://github.com/ModelDriven/Alf-Reference-Implementation/wiki).

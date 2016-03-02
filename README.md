# jaxon
What do you get when you combine Jack and Klaxon? A jaxon, simple, but powerfull XML library for java

## Introducion
Jaxon is library which transforms objects into XML and back completelly automatically. The only thing you need to do is to declare your class as implementation of JaxonSerializable interface and just keep convence of data classes in java (like EJB - getters, setters and nonparametric constructor).

Nothing new? The jaxon is an "academic" implementation, simple, configurable and modulable with reusable components. Its destination is not huge applications, but small applications with request of precision. And - the main part - generated XML is human readable (with minimum "technical" informations) and in user specified format (see `linus.xml` for demonstration).

## How does it work?
Each (Java) Object (in fact `JaxonSerializable` object) is converted into jaxon's internal form - jack. The jack describes fields and values of them. The jack object is then transformed into klaxon object. Klaxon object is abstraction on XML DOM. This klaxon object is "rooted" (anotated with some header informations) into klaxon root object. Resulting root object is nextly converted into classical XML document. Finally, the XML document is stored into file, stream, writer or simply string.

The reverse process is similar, but you have to know information about type(s) of object(s) in the XML (so XML file has to contain some type specifiers).


## Example
Consider simple sample code (part of `cz.martlin.jaxon.TestSomeJaxon` class):
```java
final Config config = new Config();
final JaxonConverter converter = new JaxonConverter(config);

// me -> string
PersonTestTuples persons = PersonTestTuples.createMe();
Person me = persons.createObject();
System.out.println("It's me, " + me + ":");
```

Will produce following output:
```xml
It's me, Person [name=m@rtlin, age=24, employed=false]:
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<person description="person" format="Fry" type="cz.martlin.jaxon.testings.jaxon.person.Person">
  <name>m@rtlin</name>
  <age>24</age>
  <employed>false</employed>
</person>
```

When are used different configuration, the XML of person could look like this:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<jaxon>
  <header>
    <format>Hermes</format>
    <type>cz.martlin.jaxon.testings.jaxon.person.Person</type>
    <description>person</description>
  </header>
  <person type="cz.martlin.jaxon.testings.jaxon.person.Person">
    <name type="java.lang.String">m@rtlin</name>
    <age type="java.lang.Integer">24</age>
    <employed type="java.lang.Boolean">false</employed>
  </person>
</jaxon>
```

## TODO
The work is at the begining (not really, but) there is still lot of to do. For instance:
 - [x] fix maven build
 - [x] upgrade to Java 8
 - [ ] add logging (debug), clearify exceptions handling
 - [ ] add validation (XLS) and in-code (?)
 - [x] config. What else? 
 - [ ] Use jaxon to read jaxon config from jaxon file? Hm..
 - [ ] fix all theese TODOs and FIXMEs in sources
 - [ ] add support for arrrays
 - [x] add lots of translators (see cz.martlin.jaxon.j2k.data.SupportedTransformers for currently implemented)
 - [ ] add much more translators
 - [x] "fix" atomic value j2k formats (everything instide package cz.martlin.jaxon.j2k.atomics.format)
 - [x] write doc to all classes
 - [x] comment out prints in tests (looks bad in maven)
 - [x] add some transformers, mainly Hermes
 - [ ] add some more transformers, like Bender, and also - Amy, Leela, ...?, generalize cz.martlin.jaxon.j2k.transformers.AbstractFuturamaJ2KTransformer
 - [ ] add support of comments to klaxon? add support of cdata (and entities and stuff like that) to klaxon
 - [x] add description support (like cz.martlin.jaxon.jack.data.values.JackObject#jackDescription) and (cz.martlin.jaxon.j2k.abstracts.JackToKlaxonSerializable) (or not?)
 - [ ] create jason (Jack+JSON), and then maybe jamol (Jack+AML), jaties (Jack+Properties), jain (Jack+plain)...?
 - [x] test. Write tests, create testing classes and instances and everything. Complex and describing. Once again: TEST!
 - [ ] Everything except K2DocFormat.CHILDREN_EVERYWHERE fails when parsing (KlaxonString is not KlaxonString as expected)


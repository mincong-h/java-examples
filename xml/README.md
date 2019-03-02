# XML

## XPath

XML Path Language (XPath) examples.

### Following Sibling

Expression `following-sibling` returns the sibling node just after the selected
one. For example, the following XPath returns user C, which is the following
sibling of user B.

```xml
<users>
  <user id="A" />
  <user id="B" />
  <user id="C" />
</users>
```

```xpath
//user[@id='B']/following-sibling::user[1]
```

## XSD

An XML Schema describes the structure of an XML document. The XML Schema
language is also referred to as XML Schema Definition (XSD).

Here's an example coming from W3Schools, where an element called "note" is
defined under the default schema. Its type is complex, which contains a
sequence of 4 elements (`xs:element`) of type string (`xs:string`). In this
case, complex type is nested inside the element node `note`, and it is an
anonymous type (name is not allowed).

```xml
<?xml version="1.0"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="note">
    <xs:complexType>
      <xs:sequence>
        <xs:element name="to" type="xs:string"/>
        <xs:element name="from" type="xs:string"/>
        <xs:element name="heading" type="xs:string"/>
        <xs:element name="body" type="xs:string"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>

</xs:schema>
```

You can also create a type outside of the element `note`, so that it is a named
type and can be re-used in different places.

```xml
<?xml version="1.0"?>
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">

  <xs:element name="note" type="noteType"/>

  <xs:complexType name="noteType">
    <xs:sequence>
      <xs:element name="to" type="xs:string"/>
      <xs:element name="from" type="xs:string"/>
      <xs:element name="heading" type="xs:string"/>
      <xs:element name="body" type="xs:string"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>
```

## References

- "XML Introduction", W3Schools. <https://www.w3schools.com/xml/schema_intro.asp>

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

## References

- "XML Introduction", W3Schools. <https://www.w3schools.com/xml/schema_intro.asp>

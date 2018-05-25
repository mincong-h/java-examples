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

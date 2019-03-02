package io.mincongh.xml.xsd;

import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSImplementationImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.xs.XSConstants;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSTypeDefinition;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mincong Huang
 * @since 1.0
 */
public class XsdTest {

  private XSModel model;

  @Before
  public void setUp() throws Exception {
    // Java API to parse XSD schema file
    // https://stackoverflow.com/questions/3996857
//    System.setProperty(
//        DOMImplementationRegistry.PROPERTY,
//        "com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl");
    DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
    XSImplementationImpl impl = (XSImplementationImpl) registry.getDOMImplementation("XS-Loader");
    XSLoader schemaLoader = impl.createXSLoader(null);
    model = schemaLoader.loadURI("src/test/resources/note.xsd");
  }

  @Test
  public void xsElement() {
    XSElementDeclaration xsElement = model.getElementDeclaration("note", null);
    assertThat(xsElement.getName()).isEqualTo("note");
  }

  @Test
  public void xsComplexType() {
    XSElementDeclaration elementDeclaration = model.getElementDeclaration("note", null);

    // xs:complexType
    XSComplexTypeDecl complexTypeDecl = (XSComplexTypeDecl) elementDeclaration.getTypeDefinition();
    assertThat(complexTypeDecl.getTypeCategory()).isEqualTo(XSTypeDefinition.COMPLEX_TYPE);

    // xs:sequence
    XSParticle particle = complexTypeDecl.getParticle();
    XSModelGroup modelGroup = (XSModelGroup) particle.getTerm();
    XSObjectList particles = modelGroup.getParticles();
    assertThat(particles.getLength()).isEqualTo(4);

    // <xs:element name="to" type="xs:string"/>
    // <xs:element name="from" type="xs:string"/>
    // <xs:element name="heading" type="xs:string"/>
    // <xs:element name="body" type="xs:string"/>
    XSParticleDecl item0 = (XSParticleDecl) particles.item(0);
    assertThat(item0.getTerm().getName()).isEqualTo("to");
    assertThat(item0.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
    XSParticleDecl item1 = (XSParticleDecl) particles.item(1);
    assertThat(item1.getTerm().getName()).isEqualTo("from");
    assertThat(item1.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
    XSParticleDecl item2 = (XSParticleDecl) particles.item(2);
    assertThat(item2.getTerm().getName()).isEqualTo("heading");
    assertThat(item2.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
    XSParticleDecl item3 = (XSParticleDecl) particles.item(3);
    assertThat(item3.getTerm().getName()).isEqualTo("body");
    assertThat(item3.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
  }
}

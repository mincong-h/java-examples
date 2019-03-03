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

  private XSModel modelV1;
  private XSModel modelV2;

  @Before
  public void setUp() throws Exception {
    // Java API to parse XSD schema file
    // https://stackoverflow.com/questions/3996857
    DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
    XSImplementationImpl impl = (XSImplementationImpl) registry.getDOMImplementation("XS-Loader");
    XSLoader schemaLoader = impl.createXSLoader(null);
    modelV1 = schemaLoader.loadURI("src/test/resources/note.v1.xsd");
    modelV2 = schemaLoader.loadURI("src/test/resources/note.v2.xsd");
  }

  @Test
  public void xsElement() {
    XSElementDeclaration xsElementV1 = modelV1.getElementDeclaration("note", null);
    assertThat(xsElementV1.getName()).isEqualTo("note");

    XSElementDeclaration xsElementV2 = modelV2.getElementDeclaration("note", null);
    assertThat(xsElementV2.getName()).isEqualTo("note");
  }

  @Test
  public void xsComplexType_v1() {
    testComplexType(modelV1.getElementDeclaration("note", null));
  }

  @Test
  public void xsComplexType_v2() {
    testComplexType(modelV2.getElementDeclaration("note", null));
  }

  private void testComplexType(XSElementDeclaration elementDeclaration) {
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
    assertThat(item0.getMinOccurs()).isEqualTo(1);
    assertThat(item0.getMaxOccurs()).isEqualTo(1);
    assertThat(item0.getMaxOccursUnbounded()).isFalse();

    XSParticleDecl item1 = (XSParticleDecl) particles.item(1);
    assertThat(item1.getTerm().getName()).isEqualTo("from");
    assertThat(item1.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
    assertThat(item1.getMinOccurs()).isEqualTo(1);
    assertThat(item1.getMaxOccurs()).isEqualTo(1);
    assertThat(item1.getMaxOccursUnbounded()).isFalse();

    XSParticleDecl item2 = (XSParticleDecl) particles.item(2);
    assertThat(item2.getTerm().getName()).isEqualTo("heading");
    assertThat(item2.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
    assertThat(item2.getMinOccurs()).isEqualTo(1);
    assertThat(item2.getMaxOccurs()).isEqualTo(1);
    assertThat(item2.getMaxOccursUnbounded()).isFalse();

    XSParticleDecl item3 = (XSParticleDecl) particles.item(3);
    assertThat(item3.getTerm().getName()).isEqualTo("body");
    assertThat(item3.getTerm().getType()).isEqualTo(XSConstants.STRING_DT);
    assertThat(item3.getMinOccurs()).isEqualTo(1);
    assertThat(item3.getMaxOccurs()).isEqualTo(1);
    assertThat(item3.getMaxOccursUnbounded()).isFalse();
  }
}

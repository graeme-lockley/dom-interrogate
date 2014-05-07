package za.co.no9.util;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DOMUtilsTest {
    public static final String XML_STRING = "<xml attr1='AttrA' attr2='AttrB'><p>Hello</p><p>World</p></xml>";

    @Test
    public void should_create_an_XML_DOM_from_a_string() throws Exception {
        Document document = DOMUtils.create(XML_STRING);

        assertEquals("xml", document.getDocumentElement().getNodeName());
    }

    @Test
    public void should_get_valid_attribute_simple_case() throws Exception {
        assertEquals("AttrA", DOMUtils
                .from(XML_STRING)
                .attribute("attr1")
                .toString());
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void should_throw_an_exception_if_attempting_to_access_an_attribute_that_does_not_exist() throws Exception {
        assertNull(DOMUtils
                .from(XML_STRING)
                .attribute("attrX")
                .toString());
    }

    @Test
    public void should_return_a_default_value_if_attribute_does_not_exist() throws Exception {
        assertEquals("My Value", DOMUtils
                .from(XML_STRING)
                .attribute("attrX")
                .ifNullDefault("My Value")
                .toString());
    }

    @Test
    public void should_return_a_default_int_value_if_attribute_does_not_exist() throws Exception {
        assertEquals("123", DOMUtils
                .from(XML_STRING)
                .attribute("attrX")
                .ifNullDefault(123)
                .toString());

        assertEquals(123, DOMUtils
                .from(XML_STRING)
                .attribute("attrX")
                .ifNullDefault(123)
                .toInteger().intValue());
    }
}

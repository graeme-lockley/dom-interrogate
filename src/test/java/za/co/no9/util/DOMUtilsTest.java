package za.co.no9.util;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;

public class DOMUtilsTest {
    public static final String XML_STRING = "<xml attr1='AttrA' attr2='AttrB'><p>Hello</p><p>World</p></xml>";

    @Test
    public void should_create_an_XML_DOM_from_a_string() throws Exception {
        Document document = DOMUtils.create(XML_STRING);

        assertEquals("xml", document.getDocumentElement().getNodeName());
    }

    @Test
    public void should_get_valid_attribute() throws Exception {
        assertEquals("AttrA", DOMUtils
                .from(XML_STRING)
                .attribute("attr1")
                .toString());
    }
}

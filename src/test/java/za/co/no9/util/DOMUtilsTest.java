package za.co.no9.util;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertEquals;

public class DOMUtilsTest {
    @Test
    public void should_create_an_XML_DOM_from_a_string() throws Exception {
        Document document = DOMUtils.create("<xml><p>Hello World</p></xml>");

        assertEquals("xml", document.getDocumentElement().getNodeName());
    }
}

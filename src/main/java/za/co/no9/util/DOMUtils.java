package za.co.no9.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DOMUtils {

    public static Document create(String xmlContent) throws SAXException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes())) {
            InputSource inputSource = new InputSource(inputStream);
            return openDocument(inputSource);
        } catch (IOException | ParserConfigurationException ex) {
            throw new SAXException(ex);
        }
    }

    private static Document openDocument(InputSource inputSource) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(inputSource);
    }

    public static ElementDSL from(String xmlString) throws SAXException {
        return ElementDSL.from(create(xmlString).getDocumentElement());
    }

    public static class ElementDSL {
        private final Element element;

        public ElementDSL(Element element) {
            this.element = element;
        }

        public static ElementDSL from(Element element) {
            return new ElementDSL(element);
        }

        public AttributeDSL attribute(String attributeName) {
            return AttributeDSL.create(element, attributeName);
        }
    }

    public abstract static class AttributeDSL {
        public static AttributeDSL create(Element element, String attributeName) {
            return new AttributeElementDSL(element, attributeName);
        }

        public abstract String toString();
    }

    public static class AttributeElementDSL extends AttributeDSL {
        private final Element element;
        private final String attributeName;

        public AttributeElementDSL(Element element, String attributeName) {
            super();

            this.element = element;
            this.attributeName = attributeName;
        }

        public String toString() {
            return element.getAttribute(attributeName);
        }
    }
}
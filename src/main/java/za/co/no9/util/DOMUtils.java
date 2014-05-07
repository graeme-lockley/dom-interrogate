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

    public static class AttributeDSL {
        private final Element element;
        private final String attributeName;
        private Optional<Object> defaultValue;

        private AttributeDSL(Element element, String attributeName) {
            super();

            this.element = element;
            this.attributeName = attributeName;
        }

        public static AttributeDSL create(Element element, String attributeName) {
            return new AttributeDSL(element, attributeName);
        }

        @Override
        public String toString() {
            Object resultObj = toObject();
            return resultObj == null ? null : resultObj.toString();
        }

        protected Object toObject() {
            if (element.hasAttribute(attributeName)) {
                return element.getAttribute(attributeName);
            } else if (defaultValue == null) {
                throw new IllegalArgumentException("Unknown attribute " + attributeName + " on node " + element.getNodeName() + ".");
            } else if (defaultValue.isPresent()) {
                return defaultValue.get();
            } else {
                return null;
            }
        }

        public AttributeDSL ifNullDefault(String defaultValue) {
            this.defaultValue = Optional.<Object>ofNullable(defaultValue);
            return this;
        }

        public AttributeIntDSL ifNullDefault(Integer defaultValue) {
            this.defaultValue = Optional.<Object>ofNullable(defaultValue);
            return new AttributeIntDSL(this);
        }
    }

    public static class AttributeIntDSL {
        private final AttributeDSL attributeDSL;

        public AttributeIntDSL(AttributeDSL attributeDSL) {
            this.attributeDSL = attributeDSL;
        }

        @Override
        public String toString() {
            Integer result = toInteger();

            return result == null ? null : result.toString();
        }

        public Integer toInteger() {
            Object resultObj = attributeDSL.toObject();

            if (resultObj == null) {
                return null;
            } else if (resultObj instanceof Integer) {
                return (Integer) resultObj;
            } else {
                return Integer.parseInt(resultObj.toString());
            }
        }
    }
}
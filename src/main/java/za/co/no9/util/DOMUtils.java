package za.co.no9.util;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DOMUtils {

    public static Document create(String xmlContent) throws IOException, ParserConfigurationException, SAXException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes())) {
            org.xml.sax.InputSource vInputSource;
            vInputSource = new org.xml.sax.InputSource(inputStream);
            return openDocument(vInputSource);
        }
    }

    private static Document openDocument(InputSource aInputSource) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder vDocumentBuilder = dbFactory.newDocumentBuilder();

        return vDocumentBuilder.parse(aInputSource);
    }
}
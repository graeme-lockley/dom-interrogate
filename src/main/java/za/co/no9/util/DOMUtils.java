package za.co.no9.util;

import org.w3c.dom.Document;
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
}
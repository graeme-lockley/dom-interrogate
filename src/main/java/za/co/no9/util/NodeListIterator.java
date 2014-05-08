package za.co.no9.util;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Iterator;

class NodeListIterator implements Iterator<Element> {
    private final NodeList nodeList;
    private int nodeListIndex;
    private Element nextElement;

    public NodeListIterator(Element element) {
        this.nodeList = element.getChildNodes();
        this.nodeListIndex = 0;

        calculateNextNodeElement();
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Element next() {
        Element result = nextElement;
        calculateNextNodeElement();
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void calculateNextNodeElement() {
        nextElement = null;
        while (nextElement == null && nodeListIndex < nodeList.getLength()) {
            org.w3c.dom.Node childNode = nodeList.item(nodeListIndex);
            if (childNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                nextElement = (Element) childNode;
            }
            nodeListIndex += 1;
        }
    }
}

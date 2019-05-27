package com.baeldung.xml.attribute;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class JaxpProcessorUnitTest {

    @Test
    void dunno() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
        String xml = this.getClass()
            .getClassLoader()
            .getResource("xml/attribute.xml")
            .getPath();

        // 1- Build the doc from the XML file
        Document doc = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(new InputSource(xml));

        System.out.println(doc.getTextContent());
        // 2- Locate the node(s) with xpath
        XPath xpath = XPathFactory.newInstance()
            .newXPath();
        NodeList nodes = (NodeList) xpath.evaluate("//*[contains(@customer, 'true')]", doc, XPathConstants.NODESET);

        // 3- Make the change on the selected nodes
        for (int idx = 0; idx < nodes.getLength(); idx++) {
            Node value = nodes.item(idx)
                .getAttributes()
                .getNamedItem("customer");
            String val = value.getNodeValue();
            value.setNodeValue(val.replaceAll("true", "false"));
        }

        // 4- Save the result to a new XML doc
        Transformer xformer = TransformerFactory.newInstance()
            .newTransformer();
        Writer output = new StringWriter();
        xformer.transform(new DOMSource(doc), new StreamResult(output));

        System.out.println(output.toString());
    }

}

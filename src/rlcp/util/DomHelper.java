package rlcp.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import rlcp.generate.GeneratingResult;

/**
 * Provides some useful methods for dom4j
 *
 * @author Eugene Efimchick
 */
public class DomHelper {

    private DomHelper() {
    }

    /**
     * Safe method for get text from node by xpath expression.
     *
     * @param node  root element
     * @param xpath - xpath expression. Should return single element only.
     * @return The resulting {@code String} or null if exception occured
     */
    public static String getTextFromNodeByXpath(Node node, String xpath) {
        try {
            return node.selectSingleNode(xpath).getText();
        } catch (RuntimeException re) {
            return null;
        }
    }

    /**
     * Safe method for get integer from node by xpath expression.
     *
     * @param node  root element
     * @param xpath - xpath expression. Should return single element only.
     * @return The resulting int or 0,  if exception not a number
     */
    public static int getIntFromNodeByXpath(Node node, String xpath) {
        try {
            return Integer.parseInt(node.selectSingleNode(xpath).getText());
        } catch (RuntimeException re) {
            return -1;
        }
    }

    /**
     * Safe method for get XML document from string.
     *
     * @param source raw string
     * @return XML document
     * @throws DocumentException if string contains a invalid XML document
     */
    public static Document toXml(String source) throws DocumentException {
        return new SAXReader().read(new StringReader(source));
    }

    /**
     * This method will print the Document to pretty String (preserving all the padding and line breaks).
     *
     * @param doc XML document
     * @return The resulting {@code String}
     * @throws IOException if there's any problem writing Document to the current Writer or closing him
     */
    public static String getPrettyOrCompactIfExc(Document doc) {
        try {
            StringWriter stringWriter = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(stringWriter, OutputFormat.createPrettyPrint());
            xmlWriter.write(doc);
            xmlWriter.close();
            return stringWriter.toString();
        } catch (IOException ex) {
            return doc.asXML();
        }
    }

    /**
     * This method will get simple unmodifiable data container {@code GeneratingResult} from XML document
     * containing RLCP-request body or RLCP-response body.
     *
     * @param rlcpRequestXml XML document
     * @return simple unmodifiable data container {@code GeneratingResult}
     * @throws NullPointerException if document the is invalid, he doesn't contain text, code or instructions
     * @see GeneratingResult
     */
    public static GeneratingResult parsePreGenearted(Document rlcpRequestXml) {
        try {
            String text = parsePreGeneratedText(rlcpRequestXml);
            String code = parsePreGeneratedCode(rlcpRequestXml);
            String instructions = parsePreGeneratedInstructions(rlcpRequestXml);
            if (text == null && code == null && instructions == null) {
                return null;
            }
            return new GeneratingResult(text, code, instructions);
        } catch (NullPointerException ex) {
            //that's ok, preGenereated is not required
            return null;
        }
    }

    private static String parsePreGeneratedCode(Document rlcpRequestXml) {
        return DomHelper.getTextFromNodeByXpath(rlcpRequestXml, Constants.xPath_selectPreGeneratedCode);
    }

    private static String parsePreGeneratedInstructions(Document rlcpRequestXml) {
        return DomHelper.getTextFromNodeByXpath(rlcpRequestXml, Constants.xPath_selectPreGeneratedInstructions);
    }

    private static String parsePreGeneratedText(Document rlcpRequestXml) {
        return DomHelper.getTextFromNodeByXpath(rlcpRequestXml, Constants.xPath_selectPreGeneratedText);
    }

    /**
     * Adds in Element node raw data container
     *
     * @param requestElement   XML element
     * @param generatingResult simple unmodifiable data container {@code GeneratingResult}
     * @see GeneratingResult
     */
    public static void addPreGeneratedElement(Element requestElement, GeneratingResult generatingResult) {
        Element preGeneratedElement = requestElement.addElement(Constants.PRE_GENERATED);
        preGeneratedElement.addElement(Constants.PRE_GENERATED_TEXT).addComment(generatingResult.getText());
        preGeneratedElement.addElement(Constants.PRE_GENERATED_CODE).addComment(generatingResult.getCode());
        preGeneratedElement.addElement(Constants.PRE_GENERATED_INSTRUCTIONS).addComment(generatingResult.getInstructions());
    }
}

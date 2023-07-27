package org.example;

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.SequenceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Date;

public class MyExtensionFunctionDefinition extends ExtensionFunctionDefinition {
    @Override
    public StructuredQName getFunctionQName() {
        return new StructuredQName(null, "http://example.org/mf", "testDOMDocumentBuilder");
    }

    @Override
    public int getMinimumNumberOfArguments() {
        return 0;
    }

    @Override
    public int getMaximumNumberOfArguments() {
        return 0;
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        return new SequenceType[]{};
    }

    @Override
    public SequenceType getResultType(SequenceType[] sequenceTypes) {
        return SequenceType.SINGLE_NODE;
    }

    @Override
    public boolean dependsOnFocus() {
        return true;
    }

    @Override
    public boolean hasSideEffects() {
        return true;
    }

    @Override
    public ExtensionFunctionCall makeCallExpression() {
        return new ExtensionFunctionCall() {
            @Override
            public Sequence call(XPathContext context, Sequence[] arguments) throws XPathException {
                Processor processor = (Processor)context.getConfiguration().getProcessor();
                DocumentBuilder documentBuilder = processor.newDocumentBuilder();
                Document document = null;
                try {
                    document = buildSomeDocument();
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                }
                XdmNode wrapped = documentBuilder.wrap(document);
                return wrapped.getUnderlyingNode();
            }
        };
    }

    private Document buildSomeDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setNamespaceAware(true);
        Document domDoc = docBuilderFactory.newDocumentBuilder().newDocument();
        Element root = domDoc.createElementNS(null, "root");
        root.setTextContent(new Date().toGMTString());
        domDoc.appendChild(root);
        return domDoc;
    }
}

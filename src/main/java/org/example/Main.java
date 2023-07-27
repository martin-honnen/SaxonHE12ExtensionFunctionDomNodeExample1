package org.example;

import net.sf.saxon.s9api.*;

import java.io.File;

public class Main {
    public static void main(String[] args) throws SaxonApiException {
        Processor processor = new Processor();

        processor.getUnderlyingConfiguration().setProcessor(processor);

        processor.registerExtensionFunction(new MyExtensionFunctionDefinition());

        XsltCompiler xsltCompiler = processor.newXsltCompiler();

        XsltExecutable xsltExecutable = xsltCompiler.compile(new File("sheet1.xsl"));

        Xslt30Transformer xslt30Transformer = xsltExecutable.load30();

        xslt30Transformer.callTemplate(null, processor.newSerializer(System.out));
    }
}
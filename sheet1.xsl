<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mf="http://example.org/mf"
                exclude-result-prefixes="#all"
                version="3.0">

    <xsl:template name="xsl:initial-template">
        <xsl:sequence select="mf:testDOMDocumentBuilder()"/>
    </xsl:template>

</xsl:stylesheet>
<xsl:stylesheet version = "1.0" 
xmlns:xsl = "http://www.w3.org/1999/XSL/Transform"> 


<xsl:template match = "/books">

    <html>
        <body>
            <h2>Book List</h2>

            <table border="1">
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Publisher</th>
                    <th>Edition</th>
                    <th>Price</th>
                </tr>


                <xsl:for-each select="book">

                    <tr>
                        <td><xsl:value-of select="title"/></td>
                        <td><xsl:value-of select="author"/></td>
                        <td><xsl:value-of select="publish"/></td>
                        <td><xsl:value-of select="edition"/></td>
                        <td><xsl:value-of select="price"/></td>
                    </tr>

                </xsl:for-each>    
            </table>    

        </body>
    </html>

</xsl:template>
</xsl:stylesheet>

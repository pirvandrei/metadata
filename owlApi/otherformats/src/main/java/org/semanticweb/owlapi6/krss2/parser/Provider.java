/* Generated By:JavaCC: Do not edit this line. Provider.java Version 7.0 */
/* JavaCCOptions:KEEP_LINE_COLUMN=true */
package org.semanticweb.owlapi6.krss2.parser;


import java.io.IOException;
@SuppressWarnings("all")
interface Provider {
    /**
     * Reads characters into an array
     * @param buffer  Destination buffer
     * @param offset   Offset at which to start storing characters
     * @param length   The maximum possible number of characters to read
     * @return The number of characters read, or -1 if all read
     * @exception  IOException
     */
    int read(char buffer[], int offset, int length) throws IOException;
    
    /**
     * Closes the stream and releases any system resources associated with
     * it.
     * @exception IOException
     */
     void close() throws IOException;
}
/* JavaCC - OriginalChecksum=70b30ac72a3864a6efdd3dd136f496ab (do not edit this line) */

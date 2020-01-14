/* Generated By:JavaCC: Do not edit this line. StreamProvider.java Version 7.0 */
/* JavaCCOptions:KEEP_LINE_COLUMN=true */
package org.semanticweb.owlapi6.functional.parser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * NOTE : This generated class can be safely deleted if installing in a GWT installation (use StringProvider instead)
 */
@SuppressWarnings("all")
class StreamProvider implements Provider {

	Reader _reader;

	public StreamProvider(Reader reader) {
		_reader = reader;
	}
	
	public StreamProvider(InputStream stream) throws IOException {
		_reader = new BufferedReader(new InputStreamReader(stream));
	}
	
	public StreamProvider(InputStream stream, String charsetName) throws IOException {
		_reader = new BufferedReader(new InputStreamReader(stream, charsetName));
	}

	@Override
	public int read(char[] buffer, int off, int len) throws IOException {
	   int result = _reader.read(buffer, off, len);

	   /* CBA -- Added 2014/03/29 -- 
	             This logic allows the generated Java code to be easily translated to C# (via sharpen) -
	             as in C# 0 represents end of file, and in Java, -1 represents end of file
	             See : http://msdn.microsoft.com/en-us/library/9kstw824(v=vs.110).aspx
	             ** Technically, this is not required for java but the overhead is extremely low compared to the code generation benefits.
	   */
	   
	   if (result == 0) {
	      if (off < buffer.length && len > 0) {
	        result = -1;
	      }
	   }
	   
		return result;
	}

	@Override
	public void close() throws IOException {
		_reader.close();
	}

}

/* JavaCC - OriginalChecksum=eae5dc0b6526d49f03c498a109c9e145 (do not edit this line) */

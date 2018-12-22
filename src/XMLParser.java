
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLParser {
    public XMLParser(){

    }

    public void parse(String url) {
        XMLReader myReader = null;
        try {
            myReader = XMLReaderFactory.createXMLReader();
            myReader.parse(new InputSource(new URL(url).openStream()));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        XMLParser test = new XMLParser();
        test.parse("Http//sr.se/api/v2/channels");
    }
}

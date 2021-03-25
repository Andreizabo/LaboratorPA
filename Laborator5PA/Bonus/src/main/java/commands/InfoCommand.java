
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InfoCommand implements Command {
    private int index;

    public InfoCommand(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: Info about " + catalog.getCatalogItem(index));
        try {
            File file = new File(catalog.getCatalogItem(index).getPath());

            //Parser method parameters
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputStream = new FileInputStream(file);
            ParseContext context = new ParseContext();

            parser.parse(inputStream, handler, metadata, context);
            System.out.println(handler.toString());

            //getting the list of all meta data elements
            String[] metadataNames = metadata.names();
            System.out.print("\n" + catalog.getCatalogItem(index).getName() + " info:\n");
            for(String name : metadataNames) {
                System.out.println(name + ": " + metadata.get(name));
            }
            System.out.print("\n");
        }
        catch (TikaException e) {
            CommandLogger.logResult(logIndex, "Tika Exception", false);
            throw new MyException("Couldn't get info, Tika exception");
        } catch (IOException e) {
            CommandLogger.logResult(logIndex, "IO Exception", false);
            throw new MyException("Couldn't get info, IO exception");
        } catch (SAXException e) {
            CommandLogger.logResult(logIndex, "SAX Exception", false);
            throw new MyException("Couldn't get info, SAX exception");
        }
        CommandLogger.logResult(logIndex, "Success", true);
    }

    @Override
    public void runWithoutLogger(Catalog catalog) throws MyException {
        try {
            File file = new File(catalog.getCatalogItem(index).getPath());

            //Parser method parameters
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream inputstream = new FileInputStream(file);
            ParseContext context = new ParseContext();

            parser.parse(inputstream, handler, metadata, context);
            System.out.println(handler.toString());

            //getting the list of all meta data elements
            String[] metadataNames = metadata.names();
            System.out.print("\n" + catalog.getCatalogItem(index).getName() + " info:\n");
            for(String name : metadataNames) {
                System.out.println(name + ": " + metadata.get(name));
            }
            System.out.print("\n");
        }
         catch (TikaException e) {
            throw new MyException("Couldn't get info, Tika exception");
        } catch (IOException e) {
            throw new MyException("Couldn't get info, IO exception");
        } catch (SAXException e) {
            throw new MyException("Couldn't get info, SAX exception");
        }
    }
}

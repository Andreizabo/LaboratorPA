import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

public class ReportCommand implements Command{
    @Override
    public void run(Catalog catalog) {
        int logIndex = CommandLogger.log("Command: Make HTML file from catalog " + catalog.getName());

        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("freemarker/"));

            Template template = cfg.getTemplate("catalog.ftlh");

            Writer out = new OutputStreamWriter(new FileOutputStream("freemarker/" + catalog.getName() + ".html"));
            template.process(catalog, out);
        }
        catch (TemplateException te) {
            CommandLogger.logResult(logIndex, "Template exception", false);
            throw new MyException("Couldn't generate html, template exception");
        }
        catch (IOException io) {
            CommandLogger.logResult(logIndex, "IO exception", false);
            throw new MyException("Couldn't generate html, io exception");
        }

        CommandLogger.logResult(logIndex, "Success", true);
    }

    @Override
    public void runWithoutLogger(Catalog catalog) throws MyException {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(new File("freemarker/"));

            Template template = cfg.getTemplate("catalog.ftlh");

            Writer out = new OutputStreamWriter(new FileOutputStream("freemarker/" + catalog.getName() + ".html"));
            template.process(catalog, out);
        }
        catch (TemplateException te) {
            throw new MyException("Couldn't generate html, template exception");
        }
        catch (IOException io) {
            throw new MyException("Couldn't generate html, io exception");
        }
    }
}

package compulsory;
import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Main
{
    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException
    {
        var classLoader = new MyClassLoader();

        File path = new File(args[0]);
        URL url = path.toURI().toURL();
        classLoader.addURL(url);

        System.out.println("Methods:");
        for (Method m : Class.forName(args[1]).getMethods())
        {
            System.out.printf("%s %s(",m.getReturnType(), m.getName());
            for (var parameter : m.getParameterTypes())
                System.out.printf("%s, ", parameter.getName());
            System.out.println(")");
        }

        System.out.println();
        System.out.println("Test methods");
        for (Method m : Class.forName(args[1]).getMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                try
                {
                    m.invoke(null);
                }
                catch (Throwable ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}

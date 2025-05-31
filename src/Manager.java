import java.io.File;

public class Manager {



    public static LogSource getSourceFile(File file){
       return new FileLogSource(file);
    }

}

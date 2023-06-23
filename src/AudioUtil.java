import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AudioUtil {
    //Singleton
    private static AudioUtil ourInstance = new AudioUtil();

    public static AudioUtil getInstance(){
        return ourInstance;
    }

    public AudioUtil(){

    }

    public URL transform(File audioFile) throws MalformedURLException {
        if(audioFile.canRead()){
            // Uniform Resource Locator
            return audioFile.toURI().toURL();
        }
        throw new IllegalArgumentException();
    }
}

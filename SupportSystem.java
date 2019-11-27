import java.util.HashSet;
import java.io.*;
import java.nio.file.*;
import java.net.URL;
import java.net.URISyntaxException;

/**
 * This class implements a technical support system.
 * It is the top level class in this project.
 * The support system communicates via text input/output
 * in the text terminal.
 * 
 * This class uses an object of class InputReader to read input
 * from the user, and an object of class Responder to generate responses.
 * It contains a loop that repeatedly reads input and generates
 * output until the users wants to leave.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2016.02.29
 */
public class SupportSystem
{
    private InputReader reader;
    private Responder responder;
    
    /**
     * Creates a technical support system.
     */
    public SupportSystem()
    {
        reader = new InputReader();
        //reader = readFromFile("Techsupport.bin");
        responder = new Responder();
    }

    /**
     * Start the technical support system. This will print a welcome message and enter
     * into a dialog with the user, until the user ends the dialog.
     */
    public void start()
    {
        boolean finished = false;

        printWelcome();

        while(!finished) {
            HashSet<String> input = reader.getInput();

            if(input.contains("bye")) {
                finished = true;
            }
            else {
                String response = responder.generateResponse(input);
                System.out.println(response);
            }
        }
        printGoodbye();
    }

    /**
     * Print a welcome message to the screen.
     */
    private void printWelcome()
    {
        System.out.println("Welcome to the DodgySoft Technical Support System.");
        System.out.println();
        System.out.println("Please tell us about your problem.");
        System.out.println("We will assist you with any problem you might have.");
        System.out.println("Please type 'bye' to exit our system.");
    }

    /**
     * Print a good-bye message to the screen.
     */
    private void printGoodbye()
    {
        System.out.println("Nice talking to you. Bye...");
    }
    
    /**
     * Save a binary version of the inputReader to the given file.
     * If the file name is not an absolute path, then it is assumed
     * to be relative to the current project folder.
     * @param destinationFile The file where the details are to be saved.
     * @throws IOException If the saving process fails for any reason.
     */
    public void saveToFile(String destinationFile) throws IOException
    {
        Path destination = Paths.get(destinationFile).toAbsolutePath();
        ObjectOutputStream os = new ObjectOutputStream(
                                    new FileOutputStream(destination.toString()));
        os.writeObject(reader);
        os.close();
    }
    
    /**
     * Read the binary version of the InputReader from the given file.
     * If the file name is not an absolute path, then it is assumed
     * to be relative to the current project folder.
     * @param sourceFile The file from where the details are to be read.
     * @return The address book object.
     * @throws IOException If the reading process fails for any reason.
     */
    public InputReader readFromFile(String sourceFile) throws IOException, ClassNotFoundException
    {
        URL resource = getClass().getResource(sourceFile);
        if(resource == null) {
            throw new FileNotFoundException(sourceFile);
        }
        try {
            File source = new File(resource.toURI());
            ObjectInputStream is = new ObjectInputStream(
                                        new FileInputStream(source));
            InputReader savedReader = (InputReader) is.readObject();
            is.close();
            return savedReader;
        }
        catch(URISyntaxException e) {
            throw new IOException("Unable to make a valid filename for " + sourceFile);
        }
    }
}

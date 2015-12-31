import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
 
/*
 *
 */
public class SocketClient extends Thread
{
        //////////////NETWORKING STUFF
        String serverName; // name of the server/host we're going to connect to
        int port; // The port we're going to connect to, on the given server
        String name; // IRC username
        String channel;
       
        Socket client; // The socket
        BufferedWriter out;
        BufferedReader in;
       
        PingThread ping;
 
        /*
         * initPingThread
         */
        public void initPingThread(PingThread ping)
        {
                this.ping = ping;
        }
       
        /*
         * hookupShit()
         * Used by: this
         * Used to: tell the IRC server, our name, our client ID, our nickname and which channel
           we want to connect to
         * Short: Pinky dinky shit, used by the IRC client.
         */
        public void hookupShit() throws IOException, InterruptedException
        {
                System.out.println("hookupShit() - SocketClient.java");
               
                name = InetAddress.getLocalHost().getHostName();
                channel = "#kykkeliky";
               
                System.out.println("J00r name iz: " + name);
                out.write("USER " + name + " whatever.pc" + serverName + " Ken Thompson \r\n");
                out.write("NICK " + " MadonnaDick" + "\r\n");
                out.write("JOIN " + channel + "\r\n");
                out.flush();
        }
       
        /*
         * init()
         * Used by: this
         * Used to: Init all our shit
         * Short: Inits the Socket, connects it to the freenode irc server, then bets the streams
           up and running
         *
         */
        public void init() throws UnknownHostException, IOException
        {              
                System.out.println("init() - SocketClient.java");
                serverName = "irc.freenode.net";
                port = 6665;
               
                ///////////Init of networking stuff like socket and streams /////////
                System.out.println("Inits networking stuff");
                //      //// Socket
                System.out.println("Tries to connect to server at: " + serverName + " " + port);
                client = new Socket(serverName, port);
                System.out.println("Connection established (with " + client.getRemoteSocketAddress() + ")");
               
                out = new BufferedWriter( new OutputStreamWriter( client.getOutputStream() ) );
                in = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
        }
       
        /*
         * shortenInput()
         */
        public String shortenInput(String input)
        {
                System.out.println("shortenInput(String input) - SocketClient.java");
                String msg = "";
                if(input.contains("PRIVMSG"))
                {
                        int temp = input.indexOf(channel + " :");
                        temp += channel.length();
                        temp += 2;
                        if(temp > 0)
                        {
                                msg = input.substring(temp);
                        }
                }
                return msg;
        }
       
        /*
         * sendMessage(String msg)
         */
        public void sendMessage(String msg) throws IOException, InterruptedException
        {
                System.out.println("sendMessage(String msg) - SocketClient.java");
                out.flush();
                out.write("/msg " + name + " " + msg + "\r\n");
                System.out.println("/msg " + name + " " + msg + "\r\n");
                out.flush();
                 
                  String whatever = in.readLine();
                  System.out.println(whatever);
        }
       
        /*
         * run() // from the Thread class
         * Used by: this automaticly
         * Used for: getting input from the IRC channel, translating commands from the IRC channel and giving output if needed
         */
        public void run()
        {
                System.out.println("run() - SocketClient.java");
                Rat rat = new Rat(this);
               
                try
                {
                        init();
                        hookupShit();
                } catch (Exception e) {e.printStackTrace();}
               
               
                String inputString; // input from server in the string
               
                while(true)
                {                      
                        try
                        {
                               
                                inputString = shortenInput(in.readLine());
                                System.out.println(inputString);
                                rat.findCommand(inputString);
                               
                                // Lol, I iz 1337
                        } catch(Exception e) {System.out.println("Fak sumthin went wrung"); e.printStackTrace(); System.exit(0);}
                       
                       
                }
        }
}

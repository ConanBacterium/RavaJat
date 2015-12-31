import java.io.IOException;
import java.net.UnknownHostException;
 
 
public class Main
{
 
        public static void main(String[] args)  throws UnknownHostException, IOException
        {
                SocketClient sc = new SocketClient();
                Thread scThread = sc;
                scThread.start();
                PingThread ping = new PingThread(sc);
                Thread pingThread = ping;
                sc.initPingThread(ping);
               
               
                long time = System.currentTimeMillis();
                long newtime, temp;
                boolean yes = true;
                while(yes == true)
                {
                        newtime = System.currentTimeMillis();
                        temp = newtime - time;
                        if(temp < 100000)
                        {
                                System.out.println("starts pinging");
                                pingThread.start();
                                System.out.println("pinged");
                               
                                yes = false;
                        }
                }
        }
 
}

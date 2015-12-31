public class PingThread extends Thread
{
        SocketClient sc;
       
        public PingThread(SocketClient sc)
        {
                this.sc = sc;
        }
       
        /*
         * run()
         */
        public void run()
        {
                System.out.println("startPinging() - SocketClient.java");
               
                while(true)
                {
                        try
                        {
                                Thread.sleep(100000);
                                sc.out.write("PING");
                                sc.out.flush();
                                sc.out.flush();
                                System.out.println("boom");
                        } catch (Exception e) {System.out.println("FAILED PINGIN!!!!!!!");}
                }
        }
}

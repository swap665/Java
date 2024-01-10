
import java.io.*;
import java.net.*;
class Server{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Server(){
        try{
        server=new ServerSocket(7777);
        System.out.println("server is ready to accept connection");
        socket =server.accept();
        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream());
        startReading();
        startWriting();
    }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public void startReading(){

        Runnable r1=()->{
            System.out.println("reader started..");
            try{
            while(true){
                

                String msg=br.readLine();
                if(msg.equals("exist")){
                    System.err.println("terminated");
                    socket.close();
                    break;
                };
                System.err.println("Client : "+msg);
        
            }
            }
            catch(Exception e){
                System.out.println("Closed");
        }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
         Runnable r2=()->{
            try{
            while(true && !socket.isClosed()){
                
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();
               
            }
        }
        catch(Exception e){
            System.out.println("Closed reading");
        }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Server is running");
        new Server();
    }
}
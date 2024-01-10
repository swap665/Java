import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
     BufferedReader br;
    PrintWriter out;
    public Client(){
        try{
            System.out.println("hello");
            socket=new Socket("127.0.0.1",7777);
             br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream());
        startReading();
        startWriting();
            
        }
        catch(Exception e){

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
                System.err.println("Server : "+msg);
            
          
            };
        }
          catch(Exception e){
                 System.out.println("Closed reading");
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
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                   
            }
        }  catch(Exception e){
                    System.out.println("Closed");
                }
            
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Client is running");
        new Client();
        
    }
    
}

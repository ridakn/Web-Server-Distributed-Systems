import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.net.*;

public class MyServer {

    public static void main(String args[]) throws IOException {

        try {

          Integer.parseInt(args[3]);

        }

        catch (NumberFormatException e) {

            System.out.println("Invalid arguments. Please add the following: '-document_root /path -port number'");

        }

        if ( Integer.parseInt(args[3]) < 8000 || Integer.parseInt(args[3]) > 9999) {

            System.out.println("This is an invalid port number.");
            System.exit(0);
        }

        ServerSocket listener_socket = new ServerSocket(Integer.parseInt(args[3]));
        System.out.println("Listening on port " + Integer.parseInt(args[3]) + "...");
        String root_path = args[1];

        //Infinite loop for processing the HTTP request
        while (true) {

            //Socket listens for incoming messages, accepts and makes a connection,
            // then thread is started to handle the request.

            Socket connection = listener_socket.accept();
            HTTPRequest request = new HTTPRequest(connection, root_path);
            Thread thread = new Thread(request);
            thread.start();
        }
    }
}

class HTTPRequest implements Runnable {

    Socket socket;
    String path;

    public HTTPRequest(Socket socket, String path) throws IOException {

        this.socket = socket;
        this.path = path;
    }

    public void run() {

        try {

            requestHandler();
            System.out.println(socket);

        }

        catch (Exception e) {

            System.out.println(e);
        }
    }

    private void requestHandler() throws Exception {

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream outputStream = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));

        //We get the request from command line
        String request = inputStream.readLine();
        System.out.println("Request: " + request);
	 
        String some_file = path;
        StringTokenizer tokenizer = new StringTokenizer(request);

        try {

            if (tokenizer.hasMoreElements() && tokenizer.nextToken().equalsIgnoreCase("GET") && tokenizer.hasMoreElements())
                some_file += tokenizer.nextToken();
            else
                throw new FileNotFoundException();

           if (some_file.endsWith("/"))
               some_file += "index.html";

           if( !(new File(some_file).canRead()) && (new File(some_file).exists())) {

               outputStream.print("HTTP/1.0 403 Forbidden\r\n");
               outputStream.close();
               return;
           }

            if (some_file.indexOf("..") >= 0 || some_file.indexOf(':') >= 0 || some_file.indexOf('|') >= 0) {

                outputStream.print("HTTP/1.0 400 Bad Request\r\n");
                outputStream.close();
                return;
            }

           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            FileInputStream fstream = new FileInputStream(some_file);

           String content="text/plain";

           if (some_file.endsWith(".html") || some_file.endsWith(".htm"))
               content = "text/html";

           else if (some_file.endsWith(".gif"))
               content = "image/gif";

           else if (some_file.endsWith(".jpg") || some_file.endsWith(".jpeg"))
             content = "image/jpeg";

           else if (some_file.endsWith(".class"))
             content = "application/octet-stream";

            System.out.println("HTTP/1.0 200 OK\r\n" + "Content-type: " + content + "\r\n" +
                    "Content-length: " + fstream.getChannel().size() + "\r\n" +
                    "Date: " + formatter.format(date) + "\r\n\r\n");

            outputStream.print("HTTP/1.0 200 OK\r\n" + "Content-type: " + content + "\r\n" +
                    "Content-length: " + fstream.getChannel().size() + "\r\n" +
                    "Date: " + formatter.format(date) + "\r\n\r\n");

           int some_i;
           byte buffer[] = new byte[4096];

            while ((some_i = fstream.read(buffer)) > 0)
                outputStream.write(buffer, 0, some_i);
            outputStream.close();
            socket.close();

      }
	  
      catch (FileNotFoundException fnfe) {

          outputStream.print("HTTP/1.0 404 Not Found\r\n"+
            "Content-type: text/html\r\n\r\n"+
            "<html><head></head><body>HTTP/1.0 404 Not Found<br>" + some_file + " not found</body></html>\n");
          outputStream.close();
          inputStream.close();
          socket.close();

      }
    }
}

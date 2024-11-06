package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
  private static final String HOST = "0.0.0.0";
  private static final int PORT = 228;
  protected static Set<ClientHandler> clientHandlers = new HashSet<>();

  public static void main(String[] args) {
    try(ServerSocket serverSocket = new ServerSocket()) {
      InetAddress inetAddress = InetAddress.getByName(HOST);
      serverSocket.bind(new InetSocketAddress(inetAddress, PORT));
      System.out.println("SERVER STARTED - \nIP: "+inetAddress.getHostAddress()+ '\t' +" PORT: "+ PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New User: " + clientSocket.getInetAddress());

        ClientHandler clientHandler = new ClientHandler(clientSocket);
        clientHandlers.add(clientHandler);
        new Thread(clientHandler).start();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void broadcastMessage(String message) {
    for(ClientHandler clientHandler : clientHandlers) {
      clientHandler.sendMessage(message);
    }
  }
}

class ClientHandler implements Runnable {
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private String username;

  ClientHandler(Socket clientSocket) {
    this.socket = clientSocket;
    try {
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    try {
      username = in.readLine();

      String message;
      while ((message = in.readLine()) != null) {
        ChatServer.broadcastMessage(username +':'+ message);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        if(socket != null) {
          socket.close();
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      ChatServer.clientHandlers.remove(this);
      System.out.println(username + " left.!");
    }
  }

  public void sendMessage(String userMessage) {
    out.println(userMessage);
  }
}

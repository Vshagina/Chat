package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Сервер запущен. Ожидание подключения клиента...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен.");
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            byte[] buffer = new byte[1024];
            Scanner scanner = new Scanner(System.in);
            Thread inputThread = new Thread(() -> {
                while (true) {
                    try {
                        // принятие сообщения от клиента
                        int bytesRead = inputStream.read(buffer);
                        if (bytesRead == -1) {
                            break;
                        }

                        // выводим сообщение от клиента
                        String clientMessage = new String(buffer, 0, bytesRead);
                        System.out.println("Клиент: " + clientMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            });
            inputThread.start();

            // отправка сообщения клиенту
            while (true) {
                String message = scanner.nextLine();
                byte[] messageBytes = message.getBytes();
                outputStream.write(messageBytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

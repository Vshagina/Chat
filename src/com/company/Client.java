package com.company;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Подключено к серверу.");
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            byte[] buffer = new byte[1024];
            Scanner scanner = new Scanner(System.in);
            Thread inputThread = new Thread(() -> {
                while (true) {
                    try {
                        // чтение сообщения от сервера
                        int bytesRead = inputStream.read(buffer);
                        if (bytesRead == -1) {
                            break;
                        }
                        // вывод сообщения от сервера
                        String serverMessage = new String(buffer, 0, bytesRead);
                        System.out.println("Сервер: " + serverMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            });
            inputThread.start();

            // отправка сообщения серверу
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
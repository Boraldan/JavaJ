package ru.geekbrains.chat.server;


import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ClientManager implements Runnable {

    private Socket socket;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String name;

    public static ArrayList<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            name = bufferedReader.readLine();
            clients.add(this);
            System.out.println(name + " подключился к чату.");
            broadcastMessage("Server: " + name + " подключился к чату.");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    /**
     * Завершение работы всех потоков, закрытие соединения с клиентским сокетом,
     * удаление клиентского сокета из коллекции
     *
     * @param socket         клиентский сокет
     * @param bufferedReader буфер для чтения данных
     * @param bufferedWriter буфер для отправки данных
     */
    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Удаление клиента из коллекции
        removeClient();
        try {
            // Завершаем работу буфера на чтение данных
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            // Завершаем работу буфера для записи данных
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            // Закрытие соединения с клиентским сокетом
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление клиента из коллекции
     */
    private void removeClient() {
        clients.remove(this);
        System.out.println(name + " покинул чат.");
    }

    @Override
    public void run() {
        String massageFromClient;

        // Цикл чтения данных от клиента
        while (socket.isConnected()) {
            try {
                // Чтение данных
                massageFromClient = bufferedReader.readLine();
                //TODO  вставлена логика личных сообщений
                if (massageFromClient.contains(": $")) {
                    if (!privateMessage(massageFromClient)) {
                        this.bufferedWriter.write("Такого персонажа в чате нет.");
                        this.bufferedWriter.newLine();
                        this.bufferedWriter.flush();
                    }
                } else {
                    broadcastMessage(massageFromClient);
                }

            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    /**
     * Отправка сообщения всем слушателям
     *
     * @param massage сообщение
     */
    private void broadcastMessage(String massage) {
        for (ClientManager client : clients) {
            try {
                // Если клиент не равен по наименованию клиенту-отправителю,
                // отправим сообщение
                if (!client.name.equals(name)) {
                    client.bufferedWriter.write(massage);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private boolean privateMessage(String message) throws IOException {
        List<String> mesList = Arrays.stream(message.split(" ")).toList();

        String findName = mesList.stream().filter(el -> el.contains("$")).toList().get(0).replace("$", "");

        if (findName.equals(this.name)) {
            this.bufferedWriter.write("Ты общаешься сам с собой (: ");
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
            return true;
        }

        for (ClientManager client : clients) {
            if ((client.getName()).equals(findName)) {
                String mesNew = message.replace("$" + findName, "private " + findName + ": ");
                client.bufferedWriter.write(mesNew);
                client.bufferedWriter.newLine();
                client.bufferedWriter.flush();
                return true;
            }
        }

        return false;
    }
}

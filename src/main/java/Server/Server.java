package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    public void start() {
        while (true) {
            try (DatagramSocket datagramSocket = new DatagramSocket(22223))//открывается подключение, нужно выбрать порт, на который отправляет клиент.
            {
                byte[] buffer = new byte[1024];
                DatagramPacket datagramPacketIN = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacketIN);
                String answerTime ="Время получения сообщения " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
                System.out.println(answerTime);
                System.out.println(new String(buffer, "UTF-8").trim());
                System.out.println("Адрес отправителя" + datagramPacketIN.getSocketAddress());

                 // начался ответ
                DatagramPacket datagramPacket = new DatagramPacket(answerTime.getBytes(),answerTime.getBytes().length); //сообщение в буфер
                datagramPacket.setAddress(datagramPacketIN.getAddress());
                datagramPacket.setPort(datagramPacketIN.getPort());
                datagramSocket.send(datagramPacket);


            } catch (IOException e) {
                System.out.println("Что-то с клиентом");
                e.printStackTrace();
            }
        }
    }
}
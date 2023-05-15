package processing.library.lpd;

import processing.core.*;
import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class Printer {
    String printer_ip;
    String queue;

    Printer(String printer_ip, String queue) {
        this.printer_ip = printer_ip;
        this.queue = queue;
    }

    void printImage(PImage snapshot) {
        try {
            // Convert the PImage to a BufferedImage
            BufferedImage img = new BufferedImage(snapshot.width, snapshot.height, BufferedImage.TYPE_INT_ARGB);
            snapshot.loadPixels();
            img.setRGB(0, 0, snapshot.width, snapshot.height, snapshot.pixels, 0, snapshot.width);
            // Create a new socket and connect to the printer
            Socket socket = new Socket(printer_ip, 515);
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            // Send the control file
            out.println("\u0002" + queue);

            // Send the BufferedImage data to the printer
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            baos.flush();
            byte[] buffer = baos.toByteArray();
            out.write(new String(buffer, 0, buffer.length));
            baos.close();

            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import processing.library.lpd.*;

Printer printer;

void setup() {
    size(600, 600);
    printer = new Printer("192.168.1.100", "queue");
}

void draw() {
    sample.drawCircle(100, 100, 50);

    if (keyPressed) {
        if (keyCode == ENTER) {
            PImage snapshot = get();
            printer.printImage(snapshot);
        }
    }
}
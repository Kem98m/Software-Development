module simulation.team04 {
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;
    requires org.junit.jupiter.api;
    requires opencsv;
    requires jdk.xml.dom;
    requires java.logging;

    exports backend;
    exports frontend;
    exports testing;
    exports util;
}
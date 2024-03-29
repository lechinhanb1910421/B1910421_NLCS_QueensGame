package com.everett.controllers;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MyIcon {
        public static ImageIcon QUEEN = null;
        public static ImageIcon CHEEMS = null;
        public static ImageIcon GANYU = null;
        public static ImageIcon SUN = null;
        static {
                try {
                        QUEEN = new ImageIcon(ImageIO
                                        .read(new File("src/main/resources/pictures/queen.png")));
                        CHEEMS = new ImageIcon(ImageIO
                                        .read(new File("src/main/resources/pictures/cheems.png")));
                        GANYU = new ImageIcon(ImageIO
                                        .read(new File("src/main/resources/pictures/ganyu.png")));
                        SUN = new ImageIcon(ImageIO
                                        .read(new File("src/main/resources/pictures/sun.png")));
                } catch (IOException e) {
                }

        }
}

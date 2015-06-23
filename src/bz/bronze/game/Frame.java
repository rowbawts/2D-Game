package bz.bronze.game;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class Frame extends JFrame {

   private static final long serialVersionUID = 407032542084353623L;
   public static int fWidth = 790;
   public static int fHeight = 580;
   public static int lWidth = fWidth;
   public static int lHeight = fHeight - 20;
   public static String gameKind = "Alpha";
   public static String gameVersion = "0.0.2";
   public static String gameName = "Game";
   public static String title = "The birth of a new world";


   public Frame() {
      Image icon = Toolkit.getDefaultToolkit().getImage("res/gameicon.png");
      this.setSize(fWidth, fHeight);
      this.setResizable(false);
      this.setDefaultCloseOperation(3);
      this.setIconImage(icon);
      this.setLocationRelativeTo((Component)null);
      this.setTitle(gameName + " - " + gameKind + " " + gameVersion + " - \"" + title + "\"");
      this.init();
   }

   public void init() {
      Game screen = new Game(this);
      this.setLayout(new GridLayout(1, 1, 0, 0));
      this.add(screen);
      this.setVisible(true);
   }

   public static void main(String[] args) {
      new Frame();
   }
}

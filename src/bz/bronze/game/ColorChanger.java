package bz.bronze.game;

import java.awt.Color;
import java.util.Random;

public class ColorChanger {

   public void changeColor(int kind, boolean isTouch, int kind2, boolean isWater) {
      if(!isWater) {
         if(!isTouch) {
            if(kind == 2) {
               Game.destroyTime.changedColor = new Color(51, 134, 43);
            } else if(kind == 3) {
               Game.destroyTime.changedColor = new Color(248, 228, 190);
            } else if(kind == 0) {
               Game.destroyTime.changedColor = new Color(78, 52, 33);
            } else if(kind == 9) {
               Game.destroyTime.changedColor = new Color(69, 52, 30);
            } else if(kind == 4) {
               Game.destroyTime.changedColor = new Color(79, 79, 79);
            } else if(kind == 7) {
               Game.destroyTime.changedColor = new Color(52, 52, 52);
            } else if(kind == 6) {
               Game.destroyTime.changedColor = new Color(119, 79, 50);
            }
         } else if(kind2 == 1) {
            Game.destroyTime.changedColor = new Color(79, 79, 79);
         } else if(kind2 == 14) {
            Game.destroyTime.changedColor = new Color(78, 52, 33);
         } else if(kind2 == 40) {
            Game.destroyTime.changedColor = new Color(223, 205, 171);
         } else if(kind2 == 0) {
            Game.destroyTime.changedColor = new Color(43, 114, 30);
         } else if(kind2 != 53 && kind2 != 54 && kind2 != 55) {
            if(kind2 == 14) {
               Game.destroyTime.changedColor = new Color(78, 79, 50);
            } else if(kind2 == 58) {
               Game.destroyTime.changedColor = new Color(43, 114, 30);
            } else if(kind2 == 57) {
               Game.destroyTime.changedColor = new Color(255, 50, 50);
            } else if(kind2 == 59) {
               Game.destroyTime.changedColor = new Color(150, 150, 40);
            } else if(kind2 == 27) {
               Game.destroyTime.changedColor = new Color(69, 52, 30);
            }
         } else {
            Game.destroyTime.changedColor = new Color(51, 134, 43);
         }
      } else if((new Random()).nextInt(100) < 33) {
         Game.destroyTime.changedColor = new Color(6, 199, 240);
      } else if((new Random()).nextInt(100) < 55) {
         Game.destroyTime.changedColor = new Color(0, 252, 249);
      } else {
         Game.destroyTime.changedColor = new Color(255, 255, 255);
      }

   }
}

package bz.bronze.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Splash {

   public int fadeColor = 0;
   public int beforeTime = 175;
   public int beforeFrame = 0;
   public int fadeSpeed = 1;
   public int fadeFrame = 0;


   public void takeTime() {
      if(this.beforeFrame >= this.beforeTime) {
         if(this.fadeFrame >= this.fadeSpeed) {
            if(this.fadeColor < 255) {
               ++this.fadeColor;
            } else if(this.fadeColor != 255) {
               this.fadeColor = 255;
            } else {
               Game.inSplash = false;
            }

            this.fadeFrame = 0;
         } else {
            ++this.fadeFrame;
         }
      } else {
         ++this.beforeFrame;
      }

   }

   public void drawSplash(Graphics g) {
      g.drawImage(Game.scheme_random[0], 0, 0, Frame.lWidth, Frame.lHeight, (ImageObserver)null);
      if(this.fadeColor > 0) {
         g.setColor(new Color(0, 0, 0, this.fadeColor));
         g.fillRect(0, 0, Frame.lWidth, Frame.lHeight);
      }

   }
}

package bz.bronze.game;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Bat extends Mob {

   private static final long serialVersionUID = 4444969031891080888L;


   public Bat() {
      this.mWidth = 48;
      this.mHeight = 48;
      this.animationLength = 1;
      this.enemy = true;
   }

   public void physic() {
      this.movement();
   }

   public void movement() {
      if(this.movingFrame >= this.movingSpeed) {
         if(this.direction != this.dSt) {
            if(this.direction == this.dUp) {
               --this.y;
               this.facing = this.upward;
               this.sDir = true;
            }

            if(this.direction == this.dDo) {
               ++this.y;
               this.facing = this.downward;
               this.sDir = true;
            }

            if(this.direction == this.dLe) {
               --this.x;
               this.sDir = true;
            }

            if(this.direction == this.dRi) {
               ++this.x;
               this.sDir = true;
            }
         }

         if(this.animationFrame >= this.animationSpeed) {
            if(this.animation < this.animationLength) {
               ++this.animation;
            } else {
               this.animation = 0;
            }

            this.animationFrame = 0;
         } else {
            ++this.animationFrame;
         }

         this.movingFrame = 0;
      } else {
         ++this.movingFrame;
      }

      if(this.extraFrame >= this.extraTime) {
         this.extraTime = (new Random()).nextInt(100) + 100;
         byte stillStandPer = 20;
         int ra = (new Random()).nextInt(100 + stillStandPer);
         if(ra < 25) {
            this.direction = this.dUp;
         } else if(ra >= 25 && ra < 50) {
            this.direction = this.dDo;
         } else if(ra >= 50 && ra < 75) {
            this.direction = this.dLe;
         } else if(ra >= 75 && ra < 100) {
            this.direction = this.dRi;
         } else {
            this.direction = this.dSt;
         }

         this.extraFrame = 0;
      } else {
         ++this.extraFrame;
      }

   }

   public void drawMob(Graphics g) {
      g.drawImage(Game.scheme_bat[this.facing + this.animation], this.x - Game.sX, this.y - Game.sY, this.mWidth, this.mHeight, (ImageObserver)null);
   }
}

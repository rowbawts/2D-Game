package bz.bronze.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Particle extends Rectangle {

   private static final long serialVersionUID = 261533784448249567L;
   public Color particleColor;
   public boolean inGame = false;
   public int frame = 0;
   public int time = 100;
   public int aboveGround = 20;
   public int jumpingLeft = 20;
   public int sideFalling = 5;
   public int direction = 0;
   public int count;
   public int left = 0;
   public int right = 1;


   public void newParticle(Color particleColor, Rectangle location) {
      this.particleColor = particleColor;
      this.setBounds(location);
      this.inGame = true;
      this.jumpingLeft = (new Random()).nextInt(28) + 4;
      this.aboveGround = (new Random()).nextInt(28) + 4;
      this.sideFalling = (new Random()).nextInt(10) + 4;
      this.count = this.aboveGround;
      if((new Random()).nextInt(100) > 50) {
         this.direction = this.left;
      } else {
         this.direction = this.right;
      }

   }

   public void physics() {
      if(this.aboveGround == 0 && this.jumpingLeft == 0) {
         if(this.frame >= this.time) {
            this.deleteParticle();
            this.frame = 0;
         } else {
            ++this.frame;
         }
      } else if(this.jumpingLeft > 0) {
         --this.jumpingLeft;
         ++this.aboveGround;
         --this.y;
         if(this.sideFalling > 0) {
            --this.sideFalling;
            if(this.direction == this.left) {
               --this.x;
            } else {
               ++this.x;
            }
         }
      } else {
         if(this.aboveGround == this.count) {
            this.sideFalling = (new Random()).nextInt(10) + 4;
         }

         if(this.aboveGround > 0) {
            --this.aboveGround;
            ++this.y;
            if(this.sideFalling > 0) {
               --this.sideFalling;
               if(this.direction == this.left) {
                  --this.x;
               } else {
                  ++this.x;
               }
            }
         }
      }

   }

   public void deleteParticle() {
      this.inGame = false;
   }

   public void drawParticle(Graphics g) {
      g.setColor(this.particleColor);
      g.fillRect(this.x - Game.sX, this.y - Game.sY, this.width, this.height);
   }
}

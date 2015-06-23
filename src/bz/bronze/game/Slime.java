package bz.bronze.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Slime extends Mob {

   private static final long serialVersionUID = 7532633236829235909L;


   public Slime() {
      this.mWidth = 48;
      this.mHeight = 60;
      this.animationLength = 9;
      this.enemy = true;
      this.attacking = false;
      this.attackAway = 300;
      this.mobAttack = new Rectangle(this.x - this.attackAway, this.y - this.attackAway, this.width + this.attackAway * 2, this.height + this.attackAway * 2);
   }

   public boolean checkCollision(Point pt1, Point pt2) {
      return false;
   }

   public void movement() {
      int ra;
      if(this.movingFrame >= this.movingSpeed) {
         if(this.attacking) {
            if(Game.character.x < this.x) {
               --this.x;
            } else if(Game.character.x > this.x) {
               ++this.x;
            }

            if(Game.character.y < this.y) {
               --this.y;
            } else if(Game.character.y > this.y) {
               ++this.y;
            }
         } else if(this.direction != this.dSt) {
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
               int stillStandPer = 0;

               for(ra = 0; ra < (new Random()).nextInt(this.splash.length - 6) + 5; ++ra) {
                  ++stillStandPer;
                  if(!Game.particle[stillStandPer].inGame) {
                     if((new Random()).nextInt(100) < 33) {
                        this.splash[stillStandPer].newParticle(new Color(79, 188, 43), new Rectangle(this.x + (new Random()).nextInt(this.width), this.y + (new Random()).nextInt(this.height), 4, 4));
                     } else if((new Random()).nextInt(100) < 50) {
                        this.splash[stillStandPer].newParticle(new Color(64, 152, 34), new Rectangle(this.x + (new Random()).nextInt(this.width), this.y + (new Random()).nextInt(this.height), 4, 4));
                     } else {
                        this.splash[stillStandPer].newParticle(new Color(71, 168, 38), new Rectangle(this.x + (new Random()).nextInt(this.width), this.y + (new Random()).nextInt(this.height), 4, 4));
                     }
                  }
               }
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
         byte var3 = 20;
         ra = (new Random()).nextInt(100 + var3);
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
      g.drawImage(Game.scheme_slime[this.facing + this.animation], this.x - Game.sX, this.y - Game.sY, this.mWidth, this.mHeight, (ImageObserver)null);

      for(int i = 0; i < this.splash.length; ++i) {
         if(this.splash[i].inGame) {
            this.splash[i].drawParticle(g);
         }
      }

   }
}

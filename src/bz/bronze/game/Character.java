package bz.bronze.game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Character extends Rectangle {

   private static final long serialVersionUID = 1L;
   public int cPickerX = 0;
   public int cPickerY = 0;
   public int frame = 0;
   public int animation = 0;
   public int cWidth = 36;
   public int cHeight = 42;
   public int normSpeed = 1;
   public int runSpeed = 0;
   public int moveSpeed;
   public int moveFrame;
   public int moveFake;
   public int animSpeed;
   public int animFrame;
   public int buildAreaSize;
   public int characterHideStrength;
   public int waterFrame;
   public int waterSpeed;
   public Rectangle buildArea;
   public boolean canMove;
   public boolean isRunning;
   public boolean hasShift;
   public boolean inWater;


   public Character() {
      this.moveSpeed = this.normSpeed;
      this.moveFrame = 0;
      this.moveFake = 2;
      this.animSpeed = 10;
      this.animFrame = 0;
      this.buildAreaSize = 50;
      this.characterHideStrength = 1;
      this.waterFrame = 0;
      this.waterSpeed = 25;
      this.canMove = true;
      this.isRunning = false;
      this.hasShift = false;
      this.inWater = false;
      this.setBounds(Frame.fWidth / 2 - this.cWidth + Game.sX, Frame.fHeight / 2 - this.cWidth + Game.sY, this.cWidth, this.cHeight);
      this.buildArea = new Rectangle(this.x - this.buildAreaSize, this.y - this.buildAreaSize, this.width + this.buildAreaSize * 2, this.height + this.buildAreaSize * 2);
   }

   public void drawCharacter(Graphics g) {
      byte extraIn = 4;
      boolean isLand = false;

      for(int y = extraIn / 2 + Game.sY / Picker.blockSize; y < 5 + extraIn / 2 + Game.sY / Picker.blockSize; ++y) {
         for(int x = extraIn + Game.sX / Picker.blockSize; x < 5 + extraIn + Game.sX / Picker.blockSize; ++x) {
            if(x >= 0 && y >= 0 && x < Picker.pickerSize && y < Picker.pickerSize) {
               Rectangle col = new Rectangle(Game.picker[this.cPickerX][this.cPickerY].ground[y][x].x, Game.picker[this.cPickerX][this.cPickerY].ground[y][x].y - 27, Game.picker[this.cPickerX][this.cPickerY].ground[y][x].width, Game.picker[this.cPickerX][this.cPickerY].ground[y][x].height);
               Point pt1 = new Point(this.x + 2, this.y - 2);
               Point pt2 = new Point(this.x + this.width - 2, this.y - 2);
               Point pt3 = new Point(this.x + this.width / 2, this.y - 2);
               if(col.contains(pt1) || col.contains(pt2) || col.contains(pt3)) {
                  if(Game.picker[this.cPickerX][this.cPickerY].ground[y][x].groundID != 8) {
                     isLand = true;
                     this.inWater = false;
                  } else {
                     this.inWater = true;
                  }
               }
            }
         }
      }

      if(isLand) {
         if(Game.fight.isFighting) {
            g.drawImage(Game.scheme_character_hit[this.frame], this.x - Game.sX, this.y - Game.sY, this.width, this.height, (ImageObserver)null);
         } else {
            g.drawImage(Game.scheme_character[this.frame + this.animation], this.x - Game.sX, this.y - Game.sY, this.width, this.height, (ImageObserver)null);
         }
      } else {
         g.drawImage(Game.scheme_character_swim[this.frame + this.animation], this.x - Game.sX, this.y - Game.sY, this.width, this.height, (ImageObserver)null);
      }

   }

   public void frame() {
      this.movement();
   }

   public void waterParticles() {
      if(this.waterFrame >= this.waterSpeed) {
         int i = 0;

         for(int t = 0; t < (new Random()).nextInt(20) + 5; ++t) {
            ++i;
            if(!Game.particle[i].inGame) {
               ColorChanger cc = new ColorChanger();
               cc.changeColor(0, false, 0, true);
               Game.particle[i].newParticle(Game.destroyTime.changedColor, new Rectangle(this.x - 4 + (new Random()).nextInt(this.width + 8), this.y - 4 + (new Random()).nextInt(this.height + 8), 4, 4));
            }
         }

         this.waterFrame = 0;
      } else {
         ++this.waterFrame;
      }

   }

   public void checkMovement(boolean upward, boolean downward, boolean left, boolean right) {
      if(this.hasShift && !this.inWater) {
         this.moveSpeed = this.runSpeed;
         this.isRunning = true;
      } else {
         this.moveSpeed = this.normSpeed;
         this.isRunning = false;
      }

      this.canMove = true;
      byte extraIn = 4;

      for(int y = extraIn / 2 + Game.sY / Picker.blockSize; y < 5 + extraIn / 2 + Game.sY / Picker.blockSize; ++y) {
         for(int x = extraIn + Game.sX / Picker.blockSize; x < 5 + extraIn + Game.sX / Picker.blockSize; ++x) {
            if(x >= 0 && y >= 0 && x < Picker.pickerSize && y < Picker.pickerSize && Game.picker[this.cPickerX][this.cPickerY].touch[y][x].touchID != -1 && Game.picker[this.cPickerX][this.cPickerY].touch[y][x].touchID != 57) {
               Rectangle col = Game.picker[this.cPickerX][this.cPickerY].touch[y][x].collision;
               Point pt1;
               Point pt2;
               Point pt3;
               if(upward) {
                  if(y == 0) {
                     this.canMove = false;
                  } else {
                     pt1 = new Point(this.x + 2, this.y - 2);
                     pt2 = new Point(this.x + this.width - 2, this.y - 2);
                     pt3 = new Point(this.x + this.width / 2, this.y - 2);
                     if(col.contains(pt1) || col.contains(pt2) || col.contains(pt3)) {
                        this.canMove = false;
                     }
                  }
               } else if(downward) {
                  pt1 = new Point(this.x + 3, this.y + this.height - 1);
                  pt2 = new Point(this.x + this.width - 3, this.y + this.height - 1);
                  pt3 = new Point(this.x + this.width / 2, this.y + this.height - 1);
                  if(col.contains(pt1) || col.contains(pt2) || col.contains(pt3)) {
                     this.canMove = false;
                  }
               } else if(left) {
                  pt1 = new Point(this.x, this.y + 1);
                  pt2 = new Point(this.x, this.y + this.height - 3);
                  pt3 = new Point(this.x, this.y + this.height / 2);
                  if(col.contains(pt1) || col.contains(pt2) || col.contains(pt3)) {
                     this.canMove = false;
                  }
               } else if(right) {
                  pt1 = new Point(this.x + this.width - 1, this.y + 1);
                  pt2 = new Point(this.x + this.width - 1, this.y + this.height - 3);
                  pt3 = new Point(this.x + this.width - 1, this.y + this.height / 2);
                  if(col.contains(pt1) || col.contains(pt2) || col.contains(pt3)) {
                     this.canMove = false;
                  }
               }
            }
         }
      }

   }

   @SuppressWarnings("unused")
   public void movement() {
      if(Game.downwardDown || Game.upwardDown || Game.rightDown || Game.leftDown) {
         Game.destroyTime.stopAll();
      }

      if(Game.downwardDown || Game.upwardDown || Game.rightDown || Game.leftDown) {
         if(this.moveFrame >= this.moveSpeed) {
            if(Game.upwardDown) {
               this.checkMovement(true, false, false, false);
               if(this.canMove) {
                  this.y -= this.moveFake;
                  this.buildArea.y -= this.moveFake;
                  if(Game.sY > 0) {
                     if(Game.eY <= 0) {
                        Game.sY -= this.moveFake;
                     } else {
                        Game.eY -= this.moveFake;
                     }
                  } else {
                     Game.eY -= this.moveFake;
                  }
               }
            }

            Picker var10001;
            int var1;
            Picker var10002;
            if(Game.downwardDown) {
               this.checkMovement(false, true, false, false);
               if(this.canMove) {
                  this.y += this.moveFake;
                  this.buildArea.y += this.moveFake;
                  var10001 = Game.picker[0][0];
                  var1 = Picker.pickerSize - 9;
                  var10002 = Game.picker[0][0];
                  if(Game.sY <= var1 * Picker.blockSize) {
                     if(Game.eY >= 0) {
                        Game.sY += this.moveFake;
                     } else {
                        Game.eY += this.moveFake;
                     }
                  } else {
                     Game.eY += this.moveFake;
                  }
               }
            }

            if(Game.leftDown) {
               this.checkMovement(false, false, true, false);
               if(this.canMove) {
                  this.x -= this.moveFake;
                  this.buildArea.x -= this.moveFake;
                  if(Game.sX > 0) {
                     if(Game.eX <= 0) {
                        Game.sX -= this.moveFake;
                     } else {
                        Game.eX -= this.moveFake;
                     }
                  } else {
                     Game.eX -= this.moveFake;
                  }
               }
            }

            if(Game.rightDown) {
               this.checkMovement(false, false, false, true);
               if(this.canMove) {
                  this.x += this.moveFake;
                  this.buildArea.x += this.moveFake;
                  var10001 = Game.picker[0][0];
                  var1 = Picker.pickerSize - 12;
                  var10002 = Game.picker[0][0];
                  if(Game.sX < var1 * Picker.blockSize) {
                     if(Game.eX >= 0) {
                        Game.sX += this.moveFake;
                     } else {
                        Game.eX += this.moveFake;
                     }
                  } else {
                     Game.eX += this.moveFake;
                  }
               }
            }

            if(this.animFrame >= this.animSpeed) {
               if(this.animation < 2) {
                  ++this.animation;
               } else {
                  this.animation = 1;
               }

               this.animFrame = 0;
            } else {
               ++this.animFrame;
            }

            this.moveFrame = 0;
         } else {
            ++this.moveFrame;
         }
      }

   }
}

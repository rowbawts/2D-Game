package bz.bronze.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class DestroyTime {

   public boolean isDestroying = false;
   public int destroyTime = 0;
   public int destroyFrame = 0;
   public int animFrame = 0;
   public int animTime = 0;
   public int animation = 0;
   public int animationLength = 9;
   public int destroyKind = 0;
   public int ateFrame = 0;
   public int ateTime = 50;
   public static int groundDestroy = 0;
   public static int touchDestroy = 1;
   public boolean isTouch = false;
   public boolean ateFood = false;
   public Color changedColor = new Color(0, 0, 0);


   @SuppressWarnings("unused")
   public void chestPhysic() {
      Picker var10002 = Game.picker[0][0];
      int y = 0 + Game.sY / Picker.blockSize;

      while(true) {
         Picker var10003 = Game.picker[0][0];
         if(y >= 12 + Game.sY / Picker.blockSize) {
            return;
         }

         var10002 = Game.picker[0][0];
         int x = 0 + Game.sX / Picker.blockSize;

         while(true) {
            var10003 = Game.picker[0][0];
            if(x >= 15 + Game.sX / Picker.blockSize) {
               ++y;
               break;
            }

            if(y >= 0 && x >= 0) {
               Picker var10001 = Game.picker[0][0];
               if(y < Picker.pickerSize) {
                  var10001 = Game.picker[0][0];
                  if(x < Picker.pickerSize && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.character.buildArea.contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == 56) {
                     if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemID == -1) {
                        Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemID = Game.inventory.invBarID[Game.inventory.invPosition];
                        Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemCount = Game.inventory.invBarCount[Game.inventory.invPosition];
                        Game.inventory.invBarID[Game.inventory.invPosition] = -1;
                        Game.inventory.invBarCount[Game.inventory.invPosition] = 0;
                     } else if(Game.inventory.invBarID[Game.inventory.invPosition] == -1) {
                        Game.inventory.invBarID[Game.inventory.invPosition] = Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemID;
                        Game.inventory.invBarCount[Game.inventory.invPosition] = Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemCount;
                        Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemID = -1;
                        Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].itemCount = 0;
                     }
                  }
               }
            }

            ++x;
         }
      }
   }

   public void takeTime(int x, int y) {
      if(this.destroyFrame >= this.destroyTime) {
         this.isDestroying = false;
         Destroy i = new Destroy();
         if(this.destroyKind == groundDestroy) {
            i.destroyGround(x, y);
         } else {
            i.destroyTouch(x, y);
         }

         this.destroyFrame = 0;
      } else {
         ++this.destroyFrame;
      }

      if(this.animFrame >= this.animTime) {
         ++this.animation;
         int var6 = 0;

         for(int t = 0; t < (new Random()).nextInt(90) + 5; ++t) {
            ++var6;
            if(!Game.particle[var6].inGame) {
               ColorChanger cc = new ColorChanger();
               cc.changeColor(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[Game.destroyY][Game.destroyX].groundID, this.isTouch, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[Game.destroyY][Game.destroyX].touchID, false);
               Game.particle[var6].newParticle(this.changedColor, new Rectangle(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[Game.destroyY][Game.destroyX].x + (new Random()).nextInt(Picker.blockSize), Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[Game.destroyY][Game.destroyX].y + (new Random()).nextInt(Picker.blockSize), 4, 4));
            }
         }

         this.animFrame = 0;
      } else {
         ++this.animFrame;
      }

   }

   public void ateFood() {
      if(this.ateFrame >= this.ateTime) {
         Game.destroyTime.ateFood = false;
         this.ateFrame = 0;
      } else {
         ++this.ateFrame;
      }

   }

   @SuppressWarnings("unused")
   public void drawTime(Graphics g) {
      boolean var10000 = Game.inventory.isOpen;
   }

   public void stopAll() {
      this.isDestroying = false;
      this.destroyFrame = 0;
      this.animFrame = 0;
   }
}

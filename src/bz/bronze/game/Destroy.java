package bz.bronze.game;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class Destroy {

   public int[] groundTool = new int[]{99, 98, 97, 96, 95};
   public int[] groundToolTime = new int[]{83, 66, 50, 25, 12};
   public int[] touchTool = new int[]{94, 93, 92, 91, 90};
   public int[] touchToolTime = new int[]{333, 183, 133, 83, 33};
   public int[] placeables = new int[]{0, 2, 1, 13};
   public int[] placeablesTouch = new int[]{0, 2, 13, 1, 12, 15};
   public int[] foodItems = new int[]{10, 11, 14, 18, 19};
   public int[] foodHealth = new int[]{1, 3, 6, -4, 4};
   public int[] energyItems = new int[]{18, 14};
   public int[] energyHealth;
   public int addTime;
   public boolean ground;
   public boolean touch;
   public boolean placeable;
   public boolean placeableTouch;
   public int redApplePer;
   public int greenApplePer;
   public int saplingPer;


   public Destroy() {
      this.energyHealth = new int[]{Bar.mbSize, -4};
      this.addTime = 0;
      this.ground = false;
      this.touch = false;
      this.placeable = false;
      this.placeableTouch = false;
      this.redApplePer = 15;
      this.greenApplePer = 1;
      this.saplingPer = 90;
   }

   public void destroyCheck() {
      int willDelete;
      for(willDelete = 0; willDelete < this.groundTool.length; ++willDelete) {
         if(this.groundTool[willDelete] == Game.inventory.invBarID[Game.inventory.invPosition]) {
            this.ground = true;
            this.addTime = this.groundToolTime[willDelete];
            willDelete += this.groundTool.length + 1;
         }
      }

      for(willDelete = 0; willDelete < this.touchTool.length; ++willDelete) {
         if(this.touchTool[willDelete] == Game.inventory.invBarID[Game.inventory.invPosition]) {
            this.touch = true;
            this.addTime = this.touchToolTime[willDelete];
            willDelete += this.groundTool.length + 1;
         }
      }

      for(willDelete = 0; willDelete < this.placeables.length; ++willDelete) {
         if(this.placeables[willDelete] == Game.inventory.invBarID[Game.inventory.invPosition]) {
            this.placeable = true;
            willDelete += this.placeables.length + 1;
         }
      }

      for(willDelete = 0; willDelete < this.placeablesTouch.length; ++willDelete) {
         if(this.placeablesTouch[willDelete] == Game.inventory.invBarID[Game.inventory.invPosition]) {
            this.placeableTouch = true;
            willDelete += this.placeablesTouch.length + 1;
         }
      }

      boolean var4 = false;
      int y;
      if(!Game.inventory.isOpen && !Game.destroyTime.ateFood) {
         for(y = 0; y < this.energyItems.length; ++y) {
            if((this.energyItems[y] == 18 || Bar.food < Bar.mbSize) && this.energyItems[y] == Game.inventory.invBarID[Game.inventory.invPosition]) {
               Bar.energy += this.energyHealth[y];
               if(Bar.energy > Bar.mbSize) {
                  Bar.energy = Bar.mbSize;
               } else if(Bar.energy < 0) {
                  Bar.energy = 0;
               }

               var4 = true;
               Game.destroyTime.ateFood = true;
               y += this.foodItems.length;
            }
         }

         for(y = 0; y < this.foodItems.length; ++y) {
            if((this.foodItems[y] == 18 || Bar.food < Bar.mbSize) && this.foodItems[y] == Game.inventory.invBarID[Game.inventory.invPosition]) {
               Bar.food += this.foodHealth[y];
               if(Bar.food > Bar.mbSize) {
                  Bar.food = Bar.mbSize;
               } else if(Bar.food < 0) {
                  Bar.food = 0;
               }

               var4 = true;
               Game.destroyTime.ateFood = true;
               y += this.foodItems.length;
            }
         }

         if(var4) {
            --Game.inventory.invBarCount[Game.inventory.invPosition];
            if(Game.inventory.invBarCount[Game.inventory.invPosition] == 0) {
               Game.inventory.invBarID[Game.inventory.invPosition] = -1;
            }
         }
      }

      int x;
      for(y = 0; y < Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground.length; ++y) {
         for(x = 0; x < Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground.length; ++x) {
            if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID != 4 && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.character.buildArea.contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && this.ground && Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == -1) {
               Game.destroyTime.animation = 0;
               Game.destroyTime.destroyTime = this.addTime;
               Game.destroyTime.animFrame = 0;
               Game.destroyTime.animTime = this.addTime / Game.destroyTime.animationLength;
               Game.destroyX = x;
               Game.destroyY = y;
               Game.destroyTime.isDestroying = true;
               Game.destroyTime.destroyKind = DestroyTime.groundDestroy;
               Game.destroyTime.isTouch = false;
            }

            if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.character.buildArea.contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID != -1 && this.touch) {
               Game.destroyTime.animation = 0;
               Game.destroyTime.destroyTime = this.addTime;
               Game.destroyTime.animFrame = 0;
               Game.destroyTime.animTime = this.addTime / Game.destroyTime.animationLength;
               Game.destroyX = x;
               Game.destroyY = y;
               Game.destroyTime.isDestroying = true;
               Game.destroyTime.destroyKind = DestroyTime.touchDestroy;
               Game.destroyTime.isTouch = true;
            }
         }
      }

      for(y = 0; y < Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground.length; ++y) {
         for(x = 0; x < Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground.length; ++x) {
            if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.character.buildArea.contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY))) {
               if(this.placeableTouch && Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == -1 && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID != 4 && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID != 0) {
                  if(Game.inventory.invBarID[Game.inventory.invPosition] == 12) {
                     if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID == 2 || Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID == 6) {
                        this.placeItem(x, y, false, true);
                     }
                  } else {
                     this.placeItem(x, y, false, true);
                  }
               }

               if(this.placeable && Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == -1 && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID == 4 && Game.inventory.invBarID[Game.inventory.invPosition] == 0) {
                  this.placeItem(x, y, false, false);
               } else if(this.placeable && Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == -1 && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID == 0) {
                  this.placeItem(x, y, true, false);
               }
            }
         }
      }

   }

   public void placeItem(int x, int y, boolean isTop, boolean isTouch) {
      if(!isTouch) {
         if(!isTop) {
            Game.ic.changeDestroyedItem(Game.inventory.invBarID[Game.inventory.invPosition], true, false, false);
            Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID = Game.ic.changedItem;
         } else {
            Game.ic.changeDestroyedItem(Game.inventory.invBarID[Game.inventory.invPosition], true, true, false);
            Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID = Game.ic.changedItem;
         }
      } else {
         Game.ic.changeDestroyedItem(Game.inventory.invBarID[Game.inventory.invPosition], true, false, true);
         Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID = Game.ic.changedItem;
         if(Game.ic.changedItem == 53) {
            Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].collision = new Rectangle(0, 0, 0, 0);
         } else {
            Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].collision = new Rectangle(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].x, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].y, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].width, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].height);
         }
      }

      Game.inventory.removeInventory();
      Game.hasDestroyed = true;
   }

   public void destroyGround(int x, int y) {
      if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == -1) {
         if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID != 0 && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID != 4) {
            Game.ic.changeDestroyedItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID, false, false, false);
            Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID = 0;
         } else if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID != 4) {
            Game.ic.changeDestroyedItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID, false, false, false);
            Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID = 4;
         }
      }

      for(int i = 0; i < Game.item.length; ++i) {
         if(Game.item[i].itemID == -1) {
            Game.item[i].createNewItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x], Game.ic.changedItem);
            i += Game.item.length + 1;
         }
      }

      Game.hasDestroyed = true;
   }

   @SuppressWarnings("unused")
   public void destroyTouch(int x, int y) {
      Game.ic.changeDestroyedItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID, false, false, false);
      Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID = -1;

      int t;
      for(t = 0; t < Game.item.length; ++t) {
         if(Game.item[t].itemID == -1) {
            Game.item[t].createNewItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x], Game.ic.changedItem);
            t += Game.item.length + 1;
         }
      }

      for(t = 0; t < (new Random()).nextInt(3) + 1; ++t) {
         for(int i = 0; i < Game.item.length; ++i) {
            if(Game.item[i].itemID == -1) {
               if(Game.ic.changedItem == 3) {
                  if((new Random()).nextInt(100) <= this.greenApplePer) {
                     Game.item[i].createNewItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x], 11);
                  } else if((new Random()).nextInt(100) <= this.redApplePer) {
                     Game.item[i].createNewItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x], 10);
                  } else if((new Random()).nextInt(100) <= this.saplingPer) {
                     Game.item[i].createNewItem(Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x], 12);
                  }
               } else {
                  int var10000 = Game.ic.changedItem;
               }

               i += Game.item.length + 1;
            }
         }
      }

   }
}

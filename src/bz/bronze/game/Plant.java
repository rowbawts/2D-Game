package bz.bronze.game;

import java.util.Random;

public class Plant {

   public int treeTime = 10000;
   public int treeFrame = 0;
   public int treeGrowPer = 40;
   public int grassTime = 10000;
   public int grassFrame = 0;
   public int grassGrowPer = 60;


   @SuppressWarnings("unused")
   public void grassGrow() {
      if(this.grassFrame >= this.grassTime) {
         Picker var10002 = Game.picker[0][0];
         int y = -10 + Game.sY / Picker.blockSize;

         while(true) {
            Picker var10003 = Game.picker[0][0];
            if(y >= 20 + Game.sY / Picker.blockSize) {
               this.grassFrame = 0;
               break;
            }

            var10002 = Game.picker[0][0];
            int x = -10 + Game.sX / Picker.blockSize;

            while(true) {
               var10003 = Game.picker[0][0];
               if(x >= 23 + Game.sX / Picker.blockSize) {
                  ++y;
                  break;
               }

               if(y >= 0 && x >= 0 && y < Picker.pickerSize && x < Picker.pickerSize && (new Random()).nextInt(100) <= this.grassGrowPer) {
                  try {
                     if(Game.picker[0][0].ground[y][x].groundID == 0) {
                        if(Game.picker[0][0].ground[y + 1][x].groundID == 2) {
                           Game.picker[0][0].ground[y][x].groundID = 2;
                        } else if(Game.picker[0][0].ground[y - 1][x].groundID == 2) {
                           Game.picker[0][0].ground[y][x].groundID = 2;
                        } else if(Game.picker[0][0].ground[y][x + 1].groundID == 2) {
                           Game.picker[0][0].ground[y][x].groundID = 2;
                        } else if(Game.picker[0][0].ground[y][x - 1].groundID == 2) {
                           Game.picker[0][0].ground[y][x].groundID = 2;
                        }
                     }
                  } catch (Exception var4) {
                     ;
                  }
               }

               ++x;
            }
         }
      } else {
         ++this.grassFrame;
      }

   }

   @SuppressWarnings("unused")
   public void treeGrow() {
      if(this.treeFrame >= this.treeTime) {
         Picker var10002 = Game.picker[0][0];
         int y = -20 + Game.sY / Picker.blockSize;

         while(true) {
            Picker var10003 = Game.picker[0][0];
            if(y >= 40 + Game.sY / Picker.blockSize) {
               this.treeFrame = 0;
               break;
            }

            var10002 = Game.picker[0][0];
            int x = -20 + Game.sX / Picker.blockSize;

            while(true) {
               var10003 = Game.picker[0][0];
               if(x >= 40 + Game.sX / Picker.blockSize) {
                  ++y;
                  break;
               }

               if(y >= 0 && x >= 0 && y < Picker.pickerSize && x < Picker.pickerSize && (new Random()).nextInt(100) <= this.treeGrowPer) {
                  if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == 53) {
                     Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID = 54;
                  } else if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == 54) {
                     Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID = 55;
                  } else if(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID == 55) {
                     Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].touchID = 0;
                     Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].collision.setBounds(Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].x, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].y, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].width, Game.picker[Game.character.cPickerX][Game.character.cPickerY].touch[y][x].height);
                  }
               }

               ++x;
            }
         }
      } else {
         ++this.treeFrame;
      }

   }
}

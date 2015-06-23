package bz.bronze.game;

import java.util.Random;

public class InventoryChanger {

   public int changedItem;


   public void changeDestroyedItem(int itc, boolean isPlacing, boolean isTop, boolean isTouch) {
      this.changedItem = -1;
      if(!isPlacing) {
         if(Game.destroyTime.isTouch) {
            if(itc == 1) {
               if((new Random()).nextInt(300) == 1) {
                  this.changedItem = 6;
               } else if((new Random()).nextInt(150) == 2) {
                  this.changedItem = 5;
               } else if((new Random()).nextInt(90) == 3) {
                  this.changedItem = 7;
               } else {
                  this.changedItem = 2;
               }
            } else if(itc == 0) {
               this.changedItem = 3;
            } else if(itc == 14) {
               this.changedItem = 0;
            } else if(itc == 27) {
               this.changedItem = 13;
            } else if(itc == 40) {
               this.changedItem = 1;
            } else if(itc != 53 && itc != 54 && itc != 55) {
               if(itc == 58) {
                  this.changedItem = 4;
               }
            } else {
               this.changedItem = 12;
            }
         } else if(itc == 0) {
            this.changedItem = 0;
         } else if(itc == 2) {
            this.changedItem = 0;
         } else if(itc == 3) {
            this.changedItem = 1;
         } else if(itc == 6) {
            this.changedItem = 0;
         } else if(itc == 9) {
            this.changedItem = 13;
         }
      } else if(!isTouch) {
         if(itc == 0) {
            if(isTop) {
               this.changedItem = 6;
            } else {
               this.changedItem = 0;
            }
         } else if(itc == 2) {
            this.changedItem = 7;
         } else if(itc == 1) {
            this.changedItem = 3;
         } else if(itc == 13) {
            this.changedItem = 9;
         }
      } else if(itc == 0) {
         this.changedItem = 14;
      } else if(itc == 2) {
         this.changedItem = 1;
      } else if(itc == 13) {
         this.changedItem = 27;
      } else if(itc == 1) {
         this.changedItem = 40;
      } else if(itc == 12) {
         this.changedItem = 53;
      } else if(itc == 15) {
         this.changedItem = 56;
      }

   }
}

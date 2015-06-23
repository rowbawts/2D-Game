package bz.bronze.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

public class CraftingMenu {

   public Rectangle[] crafting = new Rectangle[2];
   public int[] craftingID = new int[2];
   public int[] craftingCount = new int[2];
   public Rectangle result;
   public int resultID;
   public int resultCount;
   public int[][] craftingRecipe = new int[][]{{4, 3}, {4, 2}, {4, 7}, {4, 5}, {4, 6}, {23, 4}, {8, 4}, {22, 4}, {20, 4}, {21, 4}, {3, 3}, {2, 2}, {2, 2}, {3, -1}, {3, 13}, {14, 0}, {13, 13}, {7, 7}, {5, 5}, {6, 6}};
   public int[] craftingCreationID = new int[]{99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 4, 8, 8, 13, 15, 18, 23, 22, 20, 21};
   public int[] craftingCreationCount = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1};


   public CraftingMenu(Rectangle goPoint, int cSize, int bcSize) {
      for(int i = 0; i < this.crafting.length; ++i) {
         this.crafting[i] = new Rectangle(goPoint.x + cSize * i + bcSize * i, goPoint.y - cSize - bcSize, cSize, cSize);
         this.craftingID[i] = -1;
         this.craftingCount[i] = 0;
      }

      this.result = new Rectangle(this.crafting[1].x + cSize * 2 + bcSize * 2, goPoint.y - cSize - bcSize, cSize, cSize);
      this.resultID = 0;
      this.resultCount = 0;
   }

   public void craftingMouse(MouseEvent e) {
      int i;
      for(i = 0; i < this.crafting.length; ++i) {
         if(this.crafting[i].contains(Game.mse)) {
            int split;
            if(e.getButton() == 1) {
               if(Game.inventory.heldID == -1) {
                  Game.inventory.heldID = this.craftingID[i];
                  Game.inventory.heldCount = this.craftingCount[i];
                  this.craftingID[i] = -1;
                  this.craftingCount[i] = 0;
               } else if(this.craftingID[i] == -1) {
                  this.craftingID[i] = Game.inventory.heldID;
                  this.craftingCount[i] = Game.inventory.heldCount;
                  Game.inventory.heldID = -1;
                  Game.inventory.heldCount = 0;
               } else if(Inventory.canStack) {
                  if(this.craftingID[i] == Game.inventory.heldID) {
                     this.craftingCount[i] += Game.inventory.heldCount;
                     if(this.craftingCount[i] > Game.inventory.maxStack) {
                        split = this.craftingCount[i] - Game.inventory.maxStack;
                        Game.inventory.heldCount = split;
                        this.craftingCount[i] -= split;
                     } else {
                        Game.inventory.heldCount = 0;
                     }
                  } else {
                     split = Game.inventory.heldID;
                     int heldCountExtra = Game.inventory.heldCount;
                     Game.inventory.heldID = this.craftingID[i];
                     Game.inventory.heldCount = this.craftingCount[i];
                     this.craftingID[i] = split;
                     this.craftingCount[i] = heldCountExtra;
                  }
               }
            } else if(Game.inventory.heldID == -1) {
               if(this.craftingCount[i] > 1) {
                  split = this.craftingCount[i] / 2;
                  Game.inventory.heldCount = split;
                  Game.inventory.heldID = this.craftingID[i];
                  this.craftingCount[i] -= split;
               }
            } else if(Inventory.canStack && (this.craftingCount[i] < Game.inventory.maxStack && this.craftingID[i] == Game.inventory.heldID || this.craftingID[i] == -1)) {
               ++this.craftingCount[i];
               this.craftingID[i] = Game.inventory.heldID;
               --Game.inventory.heldCount;
            }
         }
      }

      if(this.result.contains(Game.mse) && this.resultID != -1 && (Game.inventory.heldID == this.resultID || Game.inventory.heldID == -1) && Game.inventory.heldCount < Game.inventory.maxStack - this.resultCount) {
         Game.inventory.heldID = this.resultID;
         Game.inventory.heldCount += this.resultCount;

         for(i = 0; i < this.crafting.length; ++i) {
            --this.craftingCount[i];
            if(this.craftingCount[i] < 1) {
               this.craftingID[i] = -1;
            }
         }
      }

      if(Game.inventory.heldCount == 0) {
         Game.inventory.heldID = -1;
      }

   }

   public void drawCrafting(Graphics g, int cSize, int bcSize) {
      this.resultID = -1;
      this.resultCount = 0;

      int i;
      for(i = 0; i < this.craftingRecipe.length; ++i) {
         if(this.craftingID[0] == this.craftingRecipe[i][0] && this.craftingID[1] == this.craftingRecipe[i][1] || this.craftingID[0] == this.craftingRecipe[i][1] && this.craftingID[1] == this.craftingRecipe[i][0]) {
            this.resultID = this.craftingCreationID[i];
            this.resultCount = this.craftingCreationCount[i];
         }
      }

      for(i = 0; i < this.crafting.length; ++i) {
         if(this.crafting[i].contains(Game.mse)) {
            g.setColor(new Color(255, 255, 255, 150));
            g.fillRect(this.crafting[i].x, this.crafting[i].y, this.crafting[i].width, this.crafting[i].height);
         }
      }

      if(this.result.contains(Game.mse)) {
         g.setColor(new Color(255, 255, 255, 150));
         g.fillRect(this.result.x, this.result.y, this.result.width, this.result.height);
      }

      for(i = 0; i < this.crafting.length; ++i) {
         g.drawImage(Game.scheme_inventory[0], this.crafting[i].x, this.crafting[i].y, this.crafting[i].width, this.crafting[i].height, (ImageObserver)null);
         if(this.craftingID[i] != -1) {
            g.drawImage(Game.scheme_item[this.craftingID[i]], this.crafting[i].x, this.crafting[i].y, this.crafting[i].width, this.crafting[i].height, (ImageObserver)null);
         }

         g.setColor(Color.BLACK);
         if(this.craftingCount[i] > 1) {
            g.drawString(String.valueOf(this.craftingCount[i]), this.crafting[i].x + 9, this.crafting[i].y + 19);
         }

         g.setColor(Color.WHITE);
         if(this.craftingCount[i] > 1) {
            g.drawString(String.valueOf(this.craftingCount[i]), this.crafting[i].x + 9 - 1, this.crafting[i].y + 19 - 1);
         }
      }

      g.drawImage(Game.scheme_inventory[0], this.result.x, this.result.y, this.result.width, this.result.height, (ImageObserver)null);
      g.drawImage(Game.scheme_random[1], this.result.x - cSize - bcSize, this.result.y, this.result.width, this.result.height, (ImageObserver)null);
      if(this.resultID != -1) {
         g.drawImage(Game.scheme_item[this.resultID], this.result.x, this.result.y, this.result.width, this.result.height, (ImageObserver)null);
      }

      g.setColor(Color.BLACK);
      if(this.resultCount > 1) {
         g.drawString(String.valueOf(this.resultCount), this.result.x + 9, this.result.y + 19);
      }

      g.setColor(Color.WHITE);
      if(this.resultCount > 1) {
         g.drawString(String.valueOf(this.resultCount), this.result.x + 9 - 1, this.result.y + 19 - 1);
      }

   }
}

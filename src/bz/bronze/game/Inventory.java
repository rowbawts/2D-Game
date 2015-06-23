package bz.bronze.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Inventory {

   public int invWidth = 7;
   public int invHeight = 5;
   public int bcSpace = 4;
   public int cSize = 60;
   public int eSpace = 100;
   public int sSpace = 85;
   public int sbibaInv = 20;
   public int afBorder = 10;
   public int invPosition = 0;
   public int curOut = 3;
   public int iOut;
   public int maxStack = 56;
   public Rectangle[] invBar;
   public Rectangle[] invBag;
   public int[] invBarID;
   public int[] invBagID;
   public int[] invBarCount;
   public int[] invBagCount;
   public int heldCount;
   public int heldID;
   public int[] nonStackables;
   public Font barFont;
   public CraftingMenu cm;
   public boolean isOpen;
   public static boolean canStack = true;


   public Inventory() {
      this.invBar = new Rectangle[this.invWidth];
      this.invBag = new Rectangle[this.invWidth * this.invHeight];
      this.invBarID = new int[this.invWidth];
      this.invBagID = new int[this.invWidth * this.invHeight];
      this.invBarCount = new int[this.invWidth];
      this.invBagCount = new int[this.invWidth * this.invHeight];
      this.heldCount = 0;
      this.heldID = -1;
      this.nonStackables = new int[]{-1, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90};
      this.barFont = new Font("Courier New", 1, 15);
      this.isOpen = false;

      int x;
      for(x = 0; x < this.invBar.length; ++x) {
         this.invBar[x] = new Rectangle(Frame.fWidth - (this.cSize + this.bcSpace) - this.afBorder, (this.cSize + this.bcSpace) * x + this.afBorder + 46, this.cSize, this.cSize);
         this.invBarID[x] = -1;
         this.invBarCount[x] = 0;
      }

      x = 0;
      int y = 0;

      for(int i = 0; i < this.invBag.length; ++i) {
         this.invBag[i] = new Rectangle(Frame.fWidth / 2 - (this.cSize * this.invWidth + this.bcSpace * this.invWidth - this.eSpace + this.sSpace) / 2 + x * this.cSize + x * this.bcSpace, Frame.fHeight - this.eSpace - (this.cSize + this.bcSpace) * this.invHeight + y * this.cSize + y * this.bcSpace - this.sbibaInv, this.cSize, this.cSize);
         this.invBagID[i] = -1;
         this.invBagCount[i] = 0;
         ++x;
         if(x == this.invWidth) {
            ++y;
            x = 0;
         }
      }

      this.cm = new CraftingMenu(this.invBag[0], this.cSize, this.bcSpace);
   }

   public void checkInventory(MouseEvent e) {
      canStack = true;

      int i;
      for(i = 0; i < this.nonStackables.length; ++i) {
         if(this.nonStackables[i] == this.heldID) {
            canStack = false;
         }
      }

      if(this.isOpen) {
         int split;
         int heldCountExtra;
         for(i = 0; i < this.invBag.length; ++i) {
            if(this.invBag[i].contains(Game.mse)) {
               if(e.getButton() == 1) {
                  if(this.heldID == -1) {
                     this.heldID = this.invBagID[i];
                     this.heldCount = this.invBagCount[i];
                     this.invBagID[i] = -1;
                     this.invBagCount[i] = 0;
                  } else if(this.invBagID[i] == -1) {
                     this.invBagID[i] = this.heldID;
                     this.invBagCount[i] = this.heldCount;
                     this.heldID = -1;
                     this.heldCount = 0;
                  } else if(this.invBagID[i] == this.heldID && canStack) {
                     this.invBagCount[i] += this.heldCount;
                     if(this.invBagCount[i] > this.maxStack) {
                        split = this.invBagCount[i] - this.maxStack;
                        this.heldCount = split;
                        this.invBagCount[i] -= split;
                     } else {
                        this.heldCount = 0;
                     }
                  } else {
                     split = this.heldID;
                     heldCountExtra = this.heldCount;
                     this.heldID = this.invBagID[i];
                     this.heldCount = this.invBagCount[i];
                     this.invBagID[i] = split;
                     this.invBagCount[i] = heldCountExtra;
                  }
               } else if(this.heldID == -1) {
                  if(this.invBagCount[i] > 1) {
                     split = this.invBagCount[i] / 2;
                     this.heldCount = split;
                     this.heldID = this.invBagID[i];
                     this.invBagCount[i] -= split;
                  }
               } else if(canStack && (this.invBagCount[i] < this.maxStack && this.invBagID[i] == this.heldID || this.invBagID[i] == -1)) {
                  ++this.invBagCount[i];
                  this.invBagID[i] = this.heldID;
                  --this.heldCount;
               }
            }
         }

         for(i = 0; i < this.invBar.length; ++i) {
            if(this.invBar[i].contains(Game.mse)) {
               if(e.getButton() == 1) {
                  if(this.heldID == -1) {
                     this.heldID = this.invBarID[i];
                     this.heldCount = this.invBarCount[i];
                     this.invBarID[i] = -1;
                     this.invBarCount[i] = 0;
                  } else if(this.invBarID[i] == -1) {
                     this.invBarID[i] = this.heldID;
                     this.invBarCount[i] = this.heldCount;
                     this.heldID = -1;
                     this.heldCount = 0;
                  } else if(this.invBarID[i] == this.heldID && canStack) {
                     this.invBarCount[i] += this.heldCount;
                     if(this.invBarCount[i] > this.maxStack) {
                        split = this.invBarCount[i] - this.maxStack;
                        this.heldCount = split;
                        this.invBarCount[i] -= split;
                     } else {
                        this.heldCount = 0;
                     }
                  } else {
                     split = this.heldID;
                     heldCountExtra = this.heldCount;
                     this.heldID = this.invBarID[i];
                     this.heldCount = this.invBarCount[i];
                     this.invBarID[i] = split;
                     this.invBarCount[i] = heldCountExtra;
                  }
               } else if(this.heldID == -1) {
                  if(this.invBarCount[i] > 1) {
                     split = this.invBarCount[i] / 2;
                     this.heldCount = split;
                     this.heldID = this.invBarID[i];
                     this.invBarCount[i] -= split;
                  }
               } else if(canStack && (this.invBarCount[i] < this.maxStack && this.invBarID[i] == this.heldID || this.invBarID[i] == -1)) {
                  ++this.invBarCount[i];
                  this.invBarID[i] = this.heldID;
                  --this.heldCount;
               }
            }
         }

         if(this.heldCount == 0) {
            this.heldID = -1;
         }

         canStack = true;
         this.cm.craftingMouse(e);
      }

   }

   public void clearInventory() {
      int i;
      for(i = 0; i < this.invBar.length; ++i) {
         this.invBarID[i] = -1;
         this.invBarCount[i] = 0;
      }

      for(i = 0; i < this.invBag.length; ++i) {
         this.invBagID[i] = -1;
         this.invBagCount[i] = 0;
      }

   }

   public void dropItem(boolean dropHeld, boolean atCharacter, int drop) {
      if(!atCharacter && !dropHeld) {
         for(int i = 0; i < Game.item.length; ++i) {
            if(Game.item[i].itemID == -1) {
               Game.item[i].createNewItem(new Rectangle(Game.character.x - 10 + (new Random()).nextInt(20), Game.character.y - 120, Game.character.width, Game.character.height), this.invBarID[this.invPosition]);
               i += Game.item.length + 1;
            }
         }

         --this.invBarCount[this.invPosition];
         if(this.invBarCount[this.invPosition] == 0) {
            this.invBarID[this.invPosition] = -1;
         }
      }

   }

   public void cheatItems() {
      this.invBagID[0] = 0;
      this.invBagID[1] = 1;
      this.invBagID[2] = 2;
      this.invBagID[3] = 3;
      this.invBarID[0] = 99;
      this.invBarID[1] = 95;
      this.invBarID[2] = 94;
      this.invBarID[3] = 90;
      this.invBagCount[0] = 56;
      this.invBagCount[1] = 56;
      this.invBagCount[2] = 56;
      this.invBagCount[3] = 56;
      this.invBarCount[0] = 1;
      this.invBarCount[1] = 1;
      this.invBarCount[2] = 1;
      this.invBarCount[3] = 1;
   }

   public void removeInventory() {
      --this.invBarCount[this.invPosition];
      if(this.invBarCount[this.invPosition] < 1) {
         this.invBarID[this.invPosition] = -1;
      }

   }

   public void addInventory(int itemID) {
      boolean canContinue = true;

      int i;
      for(i = 0; i < this.invBar.length; ++i) {
         if(this.invBarID[i] == itemID && this.invBarCount[i] < this.maxStack) {
            ++this.invBarCount[i];
            canContinue = false;
            i += this.invBar.length + 1;
         }
      }

      if(canContinue) {
         for(i = 0; i < this.invBag.length; ++i) {
            if(this.invBagID[i] == itemID && this.invBagCount[i] < this.maxStack) {
               ++this.invBagCount[i];
               canContinue = false;
               i += this.invBag.length + 1;
            }
         }
      }

      if(canContinue) {
         for(i = 0; i < this.invBar.length; ++i) {
            if(this.invBarID[i] == -1) {
               this.invBarCount[i] = 1;
               this.invBarID[i] = itemID;
               canContinue = false;
               i += this.invBar.length + 1;
            }
         }
      }

      if(canContinue) {
         for(i = 0; i < this.invBag.length; ++i) {
            if(this.invBagID[i] == -1) {
               this.invBagCount[i] = 1;
               this.invBagID[i] = itemID;
               canContinue = false;
               i += this.invBag.length + 1;
            }
         }
      }

   }

   public void drawInventory(Graphics g) {
      g.setFont(this.barFont);
      int i;
      if(this.isOpen) {
         for(i = 0; i < this.invBar.length; ++i) {
            if(this.invBar[i].contains(Game.mse)) {
               g.setColor(new Color(255, 255, 255, 160));
               g.fillRect(this.invBar[i].x, this.invBar[i].y, this.invBar[i].width, this.invBar[i].height);
            }
         }

         for(i = 0; i < this.invBag.length; ++i) {
            if(this.invBag[i].contains(Game.mse)) {
               g.setColor(new Color(255, 255, 255, 160));
               g.fillRect(this.invBag[i].x, this.invBag[i].y, this.invBag[i].width, this.invBag[i].height);
            }
         }
      }

      for(i = 0; i < this.invBar.length; ++i) {
         g.drawImage(Game.scheme_inventory[0], this.invBar[i].x, this.invBar[i].y, this.invBar[i].width, this.invBar[i].height, (ImageObserver)null);
         if(i == this.invPosition) {
            g.drawImage(Game.scheme_inventory[1], this.invBar[i].x - this.curOut, this.invBar[i].y - this.curOut, this.invBar[i].width + this.curOut * 2, this.invBar[i].height + this.curOut * 2, (ImageObserver)null);
         }

         if(this.invBarID[i] != -1) {
            g.drawImage(Game.scheme_item[this.invBarID[i]], this.invBar[i].x, this.invBar[i].y, this.invBar[i].width, this.invBar[i].height, (ImageObserver)null);
         }

         g.setColor(Color.BLACK);
         if(this.invBarCount[i] > 1) {
            g.drawString(String.valueOf(this.invBarCount[i]), this.invBar[i].x + 9, this.invBar[i].y + 19);
         }

         g.setColor(Color.WHITE);
         if(this.invBarCount[i] > 1) {
            g.drawString(String.valueOf(this.invBarCount[i]), this.invBar[i].x + 9 - 1, this.invBar[i].y + 19 - 1);
         }
      }

      if(this.isOpen) {
         for(i = 0; i < this.invBag.length; ++i) {
            g.drawImage(Game.scheme_inventory[0], this.invBag[i].x, this.invBag[i].y, this.invBag[i].width, this.invBag[i].height, (ImageObserver)null);
            if(this.invBagID[i] != -1) {
               g.drawImage(Game.scheme_item[this.invBagID[i]], this.invBag[i].x, this.invBag[i].y, this.invBag[i].width, this.invBag[i].height, (ImageObserver)null);
            }

            g.setColor(Color.BLACK);
            if(this.invBagCount[i] > 1) {
               g.drawString(String.valueOf(this.invBagCount[i]), this.invBag[i].x + 9, this.invBag[i].y + 19);
            }

            g.setColor(Color.WHITE);
            if(this.invBagCount[i] > 1) {
               g.drawString(String.valueOf(this.invBagCount[i]), this.invBag[i].x + 9 - 1, this.invBag[i].y + 19 - 1);
            }
         }

         this.cm.drawCrafting(g, this.cSize, this.bcSpace);
      }

      if(this.heldID != -1) {
         g.drawImage(Game.scheme_item[this.heldID], Game.mse.x - this.cSize / 2, Game.mse.y - this.cSize / 2, this.cSize, this.cSize, (ImageObserver)null);
      }

      g.setColor(Color.BLACK);
      if(this.heldCount > 1) {
         g.drawString(String.valueOf(this.heldCount), Game.mse.x - this.cSize / 2 + 9, Game.mse.y - this.cSize / 2 + 19);
      }

      g.setColor(Color.WHITE);
      if(this.heldCount > 1) {
         g.drawString(String.valueOf(this.heldCount), Game.mse.x - this.cSize / 2 + 9 - 1, Game.mse.y - this.cSize / 2 + 19 - 1);
      }

      if(this.isOpen) {
         for(i = 0; i < this.invBar.length; ++i) {
            if(this.invBar[i].contains(Game.mse) && this.invBarID[i] != -1) {
               g.setFont(new Font("Courier New", 1, 15));
               g.setColor(Color.BLACK);
               g.drawString(Value.itemNames[this.invBarID[i]], Game.mse.x - 80 + 1, Game.mse.y + 1);
               g.setColor(Color.WHITE);
               g.drawString(Value.itemNames[this.invBarID[i]], Game.mse.x - 80, Game.mse.y);
            }
         }

         for(i = 0; i < this.invBag.length; ++i) {
            if(this.invBag[i].contains(Game.mse) && this.invBagID[i] != -1) {
               g.setFont(new Font("Courier New", 1, 15));
               g.setColor(Color.BLACK);
               g.drawString(Value.itemNames[this.invBagID[i]], Game.mse.x - 80 + 1, Game.mse.y + 1);
               g.setColor(Color.WHITE);
               g.drawString(Value.itemNames[this.invBagID[i]], Game.mse.x - 80, Game.mse.y);
            }
         }
      }

   }
}

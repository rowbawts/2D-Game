package bz.bronze.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public class Bar {

   public static int health = 9;
   public static int food = 9;
   public static int energy = 9;
   public static int thirst = 9;
   public static int mbSize = 9;
   public static int iconSize = 24;
   public static int space = 12;
   public static int spaceB = 1;
   public static int healthID = 2;
   public static int energyID = 3;
   public static int sleepID = 4;
   public static int thirstID = 5;
   public static int foodID = 6;
   public int dayTime = '\uea60';
   public int dayFrame = 0;
   public int foodTime;
   public int foodFrame;
   public int thirstTime;
   public int thirstFrame;
   public int energyTime;
   public int energyFrame;
   public int looseTime;
   public int looseFrame;
   public int drinkingFrame;
   public int drinkingSpeed;
   public int day;
   public int night;
   public int timeOfDay;
   public int slotHeight;
   public int slotWidth;
   public int spaceIn;
   public int spaceHeight;


   public Bar() {
      this.foodTime = this.dayTime / 10;
      this.foodFrame = 0;
      this.thirstTime = this.foodTime / 4 * 3;
      this.thirstFrame = 0;
      this.energyTime = 170;
      this.energyFrame = 0;
      this.looseTime = 90;
      this.looseFrame = 0;
      this.drinkingFrame = 0;
      this.drinkingSpeed = 30;
      this.day = 0;
      this.night = 1;
      this.timeOfDay = this.day;
      this.slotHeight = 20;
      this.slotWidth = 20;
      this.spaceIn = 20;
      this.spaceHeight = 20;
   }

   @SuppressWarnings("unused")
   public void drinkRefill() {
      Picker picker2 = Game.picker[0][0];
      int y = 0 + Game.sY / Picker.blockSize;

      while(true) {
         Picker picker3 = Game.picker[0][0];
         if(y >= 12 + Game.sY / Picker.blockSize) {
            return;
         }

         picker2 = Game.picker[0][0];
         int x = 0 + Game.sX / Picker.blockSize;

         while(true) {
        	 picker3 = Game.picker[0][0];
            if(x >= 15 + Game.sX / Picker.blockSize) {
               ++y;
               break;
            }

            if(y >= 0 && x >= 0) {
               Picker picker1 = Game.picker[0][0];
               if(y < Picker.pickerSize) {
                  picker1 = Game.picker[0][0];
                  if(x < Picker.pickerSize && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && Game.character.buildArea.contains(new Point(Game.mse.x + Game.sX, Game.mse.y + Game.sY)) && thirst < mbSize && Game.picker[Game.character.cPickerX][Game.character.cPickerY].ground[y][x].groundID == 8) {
                     Game.isDrinking = true;
                  }
               }
            }

            ++x;
         }
      }
   }

   public void resetTimes() {
      health = mbSize;
      food = mbSize;
      energy = mbSize;
      thirst = mbSize;
   }

   public void barUsage() {
      if(Game.isDrinking) {
         if(this.drinkingFrame >= this.drinkingSpeed) {
            if(thirst < mbSize) {
               ++thirst;
            } else {
               Game.isDrinking = false;
            }

            this.drinkingFrame = 0;
         } else {
            ++this.drinkingFrame;
         }
      }

      if(Game.upwardDown || Game.downwardDown || Game.leftDown || Game.rightDown) {
         Game.isDrinking = false;
      }

      if(this.dayFrame >= this.dayTime) {
         if(this.timeOfDay == this.day) {
            this.timeOfDay = this.night;
         } else {
            this.timeOfDay = this.day;
         }

         this.dayFrame = 0;
      } else {
         ++this.dayFrame;
      }

      if(this.foodFrame >= this.foodTime) {
         if(food > 0) {
            --food;
         }

         this.foodFrame = 0;
      } else {
         ++this.foodFrame;
      }

      if(this.thirstFrame >= this.thirstTime) {
         if(thirst > 0) {
            --thirst;
         }

         this.thirstFrame = 0;
      } else {
         ++this.thirstFrame;
      }

      if(Game.character.hasShift && Game.character.isRunning && !Game.character.inWater) {
         if(this.energyFrame >= this.energyTime) {
            if(energy > 0) {
               --energy;
            }

            if(energy == 0) {
               Game.character.hasShift = false;
            }

            this.energyFrame = 0;
         } else {
            ++this.energyFrame;
         }
      } else if(this.energyFrame >= this.energyTime * 4) {
         if(energy < mbSize) {
            ++energy;
         }

         this.energyFrame = 0;
      } else {
         ++this.energyFrame;
      }

      if(thirst == 0 || food == 0) {
         if(this.looseFrame >= this.looseTime) {
            if(health > 0) {
               --health;
            }

            this.looseFrame = 0;
         } else {
            ++this.looseFrame;
         }
      }

      if(health < 1) {
         Game.menu.isDead = true;
         Game.inMenu = true;
         Game.menu.deathFade = 0;
         Game.menu.deathFrame = 0;
      }

   }

   public void drawBar(Graphics g) {
      this.slotHeight = 20;
      this.slotWidth = 20;
      this.spaceIn = 10;
      this.spaceHeight = Frame.lHeight - 68;
      if(!Game.inventory.isOpen) {
         g.setFont(new Font("Courier New", 1, 14));

         int i;
         for(i = 0; i < mbSize; ++i) {
            if(health > i) {
               g.setColor(new Color(255, 0, 40));
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
               g.setColor(Color.BLACK);
               if(i == 0) {
                  g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1);
               } else if(i == mbSize - 1) {
                  g.drawLine(this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
               }

               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight);
               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
            } else {
               g.setColor(Color.BLACK);
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
            }
         }

         g.setColor(Color.WHITE);
         g.drawString("Health", this.slotWidth + this.spaceIn + 46, this.spaceHeight + 13);
         this.spaceHeight += this.slotHeight + 10;

         for(i = 0; i < mbSize; ++i) {
            if(energy > i) {
               g.setColor(new Color(255, 170, 50));
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
               g.setColor(Color.BLACK);
               if(i == 0) {
                  g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1);
               } else if(i == mbSize - 1) {
                  g.drawLine(this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
               }

               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight);
               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
            } else {
               g.setColor(Color.BLACK);
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
            }
         }

         g.setColor(Color.WHITE);
         g.drawString("Energy", this.slotWidth + this.spaceIn + 46, this.spaceHeight + 13);
         this.spaceHeight -= this.slotHeight + 10;
         this.spaceIn += this.slotWidth * mbSize + 20;

         for(i = 0; i < mbSize; ++i) {
            if(food > i) {
               g.setColor(new Color(55, 25, 0));
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
               g.setColor(Color.BLACK);
               if(i == 0) {
                  g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1);
               } else if(i == mbSize - 1) {
                  g.drawLine(this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
               }

               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight);
               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
            } else {
               g.setColor(Color.BLACK);
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
            }
         }

         g.setColor(Color.WHITE);
         g.drawString("Hunger", this.slotWidth + this.spaceIn + 46, this.spaceHeight + 13);
         this.spaceHeight += this.slotHeight + 10;

         for(i = 0; i < mbSize; ++i) {
            if(thirst > i) {
               g.setColor(new Color(15, 100, 255));
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
               g.setColor(Color.BLACK);
               if(i == 0) {
                  g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1);
               } else if(i == mbSize - 1) {
                  g.drawLine(this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
               }

               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight);
               g.drawLine(this.slotWidth * i + this.spaceIn, this.spaceHeight + this.slotHeight - 1, this.slotWidth * i + this.spaceIn + this.slotWidth, this.spaceHeight + this.slotHeight - 1);
            } else {
               g.setColor(Color.BLACK);
               g.fillRect(this.slotWidth * i + this.spaceIn, this.spaceHeight, this.slotWidth, this.slotHeight);
            }
         }

         g.setColor(Color.WHITE);
         g.drawString("Thirst", this.slotWidth + this.spaceIn + 46, this.spaceHeight + 13);
      }

   }
}

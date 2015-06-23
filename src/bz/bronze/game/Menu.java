package bz.bronze.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.File;

public class Menu {

   public Rectangle logo = new Rectangle();
   public Rectangle[] menuButtons = new Rectangle[4];
   public Rectangle[] menuDelete = new Rectangle[3];
   public int menuButtonLight = -1;
   public int menuDeleteLight = -1;
   public int logoHeight = 120;
   public int logoWidth = 480;
   public int extraHeight = 30;
   public int buttonWidth = 480;
   public int buttonHeight = 70;
   public int buttonSpace = 10;
   public int fadeColor = 255;
   public int beforeTime = 140;
   public int beforeFrame = 0;
   public int fadeFrame = 0;
   public int fadeSpeed = 100;
   public int afterFrame = 0;
   public int afterTime = 100;
   public int guiFrame = 0;
   public int guiSpeed = 0;
   public int guiAway = 310;
   public int deathFrame = 0;
   public int deathSpeed = 20;
   public int deathFade = 0;
   public boolean isFading = true;
   public boolean showButtons = false;
   public boolean inStart = false;
   public boolean inInfo = false;
   public boolean inOptions = false;
   public boolean inEscape = false;
   public boolean inStats = false;
   public boolean isDead = false;
   public int fontNumber = 5;
   public int textSpace = 18;
   public int fontWidth;
   public int fontHeight;
   public int[][] buttonWords;
   public int[][] startWords;
   public int[][] escapeWords;
   public int[][] infoWords;
   public int[][] deathWords;
   public String[] infoText;
   public int[][] optionWords;
   public String[] optionText;
   public String[] deathText;
   public String[] statsText;


   public Menu() {
      this.fontWidth = 5 * this.fontNumber;
      this.fontHeight = 7 * this.fontNumber;
      this.buttonWords = new int[][]{{18, 19, 0, 17, 19}, {8, 13, 5, 14}, {14, 15, 19, 8, 14, 13, 18}, {4, 23, 8, 19}};
      this.startWords = new int[][]{{2, 17, 4, 0, 19, 4}, {2, 17, 4, 0, 19, 4}, {2, 17, 4, 0, 19, 4}, {1, 0, 2, 10}};
      this.escapeWords = new int[][]{{14, 15, 19, 8, 14, 13, 18}, {18, 19, 0, 19, 18}, {18, 0, 21, 4}, {1, 0, 2, 10}};
      this.infoWords = new int[][]{new int[0], new int[0], new int[0], {1, 0, 2, 10}};
      this.deathWords = new int[][]{new int[0], new int[0], new int[0], {3, 4, 11, 4, 19, 4}};
      this.infoText = new String[]{"Instafight, a game by Sebastian Cabrera.", "Patch Notes:", "- 0.0.1 -",  "Initial Release", "- 0.0.2 -", "Fixed pressing shift while standing still taking away energy.", "Performance improvements."};
      this.optionWords = new int[][]{new int[0], new int[0], new int[0], {1, 0, 2, 10}};
      this.optionText = new String[]{"Options is currently not working, they will be added in", "the (0.2.0) update of the game"};
      this.deathText = new String[]{"Another brave adventurer fallen, hope you had a nice time", "living!"};
      this.statsText = new String[]{"Statistics is currently not working, they will be added in the", "(0.2.0) update of the game."};
      if(Game.isAdmin) {
         this.deathText[0] = "Another brave \"HAM\" has fallen, hope you had a nice time";
         this.deathText[1] = "living!";
      }

      for(int i = 0; i < this.menuButtons.length; ++i) {
         this.menuButtons[i] = new Rectangle(Frame.lWidth / 2 - this.buttonWidth / 2, i * this.buttonHeight + i * this.buttonSpace + this.extraHeight * 2 + this.logoHeight, this.buttonWidth, this.buttonHeight);
         if(i < this.menuButtons.length - 1) {
            this.menuDelete[i] = new Rectangle(Frame.lWidth / 2 - this.buttonWidth / 2 + this.buttonWidth + this.buttonSpace, i * this.buttonHeight + i * this.buttonSpace + this.extraHeight * 2 + this.logoHeight, this.buttonHeight, this.buttonHeight);
         }
      }

      this.logo = new Rectangle(Frame.lWidth / 2 - this.logoWidth / 2, this.extraHeight, this.logoWidth, this.logoHeight);
   }

   public void menuAnimation() {
      if(this.isFading) {
         if(this.beforeFrame >= this.beforeTime) {
            if(this.fadeFrame >= this.fadeSpeed) {
               if(this.fadeColor > 0) {
                  --this.fadeColor;
               } else {
                  this.isFading = false;
               }
            } else {
               ++this.fadeFrame;
            }
         } else {
            ++this.beforeFrame;
         }
      } else if(!this.showButtons) {
         if(this.afterFrame >= this.afterTime) {
            this.showButtons = true;
         } else {
            ++this.afterFrame;
         }
      } else if(this.guiAway > 0) {
         if(this.guiFrame >= this.guiSpeed) {
            --this.guiAway;
            this.guiFrame = 0;
         } else {
            ++this.guiFrame;
         }
      } else {
         this.checkLight();
      }

   }

   public void deathScreen() {
      if(this.deathFrame >= this.deathSpeed) {
         if(this.deathFade < 255) {
            ++this.deathFade;
         }

         this.deathFrame = 0;
      } else {
         ++this.deathFrame;
      }

   }

   public void checkLight() {
      this.menuButtonLight = -1;

      int i;
      for(i = 0; i < this.menuButtons.length; ++i) {
         if(this.menuButtons[i].contains(Game.mse)) {
            this.menuButtonLight = i;
            i += this.menuButtons.length + 1;
         }
      }

      this.menuDeleteLight = -1;

      for(i = 0; i < this.menuDelete.length; ++i) {
         if(this.menuDelete[i].contains(Game.mse)) {
            this.menuDeleteLight = i;
            i += this.menuDelete.length + 1;
         }
      }

   }

   public void buttonClicked() {
      if(!this.isFading && this.guiAway == 0) {
         int i;
         if(!this.inStart && !this.inInfo && !this.inOptions && !this.inEscape && !this.inStats && !this.isDead) {
            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse)) {
                  if(i == 0) {
                     this.inStart = true;
                  } else if(i == 1) {
                     this.inInfo = true;
                  } else if(i == 2) {
                     this.inOptions = true;
                  } else if(i == 3) {
                     System.exit(0);
                  }
               }
            }
         } else if(this.inStart) {
            for(i = 0; i < this.menuDelete.length; ++i) {
               if(this.menuDelete[i].contains(Game.mse)) {
                  Game.save.deleteLevel(new File("saves/w" + (i + 1) + "/"), i + 1);
                  Game.save.checkSaves();
                  this.isDead = false;
               }
            }

            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse)) {
                  if(i != 0 && i != 1 && i != 2) {
                     if(i == 3) {
                        this.inStart = false;
                     }
                  } else {
                     Game.currentLevelFile = i + 1;
                     if(this.startWords[i][0] == 11) {
                        Game.inventory.clearInventory();
                        Game.save.loadLevel(new File("saves/w" + Game.currentLevelFile + "/"));
                        Game.inMenu = false;
                        this.inStart = false;
                     } else {
                        Game.sY = 500;
                        Game.sX = 500;
                        Game.character = new Character();
                        Game.inventory.clearInventory();
                        Game.world.newWorld();
                        if(Game.isAdmin) {
                           Game.inventory.cheatItems();
                        }

                        Game.inMenu = false;
                        this.inStart = false;
                     }
                  }
               }
            }
         } else if(this.inInfo) {
            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse) && i != 0 && i != 1 && i != 2 && i == 3) {
                  this.inInfo = false;
               }
            }
         } else if(this.inOptions) {
            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse) && i != 0 && i != 1 && i != 2 && i == 3) {
                  this.inOptions = false;
               }
            }
         } else if(this.inStats) {
            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse) && i != 0 && i != 1 && i != 2 && i == 3) {
                  this.inStats = false;
               }
            }
         } else if(this.inEscape) {
            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse)) {
                  if(i == 0) {
                     this.inOptions = true;
                  } else if(i == 1) {
                     this.inStats = true;
                  } else if(i == 2) {
                     this.inEscape = false;
                     Game.inMenu = true;
                     Game.save.saveLevel(new File("saves/w" + Game.currentLevelFile + "/"));
                     Game.save.checkSaves();
                     Game.world.newWorld();
                  } else if(i == 3) {
                     this.inEscape = false;
                     Game.inMenu = false;
                  }
               }
            }
         } else if(this.isDead) {
            for(i = 0; i < this.menuButtons.length; ++i) {
               if(this.menuButtons[i].contains(Game.mse) && i == 3) {
                  Game.save.deleteLevel(new File("saves/w" + Game.currentLevelFile + "/"), Game.currentLevelFile);
                  Game.save.checkSaves();
                  Game.world.newWorld();
                  this.isDead = false;
                  Game.inMenu = true;
                  Game.bar.resetTimes();
               }
            }
         }
      }

   }

   public void drawMenu(Graphics g) {
      g.drawImage(Game.scheme_random[7], 0, 0, Frame.lWidth, Frame.lHeight, (ImageObserver)null);
      if(this.isFading) {
         g.setColor(new Color(0, 0, 0, this.fadeColor));
         g.fillRect(0, 0, 1000, 1000);
      }

      if(this.isDead) {
         g.setColor(new Color(255, 0, 0, this.deathFade));
         g.fillRect(0, 0, 1000, 1000);
      }

      if(this.showButtons) {
         int i;
         int x;
         if(!this.inStart && !this.inInfo && !this.inOptions && !this.inEscape && !this.inStats && !this.isDead) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y - this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               g.drawImage(Game.scheme_button[0], this.menuButtons[i].x, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

               for(x = 0; x < this.buttonWords[i].length; ++x) {
                  g.drawImage(Game.scheme_font[this.buttonWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace + this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  g.drawImage(Game.scheme_font[this.buttonWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace + this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
               }

               if(this.menuButtonLight == i) {
                  g.setColor(new Color(255, 255, 255, 100));
                  g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
               }
            }
         } else if(this.inStart) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y + this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               g.drawImage(Game.scheme_button[0], this.menuButtons[i].x - this.guiAway, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

               for(x = 0; x < this.startWords[i].length; ++x) {
                  g.drawImage(Game.scheme_font[this.startWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  g.drawImage(Game.scheme_font[this.startWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
               }

               if(this.menuButtonLight == i) {
                  g.setColor(new Color(255, 255, 255, 100));
                  g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
               }

               if(i < this.menuButtons.length - 1) {
                  g.drawImage(Game.scheme_button[2], this.menuDelete[i].x - this.guiAway, this.menuDelete[i].y + this.guiAway, this.menuDelete[i].width, this.menuDelete[i].height, (ImageObserver)null);
               }

               if(this.menuDeleteLight == i) {
                  g.setColor(new Color(255, 255, 255, 100));
                  g.fillRect(this.menuDelete[i].x + 3 - this.guiAway, this.menuDelete[i].y + 3 + this.guiAway, this.menuDelete[i].width - 6, this.menuDelete[i].height - 6);
               }
            }
         } else if(this.inInfo) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y + this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               if(i > 2) {
                  g.drawImage(Game.scheme_button[0], this.menuButtons[i].x - this.guiAway, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

                  for(x = 0; x < this.infoWords[i].length; ++x) {
                     g.drawImage(Game.scheme_font[this.infoWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                     g.drawImage(Game.scheme_font[this.infoWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  }

                  if(this.menuButtonLight == i) {
                     g.setColor(new Color(255, 255, 255, 100));
                     g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
                  }
               }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", 1, 15));

            for(i = 0; i < this.infoText.length; ++i) {
               g.drawString(this.infoText[i], 20, this.logo.y + this.logo.height + 20 + i * 17);
            }
         } else if(this.inOptions) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y + this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               if(i > 2) {
                  g.drawImage(Game.scheme_button[0], this.menuButtons[i].x - this.guiAway, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

                  for(x = 0; x < this.optionWords[i].length; ++x) {
                     g.drawImage(Game.scheme_font[this.optionWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                     g.drawImage(Game.scheme_font[this.optionWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  }

                  if(this.menuButtonLight == i) {
                     g.setColor(new Color(255, 255, 255, 100));
                     g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
                  }
               }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", 1, 15));

            for(i = 0; i < this.optionText.length; ++i) {
               g.drawString(this.optionText[i], 20, this.logo.y + this.logo.height + 20 + i * 17);
            }
         } else if(this.inStats) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y + this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               if(i > 2) {
                  g.drawImage(Game.scheme_button[0], this.menuButtons[i].x - this.guiAway, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

                  for(x = 0; x < this.optionWords[i].length; ++x) {
                     g.drawImage(Game.scheme_font[this.optionWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                     g.drawImage(Game.scheme_font[this.optionWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  }

                  if(this.menuButtonLight == i) {
                     g.setColor(new Color(255, 255, 255, 100));
                     g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
                  }
               }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", 1, 15));

            for(i = 0; i < this.statsText.length; ++i) {
               g.drawString(this.statsText[i], 20, this.logo.y + this.logo.height + 20 + i * 17);
            }
         } else if(this.inEscape) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y + this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               g.drawImage(Game.scheme_button[0], this.menuButtons[i].x - this.guiAway, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

               for(x = 0; x < this.escapeWords[i].length; ++x) {
                  g.drawImage(Game.scheme_font[this.escapeWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  g.drawImage(Game.scheme_font[this.escapeWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
               }

               if(this.menuButtonLight == i) {
                  g.setColor(new Color(255, 255, 255, 100));
                  g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
               }
            }
         } else if(this.isDead) {
            g.drawImage(Game.scheme_button[1], this.logo.x + this.guiAway, this.logo.y + this.guiAway, this.logo.width, this.logo.height, (ImageObserver)null);

            for(i = 0; i < this.menuButtons.length; ++i) {
               if(i > 2) {
                  g.drawImage(Game.scheme_button[0], this.menuButtons[i].x - this.guiAway, this.menuButtons[i].y + this.guiAway, this.menuButtons[i].width, this.menuButtons[i].height, (ImageObserver)null);

                  for(x = 0; x < this.deathWords[i].length; ++x) {
                     g.drawImage(Game.scheme_font[this.deathWords[i][x] + 50], this.menuButtons[i].x - 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y + 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                     g.drawImage(Game.scheme_font[this.deathWords[i][x]], this.menuButtons[i].x + 1 + this.textSpace + x * this.fontWidth + x * 10 - this.guiAway, this.menuButtons[i].y - 1 + this.textSpace - this.guiAway, this.fontWidth, this.fontHeight, (ImageObserver)null);
                  }

                  if(this.menuButtonLight == i) {
                     g.setColor(new Color(255, 255, 255, 100));
                     g.fillRect(this.menuButtons[i].x + 3 - this.guiAway, this.menuButtons[i].y + 3 + this.guiAway, this.menuButtons[i].width - 6, this.menuButtons[i].height - 6);
                  }
               }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", 1, 15));

            for(i = 0; i < this.deathText.length; ++i) {
               g.drawString(this.deathText[i], 20, this.logo.y + this.logo.height + 20 + i * 17);
            }
         }
      }

   }
}

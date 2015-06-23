package bz.bronze.game;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Picker {

   public int pickerX;
   public int pickerY;
   public static int pickerSize = 400;
   public static int blockSize = 60;
   public boolean isGenerated = false;
   public Ground[][] ground;
   public Touch[][] touch;
   public int waterStep1;
   public int waterStep2;
   public int waterStep3;
   public int waterStep4;
   public int waterStep5;
   public int treePer;


   public Picker(int pickerX, int pickerY) {
      this.ground = new Ground[pickerSize][pickerSize];
      this.touch = new Touch[pickerSize][pickerSize];
      this.waterStep1 = 6;
      this.waterStep2 = 9;
      this.waterStep3 = 13;
      this.waterStep4 = 14;
      this.waterStep5 = 20;
      this.treePer = 3;
      this.pickerX = pickerX;
      this.pickerY = pickerY;

      for(int y = 0; y < this.ground.length; ++y) {
         for(int x = 0; x < this.ground[y].length; ++x) {
            this.ground[y][x] = new Ground(x + pickerX * pickerSize, y + pickerY * pickerSize, blockSize, y, x);
            this.touch[y][x] = new Touch(x + pickerX * pickerSize, y + pickerY * pickerSize, blockSize, y, x);
         }
      }

      this.generatePicker();
      this.isGenerated = true;
   }

   public void generatePicker() {
      int y;
      int x;
      for(y = 0; y < this.ground.length; ++y) {
         for(x = 0; x < this.ground[y].length; ++x) {
            try {
               if((new Random()).nextInt(100) < 75) {
                  if((new Random()).nextInt(100) < 50) {
                     this.ground[y][x].groundID = this.ground[y - 1][x].groundID;
                  } else if((new Random()).nextInt(100) < 10) {
                     this.ground[y][x].groundID = 3;
                  } else if((new Random()).nextInt(100) < 80) {
                     this.ground[y][x].groundID = 2;
                  } else {
                     this.ground[y][x].groundID = 8;
                  }
               } else if((new Random()).nextInt(100) < 75) {
                  this.ground[y][x].groundID = this.ground[y][x - 1].groundID;
               } else if((new Random()).nextInt(100) < 15) {
                  this.ground[y][x].groundID = 3;
               } else if((new Random()).nextInt(100) < 80) {
                  this.ground[y][x].groundID = 2;
               } else {
                  this.ground[y][x].groundID = 8;
               }
            } catch (Exception var5) {
               if((new Random()).nextInt(100) < 15) {
                  this.ground[y][x].groundID = 3;
               } else if((new Random()).nextInt(100) < 40) {
                  this.ground[y][x].groundID = 2;
               } else {
                  this.ground[y][x].groundID = 6;
               }
            }

            if(this.ground[y][x].groundID == 2 && (new Random()).nextInt(100) < 45) {
               if((new Random()).nextInt(100) < 20) {
                  if((new Random()).nextInt(100) < 87) {
                     this.touch[y][x].touchID = 0;
                  } else {
                     this.touch[y][x].touchID = 58;
                  }
               } else {
                  try {
                     if((new Random()).nextInt() >= 10 && this.touch[y][x - 1].touchID != 1) {
                        this.touch[y][x].touchID = -1;
                     } else {
                        this.touch[y][x].touchID = 1;
                     }
                  } catch (Exception var4) {
                     this.touch[y][x].touchID = -1;
                  }
               }
            }
         }
      }

      for(y = 0; y < this.ground.length; ++y) {
         for(x = 0; x < this.ground[y].length; ++x) {
            if(x >= 10 && y >= 10 && y <= pickerSize - 10 && x <= pickerSize - 10) {
               if(x >= 13 && y >= 13 && y <= pickerSize - 13 && x <= pickerSize - 13) {
                  if(x >= 15 && y >= 15 && y <= pickerSize - 15 && x <= pickerSize - 15) {
                     if((x < 17 || y < 17 || y > pickerSize - 17 || x > pickerSize - 17) && (new Random()).nextInt(100) < 50) {
                        this.ground[y][x].groundID = 3;
                        this.touch[y][x].touchID = -1;
                     }
                  } else {
                     this.ground[y][x].groundID = 3;
                     this.touch[y][x].touchID = -1;
                  }
               } else if((new Random()).nextInt(100) < 50) {
                  this.ground[y][x].groundID = 8;
                  this.touch[y][x].touchID = -1;
               } else {
                  this.ground[y][x].groundID = 3;
                  this.touch[y][x].touchID = -1;
               }
            } else {
               this.ground[y][x].groundID = 8;
               this.touch[y][x].touchID = -1;
            }
         }
      }

   }

   public void drawPicker(Graphics g) {
      for(int y = 0 + Game.sY / blockSize; y < 11 + Game.sY / blockSize; ++y) {
         for(int x = 0 + Game.sX / blockSize; x < 15 + Game.sX / blockSize; ++x) {
            if(y >= 0 && x >= 0 && y < pickerSize && x < pickerSize) {
               if(this.ground[y][x].groundID == 8) {
                  g.drawImage(Game.scheme_ground[0], this.ground[y][x].x - Game.sX, this.ground[y][x].y - Game.sY, this.ground[y][x].width, this.ground[y][x].height, (ImageObserver)null);
               }

               g.drawImage(Game.scheme_ground[this.ground[y][x].groundID], this.ground[y][x].x - Game.sX, this.ground[y][x].y - Game.sY, this.ground[y][x].width, this.ground[y][x].height, (ImageObserver)null);
               if(this.ground[y][x].groundID == 8) {
                  g.drawImage(Game.scheme_liquid[Game.animation.liquidAnimation], this.ground[y][x].x - Game.sX, this.ground[y][x].y - Game.sY, this.ground[y][x].width, this.ground[y][x].height, (ImageObserver)null);
               }
            }

            try {
               if(this.touch[y][x].touchID != -1) {
                  if(this.touch[y][x].touchID != 1 && this.touch[y][x].touchID != 14 && this.touch[y][x].touchID != 27 && this.touch[y][x].touchID != 40) {
                     if(this.touch[y][x].touchID == 56) {
                        g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                        g.drawImage(Game.scheme_item[this.touch[y][x].itemID], this.touch[y][x].x - Game.sX + this.touch[y][x].width / 2 - 8, this.touch[y][x].y - Game.sY + this.touch[y][x].height / 2 - 8, 16, 16, (ImageObserver)null);
                     } else {
                        g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                     }
                  } else {
                     try {
                        if(this.touch[y][x].touchID == this.touch[y - 1][x].touchID) {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 5], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                        } else {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 1], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                        }
                     } catch (Exception var12) {
                        g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 5], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                     }

                     try {
                        if(this.touch[y][x].touchID == this.touch[y + 1][x].touchID) {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 6], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                        } else {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 2], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                        }
                     } catch (Exception var11) {
                        g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 6], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                     }

                     try {
                        if(this.touch[y][x].touchID == this.touch[y][x - 1].touchID) {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 7], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);

                           try {
                              if(this.touch[y + 1][x].touchID != this.touch[y][x].touchID) {
                                 g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 9], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                              }
                           } catch (Exception var9) {
                              ;
                           }
                        } else {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);

                           try {
                              if(this.touch[y + 1][x].touchID == this.touch[y][x].touchID) {
                                 g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 11], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                              } else {
                                 g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 3], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                              }
                           } catch (Exception var8) {
                              g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 3], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           }
                        }
                     } catch (Exception var10) {
                        g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 7], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                     }

                     try {
                        if(this.touch[y][x].touchID == this.touch[y][x + 1].touchID) {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 8], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);

                           try {
                              if(this.touch[y + 1][x].touchID != this.touch[y][x].touchID) {
                                 g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 10], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                              }
                           } catch (Exception var6) {
                              ;
                           }
                        } else {
                           g.drawImage(Game.scheme_touch[this.touch[y][x].touchID], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);

                           try {
                              if(this.touch[y + 1][x].touchID == this.touch[y][x].touchID) {
                                 g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 12], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                              } else {
                                 g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 4], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                              }
                           } catch (Exception var5) {
                              g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 4], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                           }
                        }
                     } catch (Exception var7) {
                        g.drawImage(Game.scheme_touch[this.touch[y][x].touchID + 8], this.touch[y][x].x - Game.sX, this.touch[y][x].y - Game.sY, this.touch[y][x].width, this.touch[y][x].height, (ImageObserver)null);
                     }
                  }
               }
            } catch (Exception var13) {
               ;
            }
         }
      }

   }
}

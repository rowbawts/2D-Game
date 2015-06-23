package bz.bronze.game;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {

   private static final long serialVersionUID = -3118749973450270459L;
   public static Image[] scheme_ground = new Image[80];
   public static Image[] scheme_touch = new Image[100];
   public static Image[] scheme_character = new Image[12];
   public static Image[] scheme_character_swim = new Image[12];
   public static Image[] scheme_character_hit = new Image[12];
   public static Image[] scheme_inventory = new Image[2];
   public static Image[] scheme_item = new Image[100];
   public static Image[] scheme_liquid = new Image[3];
   public static Image[] scheme_button = new Image[10];
   public static Image[] scheme_font = new Image[100];
   public static Image[] scheme_random = new Image[100];
   public static Image[] scheme_mob = new Image[4];
   public static Image[] scheme_progress = new Image[10];
   public static Image[] scheme_bat = new Image[4];
   public static Image[] scheme_slime = new Image[20];
   public static int worldSize = 1;
   public static int sStPos = (new Random()).nextInt(200) + 1000;
   public static int sX = sStPos;
   public static int sY = sStPos;
   public static int eX = 0;
   public static int eY = 0;
   public static int destroyX = 0;
   public static int destroyY = 0;
   public static int saveSize = 3;
   public static int currentLevelFile = -1;
   public static int maximumMobs = 5;
   public boolean isRunning = true;
   public boolean imagesLoaded = true;
   public static boolean upwardDown = false;
   public static boolean downwardDown = false;
   public static boolean leftDown = false;
   public static boolean rightDown = false;
   public static boolean mouseDown = false;
   public static boolean hasDestroyed = true;
   public static boolean inMenu = true;
   public static boolean isPaused = false;
   public static boolean inSplash = true;
   public static boolean isAdmin = false;
   public static boolean noGui = false;
   public static boolean isTesting = false;
   public static boolean isDrinking = false;
   public static Point mse = new Point(0, 0);
   public static Character character;
   public static Picker[][] picker = new Picker[worldSize][worldSize];
   public static Inventory inventory;
   public static DestroyTime destroyTime;
   public static Item[] item = new Item[1000];
   public static InventoryChanger ic = new InventoryChanger();
   public static Animation animation = new Animation();
   public static World world = new World();
   public static Splash splash = new Splash();
   public static Menu menu = new Menu();
   public static Save save = new Save();
   public static Bar bar = new Bar();
   public static Plant plant = new Plant();
   public static Particle[] particle = new Particle[100];
   public static Mob[] mob = new Mob[maximumMobs];
   public static Thread[] mobThread = new Thread[maximumMobs];
   public static Spawn spawnManager = new Spawn();
   public static Fight fight;
   public Thread game;
   public int drawTimes = 7;
   public long beginTime;
   public long timeTaken;
   public long timeLeft;
   static final int UPDATE_RATE = 25;
   static final long UPDATE_PERIOD = 4000000L;
   public int ups = 27;
   public int upsF = 0;
   public int hdFrame = 0;
   public int hdTime = 20;


   public Game(Frame f) {
      if(isAdmin) {
         inMenu = false;
         inSplash = false;
      }

      this.loadImages();
      fight = new Fight();
      save.checkSaves();

      int kh;
      for(kh = 0; kh < item.length; ++kh) {
         item[kh] = new Item();
      }

      destroyTime = new DestroyTime();
      character = new Character();
      inventory = new Inventory();
      world.newWorld();
      bar = new Bar();

      for(kh = 0; kh < particle.length; ++kh) {
         particle[kh] = new Particle();
      }

      for(kh = 0; kh < mob.length; mob[kh].currentID = kh++) {
         mob[kh] = new Mob();
         mobThread[kh] = new Thread(mob[kh]);
      }

      if(isAdmin) {
         kh = 0;

         for(int i = 0; i < inventory.invBag.length; ++i) {
            while(i + kh < 100) {
               if(!Value.itemNames[i + kh].equals(".")) {
                  inventory.invBagID[i] = i + kh;
                  inventory.invBagCount[i] = 200;
                  break;
               }

               ++kh;
            }
         }
      }

      System.out.println("There are currently " + inventory.cm.craftingCreationID.length + " crafting recipes in the game!");
      KeyHandel var4 = new KeyHandel();
      f.addKeyListener(var4);
      f.addMouseMotionListener(var4);
      f.addMouseWheelListener(var4);
      f.addMouseListener(var4);
      this.game = new Thread(this);
      this.game.start();
   }

   public void loadImages() {
      try {
         int e;
         for(e = 0; e < scheme_ground.length; ++e) {
            scheme_ground[e] = Toolkit.getDefaultToolkit().getImage("res/scheme_ground.png");
            scheme_ground[e] = this.createImage(new FilteredImageSource(scheme_ground[e].getSource(), new CropImageFilter(e * 20, 0, 20, 20)));
         }

         for(e = 0; e < scheme_touch.length; ++e) {
            scheme_touch[e] = Toolkit.getDefaultToolkit().getImage("res/scheme_touch.png");
            scheme_touch[e] = this.createImage(new FilteredImageSource(scheme_touch[e].getSource(), new CropImageFilter(e * 20, 0, 20, 20)));
         }

         for(e = 0; e < scheme_character.length; ++e) {
            scheme_character[e] = Toolkit.getDefaultToolkit().getImage("res/scheme_character.png");
            scheme_character[e] = this.createImage(new FilteredImageSource(scheme_character[e].getSource(), new CropImageFilter(e * 12, 0, 12, 14)));
         }

         for(e = 0; e < scheme_character_swim.length; ++e) {
            scheme_character_swim[e] = Toolkit.getDefaultToolkit().getImage("res/scheme_character_swim.png");
            scheme_character_swim[e] = this.createImage(new FilteredImageSource(scheme_character_swim[e].getSource(), new CropImageFilter(e * 12, 0, 12, 14)));
         }

         for(e = 0; e < scheme_character_hit.length; ++e) {
            scheme_character_hit[e] = Toolkit.getDefaultToolkit().getImage("res/scheme_character_hit.png");
            scheme_character_hit[e] = this.createImage(new FilteredImageSource(scheme_character_hit[e].getSource(), new CropImageFilter(e * 12, 0, 12, 14)));
         }

         for(e = 0; e < scheme_item.length; ++e) {
            scheme_item[e] = Toolkit.getDefaultToolkit().getImage("res/item_map.png");
            scheme_item[e] = this.createImage(new FilteredImageSource(scheme_item[e].getSource(), new CropImageFilter(e * 16, 0, 16, 16)));
         }

         for(e = 0; e < scheme_liquid.length; ++e) {
            scheme_liquid[e] = Toolkit.getDefaultToolkit().getImage("res/scheme_liquid.png");
            scheme_liquid[e] = this.createImage(new FilteredImageSource(scheme_liquid[e].getSource(), new CropImageFilter(e * 20, 0, 20, 20)));
         }

         for(e = 0; e < 50; ++e) {
            scheme_font[e] = Toolkit.getDefaultToolkit().getImage("res/bttn/font_reg.png");
            scheme_font[e] = this.createImage(new FilteredImageSource(scheme_font[e].getSource(), new CropImageFilter(e * 5, 0, 5, 7)));
         }

         for(e = 0; e < 50; ++e) {
            scheme_font[e + 50] = Toolkit.getDefaultToolkit().getImage("res/bttn/font_shadow.png");
            scheme_font[e + 50] = this.createImage(new FilteredImageSource(scheme_font[e + 50].getSource(), new CropImageFilter(e * 5, 0, 5, 7)));
         }

         for(e = 0; e < scheme_bat.length; ++e) {
            scheme_bat[e] = Toolkit.getDefaultToolkit().getImage("res/mob/scheme_bat.png");
            scheme_bat[e] = this.createImage(new FilteredImageSource(scheme_bat[e].getSource(), new CropImageFilter(e * 16, 0, 16, 16)));
         }

         for(e = 0; e < scheme_slime.length; ++e) {
            scheme_slime[e] = Toolkit.getDefaultToolkit().getImage("res/mob/scheme_slime.png");
            scheme_slime[e] = this.createImage(new FilteredImageSource(scheme_slime[e].getSource(), new CropImageFilter(e * 16, 0, 16, 20)));
         }

         scheme_inventory[0] = Toolkit.getDefaultToolkit().getImage("res/inventory_slot.png");
         scheme_inventory[1] = Toolkit.getDefaultToolkit().getImage("res/inventory_cursor.png");
         scheme_random[0] = Toolkit.getDefaultToolkit().getImage("res/bttn/splash.png");
         scheme_random[1] = Toolkit.getDefaultToolkit().getImage("res/scheme_arrow.png");
         scheme_random[2] = Toolkit.getDefaultToolkit().getImage("res/heart.png");
         scheme_random[3] = Toolkit.getDefaultToolkit().getImage("res/energy.png");
         scheme_random[4] = Toolkit.getDefaultToolkit().getImage("res/bttn/sleep.png");
         scheme_random[5] = Toolkit.getDefaultToolkit().getImage("res/bttn/thirst.png");
         scheme_random[6] = Toolkit.getDefaultToolkit().getImage("res/bttn/food.png");
         scheme_random[7] = Toolkit.getDefaultToolkit().getImage("res/background_blur.png");
         scheme_button[0] = Toolkit.getDefaultToolkit().getImage("res/bttn/button_reg.png");
         scheme_button[1] = Toolkit.getDefaultToolkit().getImage("res/bttn/logo.png");
         scheme_button[2] = Toolkit.getDefaultToolkit().getImage("res/bttn/button_del.png");
      } catch (Exception e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog((Component)null, "You need internet connection in order to run this game!");
         System.exit(0);
      }

      this.imagesLoaded = true;
   }

   public void paintComponent(Graphics g) {
      super.paintComponent(g);

      for(int t = 0; t <= this.drawTimes; ++t) {
         if(this.imagesLoaded) {
            if(!inMenu && !isPaused) {
               int i;
               for(i = 0; i < picker.length; ++i) {
                  for(int x = 0; x < picker.length; ++x) {
                     if(character.cPickerX == x && character.cPickerY == i && picker[i][x].isGenerated) {
                        picker[i][x].drawPicker(g);
                     }
                  }
               }

               if(destroyTime.isDestroying) {
                  destroyTime.drawTime(g);
               }

               character.drawCharacter(g);

               for(i = 0; i < mob.length; ++i) {
                  if(mob[i].inGame) {
                     mob[i].drawMob(g);
                  }
               }

               for(i = 0; i < item.length; ++i) {
                  if(item[i].itemID != -1) {
                     item[i].drawItem(g);
                  }
               }

               if(!noGui) {
                  bar.drawBar(g);
               }

               for(i = 0; i < particle.length; ++i) {
                  if(particle[i].inGame) {
                     particle[i].drawParticle(g);
                  }
               }

               if(character.inWater) {
                  character.waterParticles();
               }

               if(!noGui) {
                  inventory.drawInventory(g);
               }

               if(fight.isFighting) {
                  fight.draw(g);
               }
            } else if(inSplash) {
               splash.drawSplash(g);
            } else if(inMenu) {
               menu.drawMenu(g);
            }
         }
      }

   }

   public void run() {
      while(true) {
         this.beginTime = System.nanoTime();
         if(!inMenu && !isPaused) {
            animation.animateLiquid();
            bar.barUsage();
            plant.treeGrow();
            plant.grassGrow();
            if(destroyTime.ateFood) {
               destroyTime.ateFood();
            }

            if(fight.isFighting) {
               fight.fightPhysic();
            }

            int d;
            for(d = 0; d < particle.length; ++d) {
               if(particle[d].inGame) {
                  particle[d].physics();
               }
            }

            for(d = 0; d < item.length; ++d) {
               if(item[d].itemID != -1) {
                  item[d].itemPhysic();
               }
            }

            if(destroyTime.isDestroying) {
               destroyTime.takeTime(destroyX, destroyY);
            }

            character.frame();
            spawnManager.spawnMob();
            if(mouseDown) {
               if(!hasDestroyed) {
                  if(!destroyTime.isDestroying) {
                     Destroy var3 = new Destroy();
                     var3.destroyCheck();
                  }
               } else if(this.hdFrame >= this.hdTime) {
                  hasDestroyed = false;
                  this.hdFrame = 0;
               } else {
                  ++this.hdFrame;
               }
            }
         } else if(inSplash) {
            splash.takeTime();
         } else if(inMenu) {
            menu.menuAnimation();
            if(menu.isDead) {
               menu.deathScreen();
            }
         }

         this.repaint();
         this.timeTaken = System.nanoTime() - this.beginTime;
         this.timeLeft = (4000000L - this.timeTaken) / 1000000L;
         if(this.timeLeft < 10L) {
            this.timeLeft = 10L;
         }

         try {
            Thread.sleep(this.timeLeft);
         } catch (InterruptedException var2) {
            ;
         }
      }
   }
}

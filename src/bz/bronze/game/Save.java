package bz.bronze.game;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class Save {

   public void checkSaves() {
      for(int i = 0; i < Game.saveSize; ++i) {
         new File("");
         File file = new File("saves/w" + (i + 1) + "/isCreated.ham");
         if(file.exists()) {
            Game.menu.startWords[i] = new int[4];
            Game.menu.startWords[i][0] = 11;
            Game.menu.startWords[i][1] = 14;
            Game.menu.startWords[i][2] = 0;
            Game.menu.startWords[i][3] = 3;
         } else {
            Game.menu.startWords[i] = new int[6];
            Game.menu.startWords[i][0] = 2;
            Game.menu.startWords[i][1] = 17;
            Game.menu.startWords[i][2] = 4;
            Game.menu.startWords[i][3] = 0;
            Game.menu.startWords[i][4] = 19;
            Game.menu.startWords[i][5] = 4;
         }
      }

   }

   public void saveLevel(File path) {
      File isCreated = new File(path + "/isCreated.ham");

      try {
         isCreated.createNewFile();
      } catch (IOException var11) {
         ;
      }

      File character;
      Formatter bar;
      for(int invBar = 0; invBar < Game.picker.length; ++invBar) {
         for(int invBag = 0; invBag < Game.picker[0].length; ++invBag) {
            character = new File(path + "/picker" + invBar + invBag + ".ham");

            try {
               bar = new Formatter(character);

               for(int barFormatter = 0; barFormatter < Game.picker[invBar][invBag].ground[0].length; ++barFormatter) {
                  for(int x = 0; x < Game.picker[invBar][invBag].ground[0].length; ++x) {
                     bar.format("%d %d %d %d ", new Object[]{Integer.valueOf(Game.picker[invBar][invBag].ground[barFormatter][x].groundID), Integer.valueOf(Game.picker[invBar][invBag].touch[barFormatter][x].touchID), Integer.valueOf(Game.picker[invBar][invBag].touch[barFormatter][x].itemID), Integer.valueOf(Game.picker[invBar][invBag].touch[barFormatter][x].itemCount)});
                  }
               }

               bar.close();
            } catch (Exception var14) {
               ;
            }
         }
      }

      File var15 = new File(path + "/invBar.ham");

      try {
         Formatter var16 = new Formatter(var15);

         for(int var18 = 0; var18 < Game.inventory.invBar.length; ++var18) {
            var16.format("%d %d ", new Object[]{Integer.valueOf(Game.inventory.invBarID[var18]), Integer.valueOf(Game.inventory.invBarCount[var18])});
         }

         var16.close();
      } catch (Exception var13) {
         ;
      }

      File var17 = new File(path + "/invBag.ham");

      try {
         Formatter var19 = new Formatter(var17);

         for(int var20 = 0; var20 < Game.inventory.invBag.length; ++var20) {
            var19.format("%d %d ", new Object[]{Integer.valueOf(Game.inventory.invBagID[var20]), Integer.valueOf(Game.inventory.invBagCount[var20])});
         }

         var19.close();
      } catch (Exception var12) {
         ;
      }

      character = new File(path + "/character.ham");

      try {
         bar = new Formatter(character);
         bar.format("%d %d %d %d %d %d %d %d %d %d ", new Object[]{Integer.valueOf(Game.sX), Integer.valueOf(Game.sY), Integer.valueOf(Game.character.x), Integer.valueOf(Game.character.y), Integer.valueOf(Game.character.width), Integer.valueOf(Game.character.height), Integer.valueOf(Game.character.buildArea.x), Integer.valueOf(Game.character.buildArea.y), Integer.valueOf(Game.character.buildArea.width), Integer.valueOf(Game.character.buildArea.height)});
         bar.close();
      } catch (Exception var10) {
         ;
      }

      File var21 = new File(path + "/bar.ham");

      try {
         Formatter var22 = new Formatter(var21);
         var22.format("%d %d %d %d %d %d ", new Object[]{Integer.valueOf(Bar.food), Integer.valueOf(Bar.energy), Integer.valueOf(Bar.thirst), Integer.valueOf(Game.bar.dayTime), Integer.valueOf(Game.bar.dayFrame), Integer.valueOf(Game.bar.timeOfDay)});
         var22.close();
      } catch (Exception var9) {
         ;
      }

   }

   public void loadLevel(File path) {
      File character;
      Scanner bar;
      int i;
      int t;
      for(int invBar = 0; invBar < Game.picker.length; ++invBar) {
         for(int invBag = 0; invBag < Game.picker[0].length; ++invBag) {
            character = new File(path + "/picker" + invBar + invBag + ".ham");

            try {
               bar = new Scanner(character);

               while(bar.hasNext()) {
                  for(i = 0; i < Game.picker[invBar][invBag].ground.length; ++i) {
                     for(t = 0; t < Game.picker[invBar][invBag].ground[0].length; ++t) {
                        Game.picker[invBar][invBag].ground[i][t].groundID = Integer.parseInt(bar.next());
                        Game.picker[invBar][invBag].touch[i][t].touchID = Integer.parseInt(bar.next());
                        Game.picker[invBar][invBag].touch[i][t].itemID = Integer.parseInt(bar.next());
                        Game.picker[invBar][invBag].touch[i][t].itemCount = Integer.parseInt(bar.next());
                     }
                  }
               }

               bar.close();
            } catch (Exception var14) {
               ;
            }
         }
      }

      File var15 = new File(path + "/invBar.ham");

      try {
         Scanner var16 = new Scanner(var15);

         while(var16.hasNext()) {
            for(int var18 = 0; var18 < Game.inventory.invBar.length; ++var18) {
               Game.inventory.invBarID[var18] = Integer.parseInt(var16.next());
               Game.inventory.invBarCount[var18] = Integer.parseInt(var16.next());
            }
         }

         var16.close();
      } catch (Exception var13) {
         ;
      }

      File var17 = new File(path + "/invBag.ham");

      try {
         Scanner var19 = new Scanner(var17);

         while(var19.hasNext()) {
            for(int var20 = 0; var20 < Game.inventory.invBag.length; ++var20) {
               Game.inventory.invBagID[var20] = Integer.parseInt(var19.next());
               Game.inventory.invBagCount[var20] = Integer.parseInt(var19.next());
            }
         }

         var19.close();
      } catch (Exception var12) {
         ;
      }

      character = new File(path + "/character.ham");

      try {
         for(bar = new Scanner(character); bar.hasNext(); Game.character.buildArea.height = Integer.parseInt(bar.next())) {
            Game.sX = Integer.parseInt(bar.next());
            Game.sY = Integer.parseInt(bar.next());
            Game.character.x = Integer.parseInt(bar.next());
            Game.character.y = Integer.parseInt(bar.next());
            Game.character.width = Integer.parseInt(bar.next());
            Game.character.height = Integer.parseInt(bar.next());
            Game.character.buildArea.x = Integer.parseInt(bar.next());
            Game.character.buildArea.y = Integer.parseInt(bar.next());
            Game.character.buildArea.width = Integer.parseInt(bar.next());
         }

         bar.close();
      } catch (Exception var11) {
         ;
      }

      File var21 = new File(path + "/bar.ham");

      try {
         Scanner var22;
         for(var22 = new Scanner(var21); var22.hasNext(); Game.bar.timeOfDay = Integer.parseInt(var22.next())) {
            Bar.food = Integer.parseInt(var22.next());
            Bar.energy = Integer.parseInt(var22.next());
            Bar.thirst = Integer.parseInt(var22.next());
            Game.bar.dayTime = Integer.parseInt(var22.next());
            Game.bar.dayFrame = Integer.parseInt(var22.next());
         }

         var22.close();
      } catch (Exception var10) {
         ;
      }

      for(i = 0; i < Game.picker.length; ++i) {
         for(t = 0; t < Game.picker[0].length; ++t) {
            for(int y = 0; y < Game.picker[i][t].ground.length; ++y) {
               for(int x = 0; x < Game.picker[i][t].ground[0].length; ++x) {
                  if(Game.picker[i][t].touch[y][x].touchID == 53 || Game.picker[i][t].touch[y][x].touchID == 54 || Game.picker[i][t].touch[y][x].touchID == 55) {
                     Game.picker[i][t].touch[y][x].collision.setBounds(0, 0, 0, 0);
                  }
               }
            }
         }
      }

   }

   public void deleteLevel(File path, int deletedWorld) {
      ++deletedWorld;
      File isCreated = new File(path + "/isCreated.ham");
      isCreated.delete();

      File character;
      for(int invBar = 0; invBar < Game.picker.length; ++invBar) {
         for(int invBag = 0; invBag < Game.picker[0].length; ++invBag) {
            character = new File(path + "/picker" + invBar + invBag + ".ham");
            character.delete();
         }
      }

      File var7 = new File(path + "/invBar.ham");
      var7.delete();
      File var8 = new File(path + "/invBag.ham");
      var8.delete();
      character = new File(path + "/character.ham");
      character.delete();
   }
}

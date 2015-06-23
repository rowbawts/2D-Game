package bz.bronze.game;

import java.awt.Point;

public class World {

   public void newWorld() {
      for(int y = 0; y < Game.picker.length; ++y) {
         for(int x = 0; x < Game.picker[0].length; ++x) {
            Game.picker[y][x] = new Picker(x, y);

            for(int i = 0; i < Game.picker[y][x].touch.length; ++i) {
               for(int t = 0; t < Game.picker[y][x].touch[0].length; ++t) {
                  Point pt1 = new Point(Game.character.x, Game.character.y);
                  Point pt2 = new Point(Game.character.x + Game.character.width - 1, Game.character.y);
                  Point pt3 = new Point(Game.character.x, Game.character.y + Game.character.height - 1);
                  Point pt4 = new Point(Game.character.x + Game.character.width - 1, Game.character.y + Game.character.height - 1);
                  if(Game.picker[y][x].touch[i][t].contains(pt1) || Game.picker[y][x].touch[i][t].contains(pt2) || Game.picker[y][x].touch[i][t].contains(pt3) || Game.picker[y][x].touch[i][t].contains(pt4)) {
                     if(Game.isAdmin) {
                        Game.picker[y][x].touch[i - 2][t - 2].touchID = 27;
                        Game.picker[y][x].ground[i - 2][t - 2].groundID = 9;
                        Game.picker[y][x].touch[i - 2][t - 1].touchID = 27;
                        Game.picker[y][x].ground[i - 2][t - 1].groundID = 9;
                        Game.picker[y][x].touch[i - 2][t].touchID = 27;
                        Game.picker[y][x].ground[i - 2][t].groundID = 9;
                        Game.picker[y][x].touch[i - 2][t + 1].touchID = 27;
                        Game.picker[y][x].ground[i - 2][t + 1].groundID = 9;
                        Game.picker[y][x].touch[i - 2][t + 2].touchID = 27;
                        Game.picker[y][x].ground[i - 2][t + 2].groundID = 9;
                        Game.picker[y][x].touch[i - 1][t - 2].touchID = 27;
                        Game.picker[y][x].ground[i - 1][t - 2].groundID = 9;
                        Game.picker[y][x].touch[i - 1][t - 1].touchID = -1;
                        Game.picker[y][x].ground[i - 1][t - 1].groundID = 9;
                        Game.picker[y][x].touch[i - 1][t].touchID = -1;
                        Game.picker[y][x].ground[i - 1][t].groundID = 9;
                        Game.picker[y][x].touch[i - 1][t + 1].touchID = -1;
                        Game.picker[y][x].ground[i - 1][t + 1].groundID = 9;
                        Game.picker[y][x].touch[i - 1][t + 2].touchID = 27;
                        Game.picker[y][x].ground[i - 1][t + 2].groundID = 9;
                        Game.picker[y][x].touch[i][t - 2].touchID = 27;
                        Game.picker[y][x].ground[i][t - 2].groundID = 9;
                        Game.picker[y][x].touch[i][t - 1].touchID = -1;
                        Game.picker[y][x].ground[i][t - 1].groundID = 9;
                        Game.picker[y][x].touch[i][t].touchID = 57;
                        Game.picker[y][x].ground[i][t].groundID = 9;
                        Game.picker[y][x].touch[i][t + 1].touchID = -1;
                        Game.picker[y][x].ground[i][t + 1].groundID = 9;
                        Game.picker[y][x].touch[i][t + 2].touchID = 27;
                        Game.picker[y][x].ground[i][t + 2].groundID = 9;
                        Game.picker[y][x].touch[i + 1][t - 2].touchID = 27;
                        Game.picker[y][x].ground[i + 1][t - 2].groundID = 9;
                        Game.picker[y][x].touch[i + 1][t - 1].touchID = -1;
                        Game.picker[y][x].ground[i + 1][t - 1].groundID = 9;
                        Game.picker[y][x].touch[i + 1][t].touchID = -1;
                        Game.picker[y][x].ground[i + 1][t].groundID = 9;
                        Game.picker[y][x].touch[i + 1][t + 1].touchID = -1;
                        Game.picker[y][x].ground[i + 1][t + 1].groundID = 9;
                        Game.picker[y][x].touch[i + 1][t + 2].touchID = 27;
                        Game.picker[y][x].ground[i + 1][t + 2].groundID = 9;
                        Game.picker[y][x].touch[i + 2][t - 2].touchID = 27;
                        Game.picker[y][x].ground[i + 2][t - 2].groundID = 9;
                        Game.picker[y][x].touch[i + 2][t - 1].touchID = 27;
                        Game.picker[y][x].ground[i + 2][t - 1].groundID = 9;
                        Game.picker[y][x].touch[i + 2][t].touchID = -1;
                        Game.picker[y][x].ground[i + 2][t].groundID = 9;
                        Game.picker[y][x].touch[i + 2][t + 1].touchID = 27;
                        Game.picker[y][x].ground[i + 2][t + 1].groundID = 9;
                        Game.picker[y][x].touch[i + 2][t + 2].touchID = 27;
                        Game.picker[y][x].ground[i + 2][t + 2].groundID = 9;
                     } else {
                        Game.picker[y][x].ground[i - 1][t].groundID = 4;
                        Game.picker[y][x].touch[i - 1][t].touchID = 59;
                        Game.picker[y][x].ground[i][t].groundID = 4;
                        Game.picker[y][x].touch[i][t].touchID = 57;
                     }

                     i += 100000;
                     t += 100000;
                  }
               }
            }
         }
      }

      Game.inventory.invBarID[0] = 94;
      Game.inventory.invBarCount[0] = 1;
      Game.bar = new Bar();
   }
}

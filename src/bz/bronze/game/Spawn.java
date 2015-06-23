package bz.bronze.game;

import java.util.Random;

public class Spawn {

   public int spawnTime = 1000;
   public int spawnFrame = 0;
   public int deTime = 10000;
   public int deFrame = 0;


   public void newMob() {
      for(int i = 0; i < Game.mob.length; ++i) {
         if(!Game.mob[i].inGame) {
            if((new Random()).nextInt(100) > 50) {
               Game.mob[i] = new Bat();
            }

            Game.mob[i].newMob();
            if((new Random()).nextInt(100) > 10) {
               i += Game.mob.length + 1;
            }
         }
      }

   }

   public void removeMob() {
      for(int i = 0; i < Game.mob.length; ++i) {
         if(Game.mob[i].inGame && (new Random()).nextInt(100) < 24) {
            Game.mob[i] = new Mob();
            Game.mob[i].removeMob(i);
            i += Game.mob.length + 1;
         }
      }

   }

   public void spawnMob() {
      if(this.spawnFrame >= this.spawnTime) {
         this.newMob();
         this.spawnFrame = 0;
      } else {
         ++this.spawnFrame;
      }

      if(this.deFrame >= this.deTime) {
         this.removeMob();
         this.deFrame = 0;
      } else {
         ++this.deFrame;
      }

   }
}

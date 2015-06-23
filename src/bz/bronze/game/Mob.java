package bz.bronze.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Mob extends Rectangle implements Runnable {

   private static final long serialVersionUID = -5611859918080569281L;
   public int movingSpeed = 2;
   public int movingFrame = 0;
   public int animationSpeed = 5;
   public int animationFrame = 0;
   public int animationLength = 2;
   public int mWidth = 16;
   public int mHeight = 6;
   public int downward = 0;
   public int upward = 2;
   public int animation = 0;
   public int facing = 0;
   public int currentID = 0;
   public int dUp = 0;
   public int dDo = 1;
   public int dLe = 2;
   public int dRi = 3;
   public int dSt = 4;
   public int extraFrame = 0;
   public int extraTime = (new Random()).nextInt(100) + 100;
   public int direction;
   public int attackAway;
   public boolean sDir;
   public boolean inGame;
   public boolean enemy;
   public boolean attacking;
   public Rectangle mobAttack;
   public Particle[] splash;
   public long beginTime;
   public long timeTaken;
   public long timeLeft;
   static final int UPDATE_RATE = 25;
   static final long UPDATE_PERIOD = 4000000L;
   public int ups;
   public int upsF;
   public int hdFrame;
   public int hdTime;


   public Mob() {
      this.direction = this.dUp;
      this.attackAway = 300;
      this.sDir = false;
      this.inGame = false;
      this.enemy = false;
      this.attacking = false;
      this.splash = new Particle[10];
      this.ups = 27;
      this.upsF = 0;
      this.hdFrame = 0;
      this.hdTime = 20;

      for(int i = 0; i < this.splash.length; ++i) {
         this.splash[i] = new Particle();
      }

   }

   public void removeMob(int id) {
      Game.mob[id].inGame = false;
      //Game.mobThread[id].stop(); //Thread.stop() Deprecated
      Game.mobThread[id].interrupt();
   }

   public void newMob() {
      Game.mobThread[this.currentID] = new Thread(this);
      this.inGame = true;
      if((new Random()).nextInt(100) < 25) {
         this.setBounds(Frame.lWidth + this.mWidth + Game.sX, this.mHeight + Game.sY, this.mWidth, this.mHeight);
      } else if((new Random()).nextInt(100) < 25) {
         this.setBounds(-this.mWidth + Game.sX, -this.mHeight + Game.sY, this.mWidth, this.mHeight);
      } else if((new Random()).nextInt(100) < 25) {
         this.setBounds(Frame.lWidth + this.mWidth + Game.sX, this.mHeight + Frame.lHeight + Game.sY, this.mWidth, this.mHeight);
      } else {
         this.setBounds(-this.mWidth + Game.sX, -this.mHeight + Frame.lHeight + Game.sY, this.mWidth, this.mHeight);
      }

      Game.mobThread[this.currentID].start();
   }

   public void physic() {
      this.movement();
   }

   public void movement() {
      if(this.movingFrame >= this.movingSpeed) {
         if((new Random()).nextInt(100) > 33) {
            --Game.mob[this.currentID].y;
         } else if((new Random()).nextInt(100) > 33) {
            ++Game.mob[this.currentID].y;
         } else if((new Random()).nextInt(100) > 33) {
            --Game.mob[this.currentID].x;
         } else {
            ++Game.mob[this.currentID].x;
         }

         this.movingFrame = 0;
      } else {
         ++this.movingFrame;
      }

   }

   public void drawMob(Graphics g) {}

   public void run() {
      while(true) {
         this.beginTime = System.nanoTime();
         this.physic();

         for(int i = 0; i < this.splash.length; ++i) {
            if(this.splash[i].inGame) {
               this.splash[i].physics();
            }
         }

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

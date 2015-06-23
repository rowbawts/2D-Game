package bz.bronze.game;

import java.awt.Rectangle;

public class Ground extends Rectangle {

   private static final long serialVersionUID = 1L;
   public int groundID;
   public int lastBlockChance = 100;
   public Rectangle collision;


   public Ground(int x, int y, int blockSize, int AnY, int AnX) {
      this.setBounds(x * blockSize, y * blockSize, blockSize, blockSize);
      this.collision = this;
      this.groundID = 2;
   }
}

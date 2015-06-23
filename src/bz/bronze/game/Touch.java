package bz.bronze.game;

import java.awt.Rectangle;

public class Touch extends Rectangle {

   private static final long serialVersionUID = 1L;
   public int touchID;
   public int itemID;
   public int itemCount;
   public Rectangle collision;


   public Touch(int x, int y, int blockSize, int AnY, int AnX) {
      this.setBounds(x * blockSize, y * blockSize, blockSize, blockSize);
      this.collision = this;
      this.itemCount = 0;
      this.itemID = -1;
      this.touchID = -1;
   }
}

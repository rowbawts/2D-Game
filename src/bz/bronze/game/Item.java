package bz.bronze.game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Random;

public class Item extends Rectangle {

   private static final long serialVersionUID = 1L;
   public int itemID;
   public int itemSize = 32;
   public Rectangle pickupArea;
   public int pickupAreaSize = 50;
   public int moveFrame = 0;
   public int moveSpeed = 0;


   public Item() {
      this.setBounds(0, 0, 0, 0);
      this.pickupArea = new Rectangle(0, 0, 0, 0);
      this.itemID = -1;
   }

   public void createNewItem(Rectangle rec, int i) {
      this.setBounds(rec.x + (new Random()).nextInt(rec.width - 20), rec.y + (new Random()).nextInt(rec.height - 20), this.itemSize, this.itemSize);
      this.pickupArea.setBounds(this.x - this.pickupAreaSize, this.y - this.pickupAreaSize, this.width + this.pickupAreaSize * 2, this.height + this.pickupAreaSize * 2);
      this.itemID = i;
   }

   public void itemPhysic() {
      if(this.moveFrame >= this.moveSpeed) {
         Point pt1 = new Point(Game.character.x, Game.character.y);
         Point pt2 = new Point(Game.character.x + Game.character.width - 1, Game.character.y);
         Point pt3 = new Point(Game.character.x, Game.character.y + Game.character.height - 1);
         Point pt4 = new Point(Game.character.x + Game.character.width - 1, Game.character.y + Game.character.height - 1);
         if(this.pickupArea.contains(pt1) || this.pickupArea.contains(pt2) || this.pickupArea.contains(pt3) || this.pickupArea.contains(pt4)) {
            boolean pickup = true;
            if(this.x > Game.character.x + Game.character.width / 4) {
               --this.x;
               pickup = false;
            }

            if(this.x < Game.character.x + Game.character.width / 4) {
               ++this.x;
               pickup = false;
            }

            if(this.y > Game.character.y + Game.character.height / 4) {
               --this.y;
               pickup = false;
            }

            if(this.y < Game.character.y + Game.character.height / 4) {
               ++this.y;
               pickup = false;
            }

            if(pickup) {
               Game.inventory.addInventory(this.itemID);
               this.deleteItem();
            }
         }

         this.moveFrame = 0;
      } else {
         ++this.moveFrame;
      }

   }

   public void deleteItem() {
      this.itemID = -1;
      this.setBounds(0, 0, 0, 0);
      this.pickupArea.setBounds(0, 0, 0, 0);
   }

   public void drawItem(Graphics g) {
      g.drawImage(Game.scheme_item[this.itemID], this.x - Game.sX, this.y - Game.sY, this.width, this.height, (ImageObserver)null);
   }
}

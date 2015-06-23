package bz.bronze.game;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Fight {

   public boolean isFighting = false;
   public boolean isLoading = false;
   public int frame = 0;
   public int time = 40;
   public int lFrame = 0;
   public int lTime = 10;
   public int toolSize = 26;


   public void fightPhysic() {
      if(!this.isLoading) {
         if(this.frame >= this.time) {
            this.isFighting = false;
            this.isLoading = true;
            this.frame = 0;
         } else {
            ++this.frame;
         }
      } else if(this.lFrame >= this.lTime) {
         this.isFighting = true;
         this.isLoading = false;
         this.lFrame = 0;
      } else {
         ++this.lFrame;
      }

   }

   public void draw(Graphics g) {
      if(Game.character.frame == 3) {
         if(Game.inventory.invBarID[Game.inventory.invPosition] != -1) {
            g.drawImage(Game.scheme_item[Game.inventory.invBarID[Game.inventory.invPosition]], Game.character.x - Game.sX + Game.character.width / 2 - 8, Game.character.y - Game.sY - 20, this.toolSize, this.toolSize, (ImageObserver)null);
         }
      } else if(Game.character.frame == 0) {
         if(Game.inventory.invBarID[Game.inventory.invPosition] != -1) {
            g.drawImage(Game.scheme_item[Game.inventory.invBarID[Game.inventory.invPosition]], Game.character.x - Game.sX + Game.character.width / 2 - 8, Game.character.y + Game.character.height - Game.sY + 6, this.toolSize, this.toolSize, (ImageObserver)null);
         }
      } else if(Game.character.frame == 6) {
         if(Game.inventory.invBarID[Game.inventory.invPosition] != -1) {
            g.drawImage(Game.scheme_item[Game.inventory.invBarID[Game.inventory.invPosition]], Game.character.x - Game.sX - 20, Game.character.y - Game.sY + Game.character.height / 2 - 8, this.toolSize, this.toolSize, (ImageObserver)null);
         }
      } else if(Game.character.frame == 9 && Game.inventory.invBarID[Game.inventory.invPosition] != -1) {
         g.drawImage(Game.scheme_item[Game.inventory.invBarID[Game.inventory.invPosition]], Game.character.x + Game.character.width - Game.sX + 6, Game.character.y - Game.sY + Game.character.height / 2 - 8, this.toolSize, this.toolSize, (ImageObserver)null);
      }

   }
}

package bz.bronze.game;

public class Animation {

   public int liquidFrame = 0;
   public int liquidSpeed = 150;
   public int liquidAnimation = 0;
   public int liquidMaximumAnimation = 2;


   public void animateLiquid() {
      if(this.liquidFrame >= this.liquidSpeed) {
         if(this.liquidAnimation < this.liquidMaximumAnimation) {
            ++this.liquidAnimation;
         } else {
            this.liquidAnimation = 0;
         }

         this.liquidFrame = 0;
      } else {
         ++this.liquidFrame;
      }

   }
}

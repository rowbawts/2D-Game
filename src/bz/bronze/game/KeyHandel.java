package bz.bronze.game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class KeyHandel implements KeyListener, MouseMotionListener, MouseWheelListener, MouseListener {

   public int keyUpward = 87;
   public int keyDownward = 83;
   public int keyLeft = 65;
   public int keyRight = 68;
   public int keyInventory = 70;
   public int keyDrop = 81;
   public int changeView = 80;
   public int keyRun = 16;
   public int keyFight = 32;


   public void keyPressed(KeyEvent e) {
      if(!Game.inventory.isOpen && !Game.menu.isDead && !Game.fight.isFighting) {
         if(e.getKeyCode() == this.keyUpward && !Game.upwardDown) {
            Game.upwardDown = true;
            if(Game.downwardDown) {
               Game.downwardDown = false;
            }

            Game.character.frame = 3;
         }

         if(e.getKeyCode() == this.keyDownward && !Game.downwardDown) {
            Game.downwardDown = true;
            if(Game.upwardDown) {
               Game.upwardDown = false;
            }

            Game.character.frame = 0;
         }

         if(e.getKeyCode() == this.keyLeft && !Game.leftDown) {
            Game.leftDown = true;
            if(Game.rightDown) {
               Game.rightDown = false;
            }

            if(!Game.upwardDown && !Game.downwardDown) {
               Game.character.frame = 6;
            }
         }

         if(e.getKeyCode() == this.keyRight && !Game.rightDown) {
            Game.rightDown = true;
            if(Game.leftDown) {
               Game.leftDown = false;
            }

            if(!Game.upwardDown && !Game.downwardDown) {
               Game.character.frame = 9;
            }
         }

         if(e.getKeyCode() == this.keyDrop) {
            Game.inventory.dropItem(false, false, -1);
         }

         if(e.getKeyCode() == 27 && Game.inMenu && Game.menu.inEscape || e.getKeyCode() == 27 && !Game.inMenu && !Game.menu.inEscape) {
            if(!Game.menu.inEscape) {
               Game.menu.inEscape = true;
               Game.inMenu = true;
            } else {
               Game.menu.inEscape = false;
               Game.inMenu = false;
               Game.menu.inStats = false;
               Game.menu.inOptions = false;
            }
         }

         if(e.getKeyCode() == this.keyRun && Bar.energy > 0) {
            Game.character.hasShift = true;
         }

         if(e.getKeyCode() == this.keyFight) {
            Game.fight.isFighting = true;
         }
      }

      if(e.getKeyCode() == this.keyInventory) {
         if(Game.inventory.isOpen) {
            Game.inventory.isOpen = false;
         } else {
            Game.inventory.isOpen = true;
            Game.destroyTime.stopAll();
            Game.upwardDown = false;
            Game.downwardDown = false;
            Game.leftDown = false;
            Game.rightDown = false;
            Game.character.animation = 0;
         }
      }

      if(e.getKeyCode() == this.changeView) {
         if(Game.noGui) {
            Game.noGui = false;
         } else {
            Game.noGui = true;
         }
      }

      if(e.getKeyCode() == 49) {
         Game.inventory.invPosition = 0;
      }

      if(e.getKeyCode() == 50) {
         Game.inventory.invPosition = 1;
      }

      if(e.getKeyCode() == 51) {
         Game.inventory.invPosition = 2;
      }

      if(e.getKeyCode() == 52) {
         Game.inventory.invPosition = 3;
      }

      if(e.getKeyCode() == 53) {
         Game.inventory.invPosition = 4;
      }

      if(e.getKeyCode() == 54) {
         Game.inventory.invPosition = 5;
      }

      if(e.getKeyCode() == 55) {
         Game.inventory.invPosition = 6;
      }

   }

   public void keyReleased(KeyEvent e) {
      if(!Game.inventory.isOpen) {
         if(e.getKeyCode() == this.keyUpward) {
            Game.upwardDown = false;
            if(Game.leftDown) {
               Game.character.frame = 6;
            } else if(Game.rightDown) {
               Game.character.frame = 9;
            }

            this.checkForFrame();
         }

         if(e.getKeyCode() == this.keyDownward) {
            Game.downwardDown = false;
            if(Game.leftDown) {
               Game.character.frame = 6;
            } else if(Game.rightDown) {
               Game.character.frame = 9;
            }

            this.checkForFrame();
         }

         if(e.getKeyCode() == this.keyLeft) {
            Game.leftDown = false;
            this.checkForFrame();
         }

         if(e.getKeyCode() == this.keyRight) {
            Game.rightDown = false;
            this.checkForFrame();
         }

         if(e.getKeyCode() == this.keyRun) {
            Game.character.hasShift = false;
         }

         if(e.getKeyCode() == this.keyFight) {
            Game.fight.isFighting = false;
            Game.fight.isLoading = false;
         }
      }

   }

   public void keyTyped(KeyEvent e) {}

   public void checkForFrame() {
      if(!Game.upwardDown && !Game.downwardDown && !Game.leftDown && !Game.rightDown) {
         Game.character.animation = 0;
      }

   }

   public void mouseWheelMoved(MouseWheelEvent e) {
      if(!Game.inventory.isOpen) {
         if(e.getWheelRotation() < 0) {
            if(Game.inventory.invPosition > 0) {
               --Game.inventory.invPosition;
            } else {
               Game.inventory.invPosition = Game.inventory.invWidth - 1;
            }
         } else if(e.getWheelRotation() > 0) {
            if(Game.inventory.invPosition < Game.inventory.invWidth - 1) {
               ++Game.inventory.invPosition;
            } else {
               Game.inventory.invPosition = 0;
            }
         }
      }

   }

   public void mouseDragged(MouseEvent e) {
      Game.mse = new Point(e.getX() - 3, e.getY() - 25);
   }

   public void mouseMoved(MouseEvent e) {
      Game.mse = new Point(e.getX() - 3, e.getY() - 25);
   }

   public void mouseClicked(MouseEvent e) {}

   public void mouseEntered(MouseEvent e) {}

   public void mouseExited(MouseEvent e) {}

   public void mousePressed(MouseEvent e) {
      Game.inventory.checkInventory(e);
      if(Game.inMenu && !Game.inSplash) {
         Game.menu.buttonClicked();
      }

      if(e.getButton() == 3) {
         Game.destroyTime.chestPhysic();
      }

      if(e.getButton() == 1) {
         Game.mouseDown = true;
      } else if(e.getButton() == 3) {
         Game.bar.drinkRefill();
      }

   }

   public void mouseReleased(MouseEvent e) {
      Game.mouseDown = false;
      Game.destroyTime.stopAll();
   }
}

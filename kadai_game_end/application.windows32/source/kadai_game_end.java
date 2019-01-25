import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.*; 
import javax.swing.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class kadai_game_end extends PApplet {



abstract class Zoog{
  int x,y;
  int d1=1,d2=1;
  int c=6;
  boolean isLeftEyeBroken = false;
  boolean isRightEyeBroken = false;
  Zoog(int x0,int y0){
    x=x0;
    y=y0;
  }
  public abstract void move();
  public void hit(int hx,int hy){
    if(hx>=x-27 && hx<=x-11 && c>0){
      if(hy>=y-46 && hy<=y-14){
        isLeftEyeBroken = true;
      }
    }
    if(hx>=x+11 && hx<=x+27 && c>0){
      if(hy>=y-46 && hy<=y-14){
        isRightEyeBroken = true;
      }
    }
    c=c-1;
  
    
  }
  public boolean isDead(){
    if(isLeftEyeBroken){
      if(isRightEyeBroken){
        return true;
      }
    }
    return false;
  }
  public void display(){
    ellipseMode(CENTER);
    rectMode(CENTER);
    stroke(0);
    fill(175);
    rect(x,y,20,100);
    stroke(0);
    fill(255);
    ellipse(x,y-30,60,60);
    stroke(0);
    line(x-10,y+50,x-20,y+60);
    line(x+10,y+50,x+20,y+60);
    if(isLeftEyeBroken == false){
      fill(0);
      ellipse(x-19,y-30,16,32);
    }
    if(isRightEyeBroken == false){
      fill(0);
      ellipse(x+19,y-30,16,32);
    }
    if(c>0){
      text(c,30,30);
    }
    if(c<=0){
      text("You need to reload!Please push r key!",20,350);
    }
    if((keyPressed==true) && (key=='r')){
      c=6;
    }
  }
}
class NormalZoog extends Zoog{
  NormalZoog(int x0,int y0){
    super(x0,y0);
  }
  public void move(){
    x+=d1;
    y+=d2;
    if(x>=width || x<=0){
      d1=-d1;
    }
    if(y>=height || y<=0){
      d2=-d2;
    }
  }
}
class StoppingZoog extends Zoog{
  StoppingZoog(int x0,int y0){
    super(x0,y0);
  }
  public void move(){}
}
class SmartZoog extends Zoog{
  SmartZoog(int x0,int y0){
    super(x0,y0);
  }
  public void move(){
    if((mouseX<x+30 && mouseX>x-30)&&(mouseY<y+30 && mouseY>y-30)){
        x+=d1*5;
        y+=d2*5;
      }
      else{
        x+=d1*2;
        y+=d2*2;
      }
      if(x>width || x<=0){
      d1=-d1;
    }
    if(y>height || y<=0){
      d2=-d2;
    }
  }
}
class BossZoog extends Zoog{
    BossZoog(int x0,int y0){
    super(x0,y0);
  }
  public void move(){
    if((mouseX<x+30 && mouseX>x-30)&&(mouseY<y+30 && mouseY>y-30)){
        x+=d1*8;
        y+=d2*8;
      }
      else{
        x+=d1*3;
        y+=d2*3;
      }
      if(x>width || x<=0){
      d1=-d1;
    }
    if(y>height || y<=0){
      d2=-d2;
    }
  }
}
public void mouseClicked(){
    zoog.hit(mouseX,mouseY);
    zoog_2.hit(mouseX,mouseY);
    zoog_3.hit(mouseX,mouseY);
    if(extra==true){
    zoog_extra.hit(mouseX,mouseY);
    }
}
public void showWinningMessage(){
  textSize(32);
  text("You won!",width/2,height/2);
}
public void showLosingMessage(){
  textSize(32);
  text("You lose...",width/2,height/2);
}
Zoog zoog;
Zoog zoog_2;
Zoog zoog_3;
Zoog zoog_extra;
int timeCounter=0;
int limitTime=60;
int passedFrame=0;
int countDown=4;
boolean extra=false;
JPanel panel = new JPanel();
BoxLayout layout = new BoxLayout( panel, BoxLayout.Y_AXIS );
public void setup(){
  
  zoog = new NormalZoog(100,100);
  zoog_2 = new StoppingZoog(100,100);
  zoog_3 = new SmartZoog(100,100);
  zoog_extra = new BossZoog(100,100);
  panel.setLayout(layout);
  panel.add( new JLabel( "EXTRA STAGE\u51fa\u73fe\uff01\u6311\u6226\u3057\u307e\u3059\u304b\uff1f" ) );
}
public void draw(){
  background(128);
  if(countDown>0 && passedFrame<frameRate){
    passedFrame++;
    if(passedFrame>frameRate){
      fill(255);
      textSize(100);
      text(countDown-1,300,150);
      countDown--;
      passedFrame=0;
    }
  }
  if(countDown==0){
    if(zoog.isDead()==false){
      zoog.display();
      zoog.move();
    }
    if(zoog_2.isDead()==false){
      zoog_2.display();
      zoog_2.move();
    }
    if(zoog_3.isDead()==false){
      zoog_3.display();
      zoog_3.move();
    }
    timeCounter+=1;
    if(timeCounter%60==0){
      limitTime-=1;
    }
    fill(255);
    textSize(30);
    text(nf(limitTime,2),300,50);
  }
  if(zoog.isDead()==true && zoog_2.isDead()==true && zoog_3.isDead()==true){
    extra=true;
    if(zoog_extra.isDead()==false){
      zoog_extra.display();
      zoog_extra.move();
    }
    fill(255);
    textSize(30);
    text(nf(limitTime,2),300,50);
    if(zoog_extra.isDead()==true){
      showWinningMessage();
      noLoop();
    }
  }
  if(limitTime<1){
    showLosingMessage();
    noLoop();
  }
}
  public void settings() {  size(600,400); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "kadai_game_end" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

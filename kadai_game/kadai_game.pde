abstract class Zoog{
  int x,y;
  int directionx = 2;
  int directiony = 1;
  int c = 10;  
  boolean isLeftEyeBroken = false;
  boolean isRightEyeBroken = false;
  Zoog(int x0,int y0){
    x = x0;
    y = y0;
  }
  
  void hit(int hx, int hy){
  if((x-11>=hx && x-27<= hx && y-14>=hy && y-46<=hy) && c>0)
  {
    isLeftEyeBroken = true;
  }
  if((x+27>=hx && x+11<= hx && y-14>=hy && y-46<=hy) && c>0){
    isRightEyeBroken = true;
  }
  
  c = c-1;
      
  }
  
   void display(){
    ellipseMode(CENTER);
    rectMode(CENTER);
    stroke(0);
    fill(175);
    rect(x,y,20,100);
    stroke(0);
    fill(255);
    ellipse(x,y-30,60,60);
    fill(0);
    if(isLeftEyeBroken==false)
    {
      ellipse(x-19,y-30,16,32);
    }
    if(isRightEyeBroken==false)
    {
      ellipse(x+19,y-30,16,32);
    }
    stroke(0);
    line(x-10,y+50,x-20,y+60);
    line(x+10,y+50,x+20,y+60);
    
    if(c>0){
      text(c,30,30);
    }
    if(c<=0){
      text("rキーでリロードしてください",240,350);
    }
    if((keyPressed == true) && (key == 'r')){
    c = 10;
    }
    
  }
  
  abstract void move();
  
  boolean isDead(){
    if(isLeftEyeBroken == true && isRightEyeBroken == true)
    {
       return true;
    }
    else{
      return false;
    }
  }  
  
  void reload(){
  
  }
}

class NormalZoog extends Zoog{
  NormalZoog(int x0,int y0){
    super(x0,y0);
  }
  void move(){  
   x += directionx;
   y += directiony;
   if(x>=width||x<=0)
     directionx = -directionx;
   if(y>=height||y<=0)
     directiony = -directiony;
  }
}

class StoppingZoog extends Zoog{
  StoppingZoog(int x0,int y0){
    super(x0,y0);
  }
  void move(){  
  }
}

class SmartZoog extends Zoog{
  SmartZoog(int x0,int y0){
    super(x0,y0);
  }
  void move(){ 
    if((mouseX<x+30 && mouseX>x-30) && (mouseY<y+30 && mouseY>y-30)){
      x += 5*directionx;
      y += 5*directiony;
    }
    else{
      x += directionx;
      y += 2*directiony;
    }
   if(x>=width||x<=0)
     directionx = -directionx;
   if(y>=height||y<=0)
     directiony = -directiony;
  }
}

Zoog zoog1,zoog2,zoog3;

void setup(){
  size(600,400);
  zoog1 = new NormalZoog(100,100);
  zoog2 = new StoppingZoog(100,100);
  zoog3 = new SmartZoog(100,100);
}

void mouseClicked(){
  zoog1.hit(mouseX,mouseY);
  zoog2.hit(mouseX,mouseY);
  zoog3.hit(mouseX,mouseY);
  
}

void showWinningMessage(){
  text("You won!",300,200);
}



void draw(){
  
  background(128);
  if(zoog1.isDead()==false){ 
    zoog1.display();
    zoog1.move();
  }
  if(zoog2.isDead()==false){ 
    zoog2.display();
    zoog2.move();
  }
  if(zoog3.isDead()==false){ 
    zoog3.display();
    zoog3.move();
  }
  if(zoog1.isDead()==true && zoog2.isDead()==true && zoog3.isDead()==true){
    showWinningMessage();
    noLoop();
  }
}
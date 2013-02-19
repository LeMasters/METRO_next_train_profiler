import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class METRO_next_train_profiler extends PApplet {

PFont font;
String[] metroStationName;
int[] metroStationID;
int[] metroStationStatus;
int stationRecordID;
int textXPosition;
int textYPosition;

public void setup() {
  size(1024, 768);
  frameRate(3.5f);
  background(0xff401811);
  PImage metroLogo=loadImage("METROlogo.png");
  image(metroLogo,800,500,175,238);
  font=loadFont("HelveticaNeue.vlw");
  textFont(font,15);
  stroke(0);
  strokeWeight(3);
  smooth();
  makeKey();
  textXPosition=40;
  textYPosition=30;
  String metroStationFile="METRO Station Numbers.txt";
  String[] queryLines=loadStrings(metroStationFile);
  metroStationID=new int[queryLines.length];
  metroStationName=new String[queryLines.length];
  metroStationStatus=new int[queryLines.length];  
  for (int i=0;i<queryLines.length;i++) {
    String[] temp1=split(queryLines[i], ",");
    metroStationID[i]=PApplet.parseInt(temp1[0]);
    metroStationName[i]=temp1[1].trim();
    metroStationStatus[i]=0;
    // note that the record number is arbitrary!
    // it just unifies StationID and StationName.  But it is
    // always different than StationID.
  }
}

public void draw() {
  if (testStation(stationRecordID)==true) {
    if (metroStationStatus[stationRecordID]==1) {
      fill(0xffFFD700);
    } else {
//    if (metroStationStatus[stationRecordID]==2) {
      fill(0xffFF1111);
    }
  }
  else {
    fill(12);
  }
  ellipse(textXPosition-17,textYPosition-6,12,12);
  fill(0xff401811,128);
  noStroke();
  // we need to cover up the old station names because
  // processing doesn't do a great job of positioning text
  rect(textXPosition-2,textYPosition+5,275,-18);
  strokeWeight(3);
  stroke(0);
  fill(245);
  text(metroStationName[stationRecordID], textXPosition, textYPosition);
  textYPosition=textYPosition+22;
  if (textYPosition>height-30) {
    textYPosition=30;
    textXPosition=textXPosition+294;
  }
  if (stationRecordID<metroStationName.length-1) {
    stationRecordID++;
  } 
  else {
    stationRecordID=0;
    textXPosition=40;
    textYPosition=30;
  }
}
public void makeKey() {
  fill(0xffFFD700); //yeller
  ellipse(611,495,12,12);
  fill(0xffFF1111); //red
  ellipse(611,518,12,12);
  fill(240);
  text("Train arriving",628,500);
  text("Train boarding",628,522);
}
public boolean testStation(int recordNumber) {
  boolean isTrainInStation=false;
  String genericHTMLQuery="http://www.wmata.com/mobile/nexttrain/index.cfm?event=display.nextTrain&station_id=";  
  genericHTMLQuery=genericHTMLQuery+metroStationID[recordNumber];
  String[] queryLines=loadStrings(genericHTMLQuery);
  String queryResult=join(queryLines, "");
  String[] test1 = match(queryResult, ">ARR<");
  if (test1 != null) {
    isTrainInStation=true;
    metroStationStatus[recordNumber]=1;
  } 
  else {
    String[] test2 = match(queryResult, ">BRD<");
    if (test2 != null) {
      isTrainInStation=true;
      metroStationStatus[recordNumber]=2;
    }
  }
  return isTrainInStation;
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "METRO_next_train_profiler" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

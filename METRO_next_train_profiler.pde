PFont font;
String[] metroStationName;
int[] metroStationID;
int[] metroStationStatus;
int stationRecordID;
int textXPosition;
int textYPosition;

void setup() {
  size(1024, 768);
  frameRate(3.5);
  background(#401811);
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
    metroStationID[i]=int(temp1[0]);
    metroStationName[i]=temp1[1].trim();
    metroStationStatus[i]=0;
    // note that the record number is arbitrary!
    // it just unifies StationID and StationName.  But it is
    // always different than StationID.
  }
}

void draw() {
  if (testStation(stationRecordID)==true) {
    if (metroStationStatus[stationRecordID]==1) {
      fill(#FFD700);
    } else {
//    if (metroStationStatus[stationRecordID]==2) {
      fill(#FF1111);
    }
  }
  else {
    fill(12);
  }
  ellipse(textXPosition-17,textYPosition-6,12,12);
  fill(#401811,128);
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

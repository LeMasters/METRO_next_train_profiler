boolean testStation(int recordNumber) {
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

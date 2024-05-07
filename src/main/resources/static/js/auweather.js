console.log("Weather Map script loading ....");
//********************************[Set up valid client end]***************************************/
const mapUrl = "http://localhost:8080/weathermap/v1/dailytemp";
//********************************[Set up valid client end]***************************************/
const titleMsg = ' - weather condition for next 5 days...<br><hr width="50%">';
const errorMsg =
  ' - weather condition for next 5 days...<br><hr width="50%"> <font color="red">Error occurred....</font><br>';

//JQuery implementation
$(document).ready(function () {
  initLoad();
});
//load the idata
function initLoad() {
  $("a").click(function () {
    if ($(this).attr("id") == "src") {
      let isMiniTemp = $("#src_mini_temp").is(":checked");
      let isMaxTemp = $("#src_max_temp").is(":checked");
      let srcAlertTemp = $("#src_alert_temp").val();
      var srcCity = document.getElementById("src_city");
      if (isMiniTemp && srcAlertTemp.length > 0) {
        invokeMapAlertApi(
          srcCity.value,
          "container-1b",
          "src_mini",
          srcAlertTemp
        );
      } else if (isMaxTemp && srcAlertTemp.length > 0) {
        invokeMapAlertApi(
          srcCity.value,
          "container-1b",
          "src_max",
          srcAlertTemp
        );
      } else {
        // invokeMapApi(srcCity.value, "container-1b");
        invokeMapAlertApi(srcCity.value, "container-1b", "", "");
      }
    } else if ($(this).attr("id") == "dst") {
      let isMiniTemp = $("#dst_mini_temp").is(":checked");
      let isMaxTemp = $("#dst_max_temp").is(":checked");
      let dstAlertTemp = $("#dst_alert_temp").val();
      const dstCity = document.getElementById("dst_city");

      if (isMiniTemp && dstAlertTemp.length > 0) {
        invokeMapAlertApi(
          dstCity.value,
          "container-2b",
          "dst_mini",
          dstAlertTemp
        );
      } else if (isMaxTemp && dstAlertTemp.length > 0) {
        invokeMapAlertApi(
          dstCity.value,
          "container-2b",
          "dst_max",
          dstAlertTemp
        );
      } else {
        // invokeMapApi(dstCity.value, "container-2b");
        invokeMapAlertApi(dstCity.value, "container-2b", "", "");
      }
    }
  });
}

function invokeMapApi(reqCity, targetContainer) {
  var predata = {
    city: reqCity, //negative scenario : change city to name
  };
  var ajaxResult = $.ajax({
    type: "GET",
    url: mapUrl,
    data: predata,
    success: function (response, textStatus, jqXHR) {
      //let RecLength = jsonPath(response, "$.[*]");
      let weatherRecLength = jsonPath(response, "$.length");
      let weRecords = weatherRecordBuilder(weatherRecLength, response);
      let msgBuilder = reqCity + titleMsg + weRecords;
      document.getElementById(targetContainer).innerHTML = msgBuilder;
    },
    error: function (error) {
      console.log("Error Result 1: " + JSON.stringify(error));
      let errMsgBuilder = reqCity + errorMsg + JSON.stringify(error);
      document.getElementById(targetContainer).innerHTML = errMsgBuilder;
    },
  });
}

function invokeMapAlertApi(reqCity, targetContainer, aType, aValue) {
  var predata = {
    city: reqCity, //negative scenario : change city to name
  };
  var ajaxResult = $.ajax({
    type: "GET",
    url: mapUrl,
    data: predata,
    success: function (response, textStatus, jqXHR) {
      let alType = aType.substring(aType.indexOf("_") + 1);
      let weatherRecLength = jsonPath(response, "$.length");
      let weRecords = weatherRecordBuilder(
        weatherRecLength,
        response,
        alType,
        aValue
      );
      let msgBuilder = reqCity + titleMsg + weRecords;
      document.getElementById(targetContainer).innerHTML = msgBuilder;
    },
    error: function (error) {
      console.log("Error Result 1: " + JSON.stringify(error));
      let errMsgBuilder = reqCity + errorMsg + JSON.stringify(error);
      document.getElementById(targetContainer).innerHTML = errMsgBuilder;
    },
  });
}

function weatherRecordBuilder(weatherRecLength, response, aType, aValue) {
  var srcWeatherBuilder = "";
  for (let i = 0; i < weatherRecLength; i++) {
    // text += "The number is " + i + "<br>";
    let tempDateTime = "$.[" + i + "].tempDateTime";
    let temp = "$.[" + i + "].weatherBase.temp";
    let feelsLike = "$.[" + i + "].weatherBase.feelsLike";
    let tempMin = "$.[" + i + "].weatherBase.tempMin";
    let tempMax = "$.[" + i + "].weatherBase.tempMax";
    let pressure = "$.[" + i + "].weatherBase.pressure";
    let seaLevel = "$.[" + i + "].weatherBase.seaLevel";
    let grndLevel = "$.[" + i + "].weatherBase.grndLevel";
    let humidity = "$.[" + i + "].weatherBase.humidity";
    let tempKf7 = "$.[" + i + "].weatherBase.tempKf7";
    let weather = "$.[" + i + "].weatherBase.weather";
    let weatherDesc = "$.[" + i + "].weatherBase.weatherDesc";
    let windSpeed = "$.[" + i + "].weatherBase.windSpeed";
    let windDeg = "$.[" + i + "].weatherBase.windDeg";
    let windGust = "$.[" + i + "].weatherBase.windGust";
    let visibility = "$.[" + i + "].weatherBase.visibility";

    srcWeatherBuilder +=
      "<p align='left'>  Weather forecast &#9758; " +
      jsonPath(response, tempDateTime) +
      "</p>";
    srcWeatherBuilder += "Temperature : " + jsonPath(response, temp) + ";";
    srcWeatherBuilder +=
      "&nbsp; Feels like : " + jsonPath(response, feelsLike) + ";";
    srcWeatherBuilder +=
      "&nbsp; Temperature (Min) : " + jsonPath(response, tempMin) + ";";
    srcWeatherBuilder +=
      "&nbsp; Temperature (Max) : " + jsonPath(response, tempMax) + ";";
    srcWeatherBuilder +=
      "&nbsp; Pressure : " + jsonPath(response, pressure) + ";<br>";
    srcWeatherBuilder += "Sea Level : " + jsonPath(response, seaLevel) + ";";
    srcWeatherBuilder +=
      "&nbsp; GrndLevel : " + jsonPath(response, grndLevel) + ";";
    srcWeatherBuilder +=
      "&nbsp; Humidity : " + jsonPath(response, humidity) + ";";
    srcWeatherBuilder +=
      "&nbsp; Temperature Kf7 : " + jsonPath(response, tempKf7) + ";";
    srcWeatherBuilder +=
      "&nbsp; Weather : " + jsonPath(response, weather) + ";<br>";
    srcWeatherBuilder +=
      "Weather Desc : " + jsonPath(response, weatherDesc) + ";";
    srcWeatherBuilder +=
      "&nbsp; Wind Speed : " + jsonPath(response, windSpeed) + ";";
    srcWeatherBuilder +=
      "&nbsp; Wind Deg : " + jsonPath(response, windDeg) + ";";
    srcWeatherBuilder +=
      "&nbsp; Wind Gust : " + jsonPath(response, windGust) + ";";
    srcWeatherBuilder +=
      "&nbsp; Visibility : " + jsonPath(response, visibility) + ";<br>";
    if (aType == "mini" && jsonPath(response, temp) < aValue) {
      srcWeatherBuilder +=
        "&nbsp;<font color='red'> Warning!!! the minimum temperature as per the alert setting</font>";
    } else if (aType == "max" && jsonPath(response, temp) > aValue) {
      srcWeatherBuilder +=
        "&nbsp;<font color='red'> Warning!!! the Maximum temperature as per the alert setting</font>";
    }
    srcWeatherBuilder += "<hr width='100%'>";
  }
  return srcWeatherBuilder;
}

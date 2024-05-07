#Single Page Weather forecast application

##Project Description : 
Provides weather forecast for next 4 days for multiple Australian cities. It has a set of features,
1.) Light weight Single Page front end Application.
2.) Uses open weather API application(https://openweathermap.org/) as source feed system
3.) In-built with secure authentication mechanism using Auth0.
4.) Notification/Alert for temperature crossing the configured temperature limit during hot season.
5.) Provisioned with fault tolerance. 
6.) Clean build on both ends(front & back)

##Assumption :
Application utilise tools/technologies/
1.   Single Page Application - Web app
2.  Application compatible with chrome and safari
3. JKD 11+, SB 2.7.14 (Spring Core 5.3.29) &
4. User to feed the key inputs to the application such as Input: City Name, Output: Weather Summary, Min Temp, Max Temp.
5.  Application Version 0.1 : frontend and backend apps are combined with single pack jar [standalone app]
6. Application Version 0.2 : frontend and backend apps are combined and packed with docker instance [standalone container instance]
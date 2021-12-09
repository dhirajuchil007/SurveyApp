# SurveyApp

This is an open source app which can be used to do basic surveys. <br>
It supports multiple question types.

<img src="https://user-images.githubusercontent.com/11347648/145403370-1860efba-d359-4929-ba6e-62ccf2e3bf0e.jpg" width="200px"/>  
<img src="https://user-images.githubusercontent.com/11347648/145403449-7b2898df-eac8-4f2a-97bd-2f91baa0d2a2.jpg" width="200px"/> 
<img src="https://user-images.githubusercontent.com/11347648/145404136-ba82e9b5-1f5c-4b49-bc7a-cb113a3c4258.jpg" width="200px"/>
<img src="https://user-images.githubusercontent.com/11347648/145404161-8d1ad731-c52c-4575-928f-b73af5731855.jpg" width="200px"/>
<img src="https://user-images.githubusercontent.com/11347648/145404081-f847c741-0f16-4dbb-b8ab-a4ec4352616a.jpg" width="200px"/>



Steps to run the project:<br>
<ol>
<li> Clone/download zip</li>
<li> Change package name</li>
<li> Register this app in a Firebase project</li>
<li> In the firebase realtime db import the json below</li>
<li> Download and copy goole-services.json file into the app directory</li>
<li> Run th project</li>
</ol>

JSON for Firebase DB:
```
{
  "surveys" : {
    "-Mq5KNZo6w8pweDsRxCL" : {
      "questions" : [ {
        "options" : [ {
          "optionPosition" : 1,
          "optionText" : "Orange"
        }, {
          "optionPosition" : 2,
          "optionText" : "Tomato"
        }, {
          "optionPosition" : 3,
          "optionText" : "Grapes"
        }, {
          "optionPosition" : 4,
          "optionText" : "Potato"
        } ],
        "questionText" : "Select all the options that are fruits",
        "questionType" : 3
      }, {
        "options" : [ {
          "optionPosition" : 1,
          "optionText" : "3"
        }, {
          "optionPosition" : 2,
          "optionText" : "5"
        }, {
          "optionPosition" : 3,
          "optionText" : "7"
        }, {
          "optionPosition" : 4,
          "optionText" : "2"
        } ],
        "questionText" : "Which of these numbers is a divisor of 8",
        "questionType" : 1
      }, {
        "questionText" : "What is a color which is also a fruit",
        "questionType" : 2
      } ],
      "surveyId" : "-Mq5KNZo6w8pweDsRxCL",
      "surveyName" : "Sample Survey"
    }
  }
}
```


# SurveyApp

This is an open source app which can be used to do basic surveys. 
It supports multiple question types.

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


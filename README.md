# FB messages counter

Very simple tool that analyzes messages.htm file containg whole messaging history made on facebook (you can download yours here: https://www.facebook.com/settings).
At the moment it's just a proof of concept

## Features
+ Count threads
+ Count messages in every thread and display participants (when unable to fetch name (e.g. someone's profile is private or deleted) displays link that may open properly if you are logged in)

## Instructions
+ Place fbparser.jar and messages.htm in the same directory
+ Open command line inside the chosen directory, write ```java -jar fbparser.jar``` and hit Enter
+ If you don't have internet connection or block java on firewall you'll get only links to profiles
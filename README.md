# FB messages counter

Very simple tool that analyzes messages.htm file containg whole messaging history made on facebook (you can download yours here: https://www.facebook.com/settings).
To get friends' names it uses Facebook Graph API, you can get your access token here: https://developers.facebook.com/tools/explorer/

## Features
+ Count threads
+ Count messages in every thread and display participants. When unable to fetch name (someone's profile is deleted) displays link
+ Added caching friends' names so it's not necessary do download them every time (works much faster)
+ Added some support for Facebook Graph API so now it's required to paste access token during first launch (no more security check errors)

## Instructions
+ Place fbparser.jar and messages.htm in the same directory
+ Open command line inside the chosen directory, write ```java -jar fbparser.jar``` and hit Enter
+ If you don't have internet connection or block java on firewall you'll get only links to profiles
+ On first launch you have to paste access token, it will be stored in the "tokenfile" in the same directory, friends' names will be cached in the "friends" file
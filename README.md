# RunningMateRest
This is the backend section of my honours project. It works with the front end which was created using Flutter and is located: https://github.com/awrigh206/RunningMate. The source code for this project is publicly available on Github at: https://github.com/awrigh206/RunningMateRest.

## Function 
This project was created in order to provide a way for two people, running in tandem, against each other to communicate and to track the distance each had travelled. The tracking necessary for this to function is done by the front end Flutter mobile application, but this backend is necessary in order to link the users to each other.

## Architecture 
This project was created around the REST API standard, using Spring and Java, this project also made use of an integrated database to storeuser credentials (user name and password hash) in order to provide secure login and registration systems. All end points of this API are secured, requiring authentication of a valid user except for the login and regsitration endpoints to allow non-users to register. All endpoints are also secured using HTTPS, though this may cause some errors if you download and wish to use this software as it utilizes a self signed certificate which would need to be replaced with one signed by a Certificate Authority in order for the system to work with real world users.

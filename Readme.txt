Distributed Systems Programming Assignment Readme File:

High-level description of the assignment:
The assignment was to build a simple web server that listens on a port for incoming client requests, accepts the request, and allows a web browser to connect to the web server and retrieve the contents of the SCU homepage that was saved in the server. 

High-level description of my program: 
The command line has two arguments: the path to the document root and the port number. 

Firstly, the program creates a socket on the port and starts listening. A new thread is created to handle the request. 

The HTTPRequest takes two parameters: the socket and the path. (Path to ensure the document root path is checked for the files required)

The requestHandler firstly creates input and output streams. Input stream is to read the the request. Output stream is to write to the browser. 

The filename is acquired and then the file. 
For each file type, content-type is set. 

In case of no errors, 200 status code is set.
The response includes: Content-Type, Content-Length and Date headers. 

Errors 404, 403 and 400 are handled. 
404 is in case the file is not found. 
403 is in case the file exists however, permissions are not set. 
400 is in case the request is a bad request and cannot be run. 

The requested resource is written to the buffer and is displayed. 
The connection is closed. 


List of Submitted Files:
Project_Files
	MakeFile.txt
	MyServer.java
	Readme.txt
	ScriptFile
	Webserver_Files
		3735.html		5160_RHIANNON_GIDDENS_with_Francesco_Turrisi_Theyre_Calling_Me_Home.rev.1618347639.jpg		202104061656.js		202104061656(1).js		202104061657.css		202104061657(1).css		201955114523408		309795393777546		AAlvarez760x480.jpg		all.css		analytics.js		Bail_Bonds_-_Manhattan_48129008206.jpg		BasketballFans-small.jpg		btp.js		campaign-banner.png		campus-small.jpg		Cannabis-760.jpg		cultural-small.jpg		dancers-small.jpg		DSCN3763.JPG		embed-modal.html		embed.js		ENG.jpg		f.css		f.txt		f(1).txt		f(2).txt		f(3).txt		f(4).txt		f(5).txt		f(6).txt		f(7).txt		fbevents.js		gtm.js		impressions.js		index.html		insight.min.js		instant-notification.js		jquery.min.js		js		livewhale_52c95f5a534141e7a596d69c6aaa63ae.css		livewhale_c936cc2f8920a21bab2ccf4b25637c30.js		lwcw.js		SCU-Commencement-20190614-jgensheimer-5523.jpg		scu.css		scu.js		sdk.js		sdk(1).js		smokestack.jpg		Statman760x480.jpg		style-cf.css		Sydney-2.jpg		unnamed.jpg		unnamed(1).jpg
	

Instructions for running the program:
java MyServer -document_root PATH -port PORT_NUMBER
PATH: path to the document root where files are stored
PORT_NUMBER: port number that you want to use, this can be between 8000 and 9999.

To test the server, 
We can open a new terminal and type: 
telnet localhost PORT_NUMBER
GET /index.html /HTTP 1.0
The response will be the index.html file for the SCU homepage.

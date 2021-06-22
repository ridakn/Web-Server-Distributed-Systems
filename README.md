# Web-Server-Distributed-Systems
Built a functional web server for a distributed systems project.

# High-level description of the assignment:
The assignment was to build a simple web server that listens on a port for incoming client requests, accepts the request, and allows a web browser to connect to the web server and retrieve the contents of the SCU homepage that was saved in the server. 

# High-level description of my program: 
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

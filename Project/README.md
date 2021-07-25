
# Computer Network Lab Project

- [LAB WEBSITE](https://sites.google.com/a/iemcal.com/cs602-computernetworks/project2021)

## Objective

To implement a File Transfer service in Java using ARQ Protocols

 - Stop-and-Wait
 - Go-Back-N

## General Protocol Requirements

`Client File Request Format: `
```
REQUEST filename CRLF
```
with no spaces between REQUEST, filename and CRLF

`Server Message Format: `
```
RDT sequence_number payload CRLF
```
where the payload is a byte array of 512, and the sequence_number represents the consignment number and is an ascending number between 0 and 255 stored in 1 byte

At the very last consignment, the message format is as follows:

```
RDT sequence_number payload END CRLF
```

In the last consignment, the payload is less than, or equals to 512 bytes.

`Client Acknowledgement Format: `

```
ACK sequence_number CRLF
```

The sequence_number represents the consignment that the Client is expecting next, so it will be 1, 2, 3, 4, ... until the Client is notified of the last consignment.

Upon receiving the last consignment, the Client sends an ACK with sequence_number 0, waits for 500ms and terminates the connection.
Negative ACK is not required.

`Requirements for Stop-and-Wait: `

Time-Out = 30ms

`Requirements for Go-Back-N: `

Window Size = 4

Time-Out = 30ms

## Assignment Structure:

#### Assignment 1

| Question Number | Problem Statement  |
| :---------- | :----------------------------------- |
| #1 |  Stop-and-Wait ARQ Normal Operation  |

#### Assignment 2

| Question Number | Problem Statement  |
| :---------- | :------- |
|  #1 | Stop-and-Wait ARQ Normal Operation using RDT with Text File |
|  #2 | Stop-and-Wait ARQ with Lost Frame |
|  #3 | Stop-and-Wait ARQ with Lost Acknowledgement |
|  #4 | Stop-and-Wait ARQ with Lost Frame and Lost Acknowledgement |
|  #5 | Stop-and-Wait ARQ with Different File Types |


#### Assignment 3

| Question Number | Problem Statement  |
| :---------- | :------- |
| #1 |  Go-Back-N  |
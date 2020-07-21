# Appointment-Scheduling-App

## Overview
This is a project created for my Software II - Advanced Java Concepts course. The goal of this project is to build a database application based on a hypothetical scenario.
The application must meet all business requirements while incorporating exception control, localization APIs, collections, I/O, data manipulation, and the use of lambda expressions.

## Scenario
You have been hired by The Fernandes Group to create a schedule application for their phone counseling business. 
The Fernandes Group was founded by Dr. D. Fernandes a highly respected winner of the Nobel Prize in Psychology. 
In the course of his Nobel Prize winning research, Dr. Fernandes pioneered a revolutionary 1-on-1 phone counseling concept that is the basis of their business. 
The main office of The Fernandes Group is in Philadelphia, PA. While all the phone counselors work from their home offices. 
All of the home offices are in The United States. Due to legal and privacy concerns, all work and data connections can only be conducted within The United States. 
The Fernandes Group needs the application to work in English, Spanish, and German to accommodate counselors that work in those languages. 
The organization already has a MySQL database that you will need to use. The database is used for other systems and cannot be modified in anyway. 
A system analyst has created solution statements to implement in developing the application.

## Requirements
**A.** Create a GUI form for a counselor to login and that accepts an ID, password, and pin. Provide an appropriate error message if the login credentials are incorrect (e.g., “The ID, Password, and Pin do not match”). <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**a.** The login form and the message form must determine the user’s desktop language and display the text in the appropriate language (English, Spanish, and German). <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**b.** The login form needs to display the current time in main office and the current time from the user’s time zone.<br /><br />
**B.** Provide the ability to add, update, and delete patient records in the database, including name, phone number, state, and insurance provider.<br /> <br />
**C.** Provide the ability to add, update, and delete appointments, capturing the appointment data (including start time, appointment type, and &nbsp;&nbsp;&nbsp;&nbsp;notes) and a link the appointment to a specific patient record in the database. <br /><br />
**D.** Provide the ability to view the calendar by month, week, and bi-week (2 weeks). <br /><br />
**E.** Appointments could be stored in any time zone in the database but the application must provide the ability to determine the local desktop &nbsp;&nbsp;&nbsp;&nbsp;time zone/daylight saving time and automatically and consistently adjust the display of appointment times to match the local desktop time &nbsp;&nbsp;&nbsp;&nbsp;zone wherever appointments are displayed in the application (forms, reports etc.).<br /><br />
**F.** Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you &nbsp;&nbsp;&nbsp;&nbsp;must incorporate at least two different mechanisms of exception control (Try/Catch, GUI controls, alert messages etc.)<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**a.** scheduling an appointment on these holidays for the year chosen:<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**i.** Martin Luther King, Jr. Day (3rd Monday of January) <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**ii.** Memorial Day (last Monday of May)<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**iii.** Veterans Day (November 11th) <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**iv.** Thanksgiving and the day after (4th Thursday of November and the day after) <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**b.** scheduling overlapping appointments for the same counselor<br /> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**c.** scheduling overlapping appointments for the same patient <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**d.** scheduling an appointment outside of business hours (Monday through Friday, 9AM to 9PM Eastern Standard Time)<br /><br />  
**G.** Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment. <br /><br />
**H.** On successful login, create an alert message outlining all the appointments that the counselor has in the next 4 hours. If no appointments are scheduled the message must note that. <br /><br />
**I.** Provide the ability to generate and display on demand each of the following reports: a. count of appointments by type for the current year b. appointment totals by counselor for the current year c. appointment totals by state for the current year <br /><br />
**J.** Provide the ability to track user activity by recording timestamps for successful user logins and failed logins in two separate .txt files. One .txt for successful logins and one 
.txt for failed logins. Each new record should be appended to the log file, if the file already exists. 

# My Personal Project - Password Manager    

## Project Description

- What will the application do? <br>
The application will store all the passwords of the user. The application could also generate a password for the user. Furthermore, it will allow the user to retrieve all the details such as username, email and password for a particular site
<br>
<br>
- Who will use it? <br>
Although there are several password managers on the internet, most people (including me) don't trust them completely. However, this password manager is an application that does not store your data on the internet and is exclusive to a given user. If the user wants, they can store their passwords safely in some external hard drive. Furthermore, this project is open source so the user could alter the applications to their own needs. 
<br>
<br>
- Why is this project of interest to you? <br>
As a student, there are several websites that we need to create an account for. In such cases, I find computer generated passwords to be very secure and storing them safely to be highly convenient

## User Stories
- As a user, I want to be able to generate passwords 
- As a user, I want to be able to store a new password along with corresponding website, username and login id
- As a user, I want to be able to see all my stored passwords 
- As a user, I want to be able to search my password for a specific website
- As a user, I want to be able to edit the password, username and login id for a website
- As a user, I want to be able to delete a password
- As a user, I want to be able to store my passwords to a file when I quit the application
- As a user, I want to be able to load my stored passwords when I start the application

## Phase 4: Task 2
New password added for duckduckgo.com <br>
New password added for gradescope <br>
New password added for earth.com <br>
Password of length 15 generated <br>
New password added for reddit.com <br>
Password edited for earth.com <br>
Password deleted for gradescope <br>

## Phase 4: Task 3
### Reflection on Current Design
The current design on my project has several drawbacks. However, there are a <br>
few aspects of the current design that I think are implemented very well. Firstly, <br>
each class in the project represents only on specific concept. For example, the <br>
PasswordInfo class has fields and methods related to a single password and has <br>
nothing to do with a list. Similarly, the PasswordInfoList is centered around <br>
management of the list of PasswordInfo. Furthermore, PasswordApp class works <br>
exclusively on CUI whereas PasswordAppGUI works exclusively on GUI. <br>
In other words, the current design implements high cohesion <br>
<br>
However, high coupling is one of the major drawbacks of the current design. <br>
Changing the implementation of one of the methods in PasswordInfoList can <br>
can have a major impact on the implementation details of PasswordAppGUI <br> 
class. Currently, a change in one of the methods within the PasswordAppGUI <br>
causes a domino effect and requires changes in almost every method. 

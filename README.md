# Sport Center Web Application 💪 (GR1) <br> _Ready to get your workout in?_

## Project Scope
Our team of developers have been put in charge of creating a fully functional web application for a local sport center. In addition of this main task, we also have to deliver extensive documentation that includes requirements, use cases, details about the implementation process, and replicable testing procedures. The deployment of the application also requires the use of a local database.

<br>

**The product should be able to offer a seamless experience to three different type of users**
* The Owner: Manage classes, instructors, in addition of being able to manage the activities schedule, in addition of managing all the existing accounts.
* The Instructors: Propose new activities, request for updates of his own schedule, view assigned activities, and manage its own account.
* The Clients: View available activities and register for classes as long as there's no schedule conflict, and manage its own account.

**The system should support those use cases**
* Class management: Have a dynamic list of existing activity type, give the different users the possibilities to propose, validate and register for activities.
* Schedule organization: Every user have the possibility to see the sport center service offer by going on a schedule giving extensive information about the incoming activities.

**The system should support those functinalities in the back-end**
* Authentication: Secure login page for each account, use of different levels of data access among the accounts.
* Data management: Configuration of a local PostgreSQL database to store the data of users, activities, and schedule in an efficient manner.
* Testing: The final product should have a series of tests that executes periodically to ensure reliability, usability and consistency among the system. Different use cases are tested to verify that all the requirements are met.

***

## Tasks
### Deliverable 1
**The main goal of this deliverable was to think about the software design of the application and start the project on a good basis. To do so, it was required to use different techniques such as**
* Definition of requirements: Identification and documentation of functional and non-functional requirements related to the system.
* Domain modelling: Show the different relationships between every class of the system and make it visually appealing.
* Use case diagrams & detailed use cases: Illustrate the different use cases of the functionalities of the system by different actors.
* Configuration of the system: JDK 17, Spring Boot, Graddle & VS Code used among the team.
* Detailed use cases: Write short reports about every action that could occur during one of the previously mentioned use cases, and its exceptions.
* Persistence layer & testing: Use of JPA annotation & CRUD operations through a DAO. Write test cases using read/write operations on the local PostgreSQL database.
* Project management: Creation of communication channels (Discord, Instagram), writing of the README FILE, the Wiki page, the issues and the project and its multiple views.

<br>

### Deliverable 2
**The main goal of this deliverable is to implement the backend service of the application. This ultimately means setting up the business logic and creating numerous files to increase modularity.**
* Creation of the service, controller, dto and testing files.
* Ensure communication between the modelling, persistence and business layers. Ensure that it will eventually communicate with the front end.
* Apply the business logic and communication between model, service and controller. The business logic must be based on the functional requirements.
* Create unit and integration tests for services and controller methods. This is done with JUnit and Mockito.
* Run the tests and application with the gradle command to ensure functionality.
* Very detailed wiki on backend information and implementation.

<br>

> ### Deliverable 3
> *Released later in the development process*

***

## Team Members
| Name | GitHub Username| Deliverable 1 | Deliverable 2 | Deliverable 3 | Total Hours |
| :----: | :----------------:| :---: | :---: | :---: | :---: |
| Mathias Nahuel Pacheco Lemina |[mathias-pl](https://github.com/mathias-pl)| 10 | 21 | 31 | 62 |
| Patrick Zakaria |[Nomber1-1](https://github.com/Nomber1-1)| 10 | 23 | | |
| Anslean Albert Jeyaras |[GumballGB](https://github.com/GumballGB)| 10 | 21 | | |
| Fabian Saldana |[FabianSaldana](https://github.com/FabianSaldana)| 10 | 20 | | |
| Emilie Ruel |[emilieruel](https://github.com/emilieruel)| 10 | 22 | | |
| Andrew Nemr |[andrewnemr123](https://github.com/andrewnemr123)| 10 | 20 | | |

***

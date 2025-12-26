# Digital Life Decisions Engine

A GUI-based decision analysis system built using **JavaFX** and **Data Structures & Algorithms (DSA)** to help users make data-driven life decisions.

---

##  Project Overview

The **Digital Life Decisions Engine** is a desktop application designed to analyze daily decisions by tracking outcomes over time.  
By leveraging core DSA concepts such as **HashMaps**, **Stacks**, and **Lists**, the system enables users to evaluate options, undo actions, and identify the most successful choices based on historical data.

This project demonstrates practical implementation of **DSA**, **OOP**, and **Software Engineering principles** through a clean, modular architecture.

---

##  Key Objectives

- Provide a structured framework for decision tracking
- Apply Data Structures for real-world problem solving
- Implement undo functionality using stack-based rollback
- Persist user data using file serialization
- Follow modular and maintainable design practices

---

##  Technologies Used

- **Java**
- **JavaFX** (GUI)
- **Data Structures & Algorithms**
  - HashMap
  - Stack
  - ArrayList / List
- **File Handling & Serialization**
- **MVC-inspired Architecture**

---

##  Core Features

-  Add new decisions with multiple options  
-  Record success or failure outcomes  
-  Undo last recorded action  
-  Suggest the best option based on historical success  
-  View complete decision history  
-  Persistent storage across sessions  

---

##  Data Structures Usage

| Data Structure | Purpose |
|----------------|--------|
| `HashMap` | Stores decisions and maps options to success counts |
| `ArrayList` | Maintains ordered lists of decision options |
| `Stack` | Enables undo functionality by storing previous actions |

---

##  System Design

- **Model** → Decision data, outcomes, persistence logic  
- **View** → JavaFX GUI components  
- **Controller** → Handles user interactions and system logic  

The system follows a **clean separation of concerns**, improving readability, maintainability, and scalability.

---

##  Functional Requirements

- Create and manage decision profiles
- Add multiple options per decision
- Record outcomes (Success / Failure)
- Undo the most recent action
- Suggest best option using historical data
- Display complete decision history

---

##  Non-Functional Requirements

- User-friendly and intuitive interface
- Fast data retrieval with minimal latency
- Reliable data persistence
- Modular and maintainable codebase

---

##  Limitations

- File-based storage only (no database)
- Single-user desktop application
- Rule-based suggestion logic (not AI-powered)
- Limited scalability

---

##  Future Enhancements

- Database integration (MySQL / SQLite)
- Machine learning-based decision suggestions
- Multi-user support
- Advanced UI themes and animations
- Cloud-based data synchronization

---

##  Testing

- Manual testing of all use cases
- Validation of undo functionality
- Verification of data persistence across sessions
- GUI responsiveness and input validation

---

##  References

- Oracle Java Documentation  
  https://docs.oracle.com
- JavaFX Official Documentation  
  https://openjfx.io
- Data Structures & Algorithms Textbooks
- Software Engineering Principles  
  *(Pressman, Sommerville)*

---

##  Author

**Saiba Maka**  
BS Computer Science Student  
DSA & JavaFX Project

---

##  License

This project is created for **academic and learning purposes**.

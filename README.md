# Clinic Appointment Management System

A backend system designed to manage the complete appointment lifecycle of a medical clinic, including patients, doctors, medical specializations, doctor schedules, appointments, medical visits, and prescriptions.

The system is designed around real-world clinic business rules such as appointment conflict prevention, doctor availability, appointment status management, validation, authentication, and role-based authorization.

---

## Overview

The **Clinic Appointment Management System** provides a centralized platform for managing the clinic's operational and medical appointment data.

The system supports three main types of users:

* **Administrator**
* **Doctor**
* **Patient**

It is designed to replace manual processes such as phone calls, spreadsheets, and paper-based records with a structured backend system.

---

## Objectives

The main objectives of the system are to:

* Manage patient information.
* Manage doctor information.
* Organize doctors by medical specialization.
* Define and maintain doctor working schedules.
* Allow patients to book appointments.
* Prevent appointment conflicts.
* Manage appointment statuses and lifecycle.
* Record medical visit information.
* Manage prescriptions.
* Provide search, pagination, and sorting capabilities.
* Validate incoming requests consistently.
* Provide standardized error responses.
* Protect resources using authentication and role-based authorization.
* Provide documented APIs for future web and mobile applications.

---

## System Users

### Administrator

The administrator is responsible for managing the clinic's operational data.

Main responsibilities include:

* Managing doctors.
* Managing medical specializations.
* Managing patients when required.
* Reviewing appointments.
* Managing clinic information.
* Searching the system.
* Viewing operational information.

### Doctor

The doctor is responsible for providing medical services to patients.

Main responsibilities include:

* Viewing their profile.
* Viewing assigned appointments.
* Viewing scheduled appointments.
* Confirming appointments.
* Completing appointments.
* Marking patients as no-show.
* Reviewing relevant patient information.
* Recording medical visit information.
* Creating prescriptions.

### Patient

The patient is responsible for managing their appointments and accessing their medical history.

Main responsibilities include:

* Maintaining personal information.
* Browsing available doctors.
* Searching doctors by specialization.
* Viewing doctor availability.
* Booking appointments.
* Viewing personal appointments.
* Cancelling eligible appointments.
* Viewing previous medical visits.
* Viewing prescriptions associated with previous visits.

---

## Core Modules

The system is organized around the following main modules:

| Module                        | Responsibility                               |
| ----------------------------- | -------------------------------------------- |
| Patient Management            | Register and manage patient information      |
| Doctor Management             | Manage doctors and professional information  |
| Specialization Management     | Manage medical specializations               |
| Doctor Availability           | Define and maintain doctor working schedules |
| Appointment Management        | Create and manage appointments               |
| Medical Visits                | Record consultation outcomes                 |
| Prescriptions                 | Manage prescribed medications                |
| Authentication                | Authenticate registered users                |
| Authorization                 | Control access based on user roles           |
| Search and Pagination         | Efficiently retrieve and filter records      |
| Validation and Error Handling | Maintain consistent API behavior             |

---

## Appointment Management

Appointments follow a defined lifecycle.

```text
SCHEDULED
    |
    +----> CANCELLED
    |
    v
CONFIRMED
    |
    +----> CANCELLED
    |
    +----> NO_SHOW
    |
    v
COMPLETED
```

A newly created appointment starts with the `SCHEDULED` status.

Only valid status transitions are allowed. Invalid transitions, such as changing a `COMPLETED` appointment to `CANCELLED`, must be rejected.

---

## Appointment Availability

The initial version of the system uses a fixed appointment duration of **30 minutes**.

Available appointment slots are calculated according to:

1. Doctor working schedule.
2. Requested appointment date.
3. Existing active appointments.
4. Fixed appointment duration.

For example, if a doctor works from `09:00` to `12:00`, the theoretical appointment slots are:

```text
09:00
09:30
10:00
10:30
11:00
11:30
```

Already-booked slots must not appear as available, while cancelled appointments should release their previous time slots.

---

## Business Rules

The system enforces important business rules, including:

* Every important business record must have a unique system identifier.
* Patient email addresses must be unique.
* Doctor medical license numbers must be unique.
* Specialization names must not be duplicated.
* Every doctor must have a valid medical specialization.
* Appointments cannot be scheduled in the past.
* Doctors can only receive appointments during their defined working schedule.
* A doctor cannot have two active appointments at the same date and time.
* A patient cannot have two active appointments at the same date and time.
* Cancelled appointments do not occupy appointment slots.
* Appointment status changes must follow permitted business transitions.
* Medical visit information can only be created for an eligible completed appointment.
* Prescription information must belong to a valid medical visit.
* Unauthorized users must not access protected clinic information.
* Historical appointment information should be preserved where required instead of being automatically deleted.

---

## Validation

Incoming requests must be validated before being processed.

Validation rules include:

* Required fields cannot be empty.
* Email addresses must have a valid format.
* Dates must follow valid business rules.
* Date of birth cannot be in the future.
* Consultation fees cannot be negative.
* Years of experience cannot be negative.
* Appointment dates cannot be in the past.
* Invalid identifiers must not silently succeed.

Validation errors should provide understandable information to API consumers.

---

## Error Handling

The API should provide a consistent error response format.

The system handles cases such as:

* Patient not found.
* Doctor not found.
* Appointment not found.
* Specialization not found.
* Duplicate patient email.
* Duplicate doctor medical license.
* Doctor unavailable.
* Appointment conflict.
* Invalid appointment status transition.
* Invalid request data.

Error responses should provide useful information such as:

* Error timestamp.
* HTTP status.
* Error type.
* Human-readable message.
* Request path.

---

## Search, Pagination and Sorting

The system provides search capabilities for retrieving relevant clinic information efficiently.

Doctors can be searched using:

* Doctor name.
* Medical specialization.
* Minimum years of experience.

Appointments may be searched using:

* Date.
* Status.
* Doctor.
* Patient.

Large collections should support pagination using:

* Page number.
* Page size.

Selected lists should also support sorting, such as sorting doctors by:

* Name.
* Experience.
* Consultation fee.

---

## Authentication and Authorization

The completed system requires user authentication.

Passwords must never be stored as plain text.

The system supports the following roles:

```text
USER
DOCTOR
PATIENT
```

Access to protected resources depends on the authenticated user's role.

## Users must not be able to access protected information simply by changing identifiers in request URLs.

## Medical Visits and Prescriptions

After completing an eligible consultation, a doctor can record medical visit information including:

* Patient.
* Doctor.
* Related appointment.
* Diagnosis.
* Medical notes.
* Record creation date.

Each prescription belongs to a medical visit and may contain:

* Medication name.
* Dosage.
* Frequency.
* Treatment duration.
* Additional instructions.

A patient's medical history should allow retrieval of previous visits and associated prescriptions.

---

## Testing

Automated tests will cover important business scenarios, including:

* Valid patient registration.
* Duplicate patient email rejection.
* Valid appointment creation.
* Missing patient validation.
* Missing doctor validation.
* Past appointment rejection.
* Doctor double-booking prevention.
* Patient double-booking prevention.
* Appointment outside doctor working hours.
* Slot availability after appointment cancellation.
* Invalid appointment status transitions.
* Invalid medical visit creation.

The goal is to ensure that both successful and unsuccessful business scenarios are properly tested.

---

## API Documentation

The finished application will provide interactive API documentation covering:

* Available endpoints.
* Request parameters.
* Request bodies.
* Response formats.
* HTTP status codes.
* Authentication requirements.

---

## Technologies

The technology stack will be documented and updated as the implementation progresses.

* Java
* Spring Boot
* Maven
* RESTful APIs
* JPA / Hibernate
* Relational Database
* Bean Validation
* JUnit
* API Documentation

---

## Project Structure

```text
clinic/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── example/
│   │   │   │       └── clinic/
│   │   │   │           ├── controller/
│   │   │   │           │   └── PatientController.java
│   │   │   │           │
│   │   │   │           ├── dto/
│   │   │   │           │   ├── request/
│   │   │   │           │   │   ├── DoctorRequestDto.java
│   │   │   │           │   │   └── PatientRequestDto.java
│   │   │   │           │   │
│   │   │   │           │   └── response/
│   │   │   │           │       ├── DoctorResponseDto.java
│   │   │   │           │       └── PatientResponseDto.java
│   │   │   │           │
│   │   │   │           ├── exception/
│   │   │   │           │   └── Exception.java
│   │   │   │           │
│   │   │   │           ├── model/
│   │   │   │           │   ├── Doctor.java
│   │   │   │           │   ├── Patient.java
│   │   │   │           │   ├── Specialization.java
│   │   │   │           │   │
│   │   │   │           │   └── enums/
│   │   │   │           │       └── Gender.java
│   │   │   │           │
│   │   │   │           ├── repository/
│   │   │   │           │   ├── DoctorRepository.java
│   │   │   │           │   └── PatientRepository.java
│   │   │   │           │
│   │   │   │           └── service/
│   │   │   │               ├── DoctorService.java
│   │   │   │               └── PatientService.java
│   │   │   │
│   │   │   └── main/
│   │   │       └── java/
│   │   │           └── com/
│   │   │               └── example/
│   │   │                   └── clinic/
│   │   │                       └── repository/
│   │   │                           └── InMemoryRepository/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── clinic/
│                       └── ClinicApplicationTests.java
│
├── docs/
│   └── class-diagram.png
│
├── .gitignore
├── .gitattributes
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Class Diagram

The following class diagram represents the main classes, attributes, methods, and relationships of the Clinic Appointment Management System.

![Clinic Appointment Management System Class Diagram](docs/class-diagram.png)
---

## Getting Started

### Prerequisites

Make sure you have the following installed:

* Java
* Maven
* A relational database
* Git

### Clone the Repository

```bash
git clone <repository-url>
```

### Navigate to the Project

```bash
cd clinic-appointment-management-system
```

### Run the Application

```bash
./mvnw spring-boot:run
```

Setup instructions, environment variables, database configuration, and deployment instructions will be added as the project configuration is completed.

---

## Team

This project is developed as a collaborative backend engineering project.

### Contributors

* Abdo Nady
* Mai Ahmed
---

## Project Scope

This project is based on a defined **Business Requirements Document (BRD)** covering:

* Patient management.
* Doctor management.
* Medical specializations.
* Doctor availability.
* Appointment booking and management.
* Medical visits.
* Prescriptions.
* Search capabilities.
* Pagination and sorting.
* Validation.
* Error handling.
* Authentication.
* Authorization.
* API documentation.
* Automated testing.

---

## Project Status

**In Development**

The project is being developed incrementally, with implementation, testing, documentation, and architecture evolving throughout the development process.

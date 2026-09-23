# Clinic Appointment Management System — Use Case Specifications

Derived strictly from the Business Requirements Document (BRD) for the Clinic Appointment Management System. All content below is a transformation of the BRD requirements into UML-style Use Case Specifications; no functionality, actor, or business rule not supported by the BRD has been introduced.

---

## STEP 1 — Actors

### 1. Administrator
**Role:** Clinic staff member responsible for the clinic's operational data.
**Responsibilities (per BRD §3.1):** Register, view, and update patients; manage doctors and medical specializations; review appointments; manage clinic information; search the system; view operational information.

### 2. Doctor
**Role:** Medical professional who provides consultations to patients.
**Responsibilities (per BRD §3.2):** View own profile; view assigned/scheduled appointments; confirm, complete, and mark appointments as no-show; review patient information tied to an appointment; create medical visit records; create prescriptions.

### 3. Patient
**Role:** Individual who receives medical services from the clinic.
**Responsibilities (per BRD §3.3):** Maintain personal information; browse/search doctors; view doctor availability; book and cancel appointments; view own appointments, medical history, and prescriptions.

**Note on abstract actor:** The BRD states in §20 that "a user should log in using registered credentials" for all three roles (§20–21). Since authentication applies identically to Administrator, Doctor, and Patient, these three actors are modeled as specializations of an abstract actor, **Registered User**, solely for the purpose of the Login use case (see Step 5 — Generalization).

No other external actors (e.g., external systems, payment gateways) are described in the BRD as interacting with the system. "Clinic," "system," and "API consumer" are not actors — they refer to the system itself or to the developers who will consume its documented API (§22), not to a role that interacts with the running system's use cases.

---

## STEP 2 — Use Case List

| UC ID | Use Case Name | Primary Actor | Supporting Actor(s) |
|---|---|---|---|
| UC-01 | Register Patient | Administrator | None |
| UC-02 | View Patients | Administrator | None |
| UC-03 | Update Patient Information (Admin) | Administrator | None |
| UC-04 | Remove Patient | Administrator | None |
| UC-05 | Register Doctor | Administrator | None |
| UC-06 | View Doctors | Administrator | None |
| UC-07 | Update Doctor Information | Administrator | None |
| UC-08 | Remove Doctor | Administrator | None |
| UC-09 | Manage Medical Specializations | Administrator | None |
| UC-10 | Manage Doctor Availability | Administrator | Doctor (ambiguous, see Step 7) |
| UC-11 | Review Appointments (Admin) | Administrator | None |
| UC-12 | Manage Clinic Information | Administrator | None (Ambiguous in BRD — see notes) |
| UC-13 | View Doctor Profile | Doctor | None |
| UC-14 | View Appointments (Doctor) | Doctor | None |
| UC-15 | Confirm Appointment | Doctor | None |
| UC-16 | Complete Appointment | Doctor | None |
| UC-17 | Mark Appointment as No-Show | Doctor | None |
| UC-18 | View Patient Information (Doctor) | Doctor | None |
| UC-19 | Create Medical Visit Record | Doctor | None |
| UC-20 | Create Prescription | Doctor | None |
| UC-21 | Manage Personal Information (Patient) | Patient | None |
| UC-22 | Search Doctors | Patient | None |
| UC-23 | View Available Appointment Slots | Patient | Doctor (schedule data owner) |
| UC-24 | Book Appointment | Patient | None |
| UC-25 | View My Appointments (Patient) | Patient | None |
| UC-26 | Cancel Appointment | Patient | None |
| UC-27 | View Medical History | Patient | None |
| UC-28 | View Prescriptions | Patient | None |
| UC-29 | Login | Registered User (Administrator, Doctor, Patient) | None |

---

## STEP 3 — Full Use Case Specifications

### Use Case UC-01: Register Patient

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Add a new patient's information to the system so the patient can be scheduled for appointments.
**Brief Description:** The administrator enters a new patient's personal information, and the system validates and stores it as a new patient record.
**Trigger:** A new patient needs to be registered with the clinic.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.
- The patient is not already registered with the same email address.

**Postconditions:**
- A new patient record exists with a unique system identifier.
- The patient's registration date is recorded.

**Main Success Scenario:**
1. The administrator initiates patient registration and enters the patient's first name, last name, email, phone number, date of birth, and gender.
2. The system validates the submitted information.
3. The system confirms that the email address is not already used by another patient.
4. The system creates a new patient record with a unique identifier and registration date.
5. The system confirms successful registration and returns the newly created patient information.

**Alternative / Exception Flows:**

A1. Missing or invalid required information
1. The system detects missing required fields, an invalid email format, an invalid date of birth, or an invalid field length.
2. The system rejects the request and returns a validation error describing the problem.

A2. Future date of birth
1. The system detects that the submitted date of birth is in the future.
2. The system rejects the request with a validation error.

A3. Duplicate email address
1. The system detects that the submitted email already belongs to a registered patient.
2. The system rejects the registration and returns a duplicate-email error.

**Business Rules:**
- BR-01: The new patient must receive a unique system identifier.
- BR-02: Patient email addresses must be unique.

**Validation Rules:**
- Required fields must not be empty.
- Email address must be in a valid format.
- Date of birth must be a valid date and must not be in the future.
- Field lengths must be within allowed limits.

**Authorization / Permissions:**
- Only the Administrator role may register patients.

---

### Use Case UC-02: View Patients

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Retrieve the list of registered patients or the details of one specific patient.
**Brief Description:** The administrator retrieves either the full list of patients or a single patient's details.
**Trigger:** The administrator needs to review patient information.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.

**Postconditions:**
- The requested patient data is returned to the administrator without being modified.

**Main Success Scenario:**
1. The administrator requests either the list of all patients or a specific patient by identifier.
2. The system retrieves the matching patient information.
3. The system returns the patient list or the specific patient's details to the administrator.

**Alternative / Exception Flows:**

A1. Patient not found
1. The administrator requests a specific patient using an identifier that does not exist.
2. The system returns a clear "patient not found" error response.

**Business Rules:**
- BR-01: Every patient record has a unique system identifier used for retrieval.

**Validation Rules:**
- The supplied patient identifier, if provided, must reference an existing record.

**Authorization / Permissions:**
- Only the Administrator role may view the full patient list or arbitrary patient records through this use case (compare UC-25, where a Patient may view only personal data).

---

### Use Case UC-03: Update Patient Information (Admin)

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Correct or update an existing patient's stored information.
**Brief Description:** The administrator modifies an existing patient record's information.
**Trigger:** A patient's information needs to be corrected or updated by clinic staff.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.
- The target patient record exists.

**Postconditions:**
- The patient's stored information reflects the submitted changes.

**Main Success Scenario:**
1. The administrator selects an existing patient and submits updated information.
2. The system validates the submitted information.
3. The system verifies the patient record exists.
4. The system updates the patient record and confirms the change.

**Alternative / Exception Flows:**

A1. Patient not found
1. The system cannot locate a patient record matching the supplied identifier.
2. The system returns a "patient not found" error and does not apply any changes.

A2. Invalid submitted information
1. The system detects invalid information (e.g., invalid email format, invalid field length).
2. The system rejects the update and returns a validation error.

A3. Duplicate email
1. The updated email matches another patient's existing email address.
2. The system rejects the update and returns a duplicate-email error.

**Business Rules:**
- BR-02: Patient email addresses must remain unique after the update.

**Validation Rules:**
- Same validation constraints as patient registration apply to updated fields.

**Authorization / Permissions:**
- Only the Administrator role may perform this use case (compare UC-21, in which a Patient updates only their own information).

---

### Use Case UC-04: Remove Patient

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Remove a patient record when business rules allow it.
**Brief Description:** The administrator deletes a patient's record from the system.
**Trigger:** A patient record needs to be removed from the system.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.
- The target patient record exists.
- Business rules permit removal of this patient (see note below).

**Postconditions:**
- The patient record is removed from the active patient data.

**Main Success Scenario:**
1. The administrator selects a patient record for removal.
2. The system verifies the patient record exists.
3. The system verifies that removal is permitted under current business rules.
4. The system removes the patient record and confirms the removal.

**Alternative / Exception Flows:**

A1. Patient not found
1. The system cannot locate the requested patient.
2. The system returns a "patient not found" error.

A2. Removal not permitted
1. The system determines that removing this patient is not currently allowed (for example, due to associated historical records that must be preserved per BR-15).
2. The system rejects the removal request.

**Business Rules:**
- BR-15: Historical appointment information should be preserved where required instead of being automatically deleted.

**Validation Rules:**
- The patient identifier must reference an existing record.

**Authorization / Permissions:**
- Only the Administrator role may remove patients.

**Note — Ambiguous in BRD:** §4 states patients may be removed "when business rules allow it," but the BRD does not define the exact conditions under which removal is disallowed (e.g., whether a patient with appointment history can ever be removed, or only soft-deleted). This needs clarification.

---

### Use Case UC-05: Register Doctor

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Add a new doctor to the system with a valid specialization.
**Brief Description:** The administrator enters a new doctor's professional information, including their specialization, and the system validates and stores it.
**Trigger:** A new doctor joins the clinic and must be registered.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.
- The specialization to be assigned to the doctor already exists in the system.

**Postconditions:**
- A new doctor record exists with a unique system identifier and a valid, assigned specialization.

**Main Success Scenario:**
1. The administrator enters the doctor's first name, last name, email, phone, medical license number, years of experience, consultation fee, and medical specialization.
2. The system validates the submitted information.
3. The system verifies the medical license number is not already used by another doctor.
4. The system verifies the specified specialization exists.
5. The system creates the new doctor record with a unique identifier.
6. The system confirms successful registration and returns the doctor's information.

**Alternative / Exception Flows:**

A1. Invalid information
1. The system detects invalid consultation fee, invalid experience value, missing required fields, or invalid field lengths.
2. The system rejects the request with a validation error.

A2. Duplicate license number
1. The system detects that the license number is already registered to another doctor.
2. The system rejects the registration with a duplicate-license error.

A3. Specialization not found
1. The system cannot find the specified specialization.
2. The system rejects the registration with a "specialization not found" error.

**Business Rules:**
- BR-03: Doctor license numbers must be unique.
- BR-05: A doctor must have a valid medical specialization.

**Validation Rules:**
- Consultation fee must not be negative.
- Years of experience must not be negative.
- Required fields must not be empty; email must be valid format.

**Authorization / Permissions:**
- Only the Administrator role may register doctors.

---

### Use Case UC-06: View Doctors

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Retrieve the list of all doctors or the details of a specific doctor.
**Brief Description:** The administrator retrieves doctor records for review or reference.
**Trigger:** The administrator needs to review doctor information.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.

**Postconditions:**
- The requested doctor data is returned unmodified.

**Main Success Scenario:**
1. The administrator requests either the full list of doctors or a specific doctor by identifier.
2. The system retrieves the matching doctor information.
3. The system returns the result to the administrator.

**Alternative / Exception Flows:**

A1. Doctor not found
1. The requested doctor identifier does not match any record.
2. The system returns a "doctor not found" error.

**Business Rules:**
- BR-01: Every doctor record has a unique system identifier.

**Validation Rules:**
- The supplied doctor identifier, if provided, must reference an existing record.

**Authorization / Permissions:**
- Administrator role (compare UC-22, where a Patient browses/searches doctors with a patient-facing scope).

---

### Use Case UC-07: Update Doctor Information

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Update an existing doctor's stored information.
**Brief Description:** The administrator modifies an existing doctor's information, such as contact details, fee, experience, or specialization.
**Trigger:** A doctor's information needs to be corrected or changed.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.
- The target doctor record exists.

**Postconditions:**
- The doctor's stored information reflects the submitted changes.

**Main Success Scenario:**
1. The administrator selects an existing doctor and submits updated information.
2. The system validates the submitted information.
3. The system verifies the doctor record exists.
4. The system updates the record and confirms the change.

**Alternative / Exception Flows:**

A1. Doctor not found
1. The system cannot locate the doctor record.
2. The system returns a "doctor not found" error.

A2. Invalid information
1. The system detects an invalid consultation fee, invalid experience, or other invalid field.
2. The system rejects the update with a validation error.

A3. Duplicate license number
1. The updated license number matches another doctor's existing license number.
2. The system rejects the update.

**Business Rules:**
- BR-03: Doctor license numbers must remain unique.
- BR-05: A doctor must have a valid medical specialization.

**Validation Rules:**
- Consultation fee and experience must not be negative.

**Authorization / Permissions:**
- Only the Administrator role may update doctor information.

---

### Use Case UC-08: Remove Doctor

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Remove a doctor record when allowed.
**Brief Description:** The administrator deletes a doctor's record from the system.
**Trigger:** A doctor leaves the clinic or their record must otherwise be removed.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.
- The target doctor record exists.
- Removal is currently permitted under business rules.

**Postconditions:**
- The doctor record is removed from the active doctor data.

**Main Success Scenario:**
1. The administrator selects a doctor record for removal.
2. The system verifies the doctor exists.
3. The system verifies removal is permitted.
4. The system removes the doctor record and confirms the removal.

**Alternative / Exception Flows:**

A1. Doctor not found
1. The system cannot locate the requested doctor.
2. The system returns a "doctor not found" error.

A2. Removal not permitted
1. The system determines the doctor cannot currently be removed (e.g., due to active or historical appointments that must be preserved per BR-15).
2. The system rejects the removal request.

**Business Rules:**
- BR-15: Historical appointment information should be preserved where required.

**Validation Rules:**
- The doctor identifier must reference an existing record.

**Authorization / Permissions:**
- Only the Administrator role may remove doctors.

**Note — Ambiguous in BRD:** As with patient removal, §5 does not define exactly what conditions block doctor removal (e.g., existing future appointments). This needs clarification.

---

### Use Case UC-09: Manage Medical Specializations

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Maintain the clinic's list of medical specializations and retrieve doctors belonging to a specialization.
**Brief Description:** The administrator adds new specializations, views the list of specializations or a specific specialization, and retrieves the doctors associated with a given specialization.
**Trigger:** The clinic needs to add a new specialization, review existing ones, or find doctors within a specialization.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.

**Postconditions:**
- The specialization list reflects any additions made; requested specialization or doctor-by-specialization data is returned.

**Main Success Scenario:**
1. The administrator requests to add a specialization by submitting its name and description, or requests to view the specialization list, a specific specialization, or the doctors within a specialization.
2. If adding: the system verifies the specialization name is not already in use.
3. The system creates the new specialization record, or retrieves and returns the requested specialization or doctor information.
4. The system confirms the outcome to the administrator.

**Alternative / Exception Flows:**

A1. Duplicate specialization name
1. The administrator attempts to add a specialization whose name already exists.
2. The system rejects the request with a duplicate-name error.

A2. Specialization not found
1. The administrator requests a specific specialization, or doctors within a specialization, using a name/identifier that does not exist.
2. The system returns a "specialization not found" error.

**Business Rules:**
- BR-04: Specialization names must not be duplicated.

**Validation Rules:**
- Specialization name must not be empty and must not duplicate an existing name.

**Authorization / Permissions:**
- Only the Administrator role may add or manage specializations.

**Note:** Assigning a specialization to a specific doctor is performed as part of UC-05 (Register Doctor) and UC-07 (Update Doctor Information), since every doctor record requires a specialization field (BR-05); it is not modeled as a separate use case.

---

### Use Case UC-10: Manage Doctor Availability

**Primary Actor:** Administrator
**Supporting Actors:** Doctor (subject of the schedule; see ambiguity note)
**Goal:** Define and maintain a doctor's weekly working schedule so appointments can be booked only within valid periods.
**Brief Description:** The administrator defines the working days and time periods during which a doctor is available, and retrieves a doctor's schedule.
**Trigger:** A doctor's working schedule needs to be created or updated, or a schedule needs to be reviewed.

**Preconditions:**
- The actor is authenticated with sufficient permissions.
- The target doctor exists.

**Postconditions:**
- The doctor's working schedule is stored and available for use in appointment-slot calculations (UC-23) and booking validation (UC-24).

**Main Success Scenario:**
1. The actor submits one or more working periods for a doctor, each with a day of week, start time, and end time, or requests to view the doctor's existing schedule.
2. The system validates each submitted working period.
3. The system verifies the new period does not conflict with the doctor's existing availability.
4. The system stores the working schedule and confirms the outcome, or returns the requested schedule.

**Alternative / Exception Flows:**

A1. Invalid time information
1. The system detects that the end time is earlier than the start time, or that time values are otherwise invalid.
2. The system rejects the submitted working period with a validation error.

A2. Conflicting availability period
1. The system detects that the submitted period overlaps with an existing working period for the same doctor.
2. The system rejects the submitted period.

A3. Doctor not found
1. The referenced doctor does not exist.
2. The system returns a "doctor not found" error.

**Business Rules:**
- BR-07: Doctors may only receive appointments during their defined working schedule (this use case establishes the schedule that BR-07 depends on).

**Validation Rules:**
- End time must be later than start time.
- Time values must be valid.
- A new period must not conflict with existing periods for the same doctor.

**Authorization / Permissions:**
- Ambiguous in BRD (see note).

**Note — Ambiguous in BRD:** §7 describes the need to "maintain the working schedule of each doctor" but does not state which actor performs this maintenance. Neither §3.1 (Administrator) nor §3.2 (Doctor) explicitly lists defining/editing the schedule among their responsibilities. This use case is provisionally assigned to the Administrator, consistent with other operational-data management responsibilities in §3.1, but this needs clarification — it is equally plausible that doctors maintain their own schedules.

---

### Use Case UC-11: Review Appointments (Admin)

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Retrieve appointment information across the clinic for oversight purposes.
**Brief Description:** The administrator retrieves all appointments, a specific appointment, or appointments filtered by patient, doctor, date, or status.
**Trigger:** The administrator needs to review appointment activity.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.

**Postconditions:**
- The requested appointment data is returned unmodified.

**Main Success Scenario:**
1. The administrator requests appointments, optionally filtering by patient, doctor, date, or status.
2. The system retrieves the matching appointment records.
3. The system returns the results to the administrator.

**Alternative / Exception Flows:**

A1. Appointment not found
1. The administrator requests a specific appointment by an identifier that does not exist.
2. The system returns an "appointment not found" error.

**Business Rules:**
- BR-01: Every appointment has a unique system identifier.

**Validation Rules:**
- Any supplied filter values (date, status) must be well-formed.

**Authorization / Permissions:**
- The Administrator may access appointments across all doctors and patients, unlike Doctor (UC-14) and Patient (UC-25), whose access is restricted to their own appointments (§10, §21).

---

### Use Case UC-12: Manage Clinic Information

**Primary Actor:** Administrator
**Supporting Actors:** None
**Goal:** Maintain general clinic information.
**Brief Description:** The administrator manages clinic-level information as part of operational data.
**Trigger:** Clinic information needs to be created, viewed, or updated.

**Preconditions:**
- The administrator is authenticated with sufficient permissions.

**Postconditions:**
- Clinic information is stored/updated as submitted.

**Main Success Scenario:**
1. The administrator submits or requests clinic information.
2. The system processes and stores/returns the information.
3. The system confirms the outcome.

**Alternative / Exception Flows:**
- Not specified in the BRD.

**Business Rules:**
- Not specified in the BRD.

**Validation Rules:**
- Not specified in the BRD.

**Authorization / Permissions:**
- Only the Administrator role, per §3.1.

**Note — Ambiguous in BRD:** §3.1 lists "Manage clinic information" as an administrator responsibility, but no other section of the BRD defines what "clinic information" consists of (e.g., clinic name, address, hours, contact details), what operations are permitted, or what validation applies. This use case cannot be fully specified without clarification and is included here only as a placeholder required by traceability to §3.1.

---

### Use Case UC-13: View Doctor Profile

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** View one's own profile information.
**Brief Description:** The doctor retrieves their own stored profile information.
**Trigger:** The doctor wants to view their profile.

**Preconditions:**
- The doctor is authenticated.

**Postconditions:**
- The doctor's profile information is returned unmodified.

**Main Success Scenario:**
1. The doctor requests to view their profile.
2. The system retrieves the doctor's own record.
3. The system returns the profile information.

**Alternative / Exception Flows:**

A1. Unauthorized access attempt
1. The doctor attempts to view a profile other than their own.
2. The system rejects the request as unauthorized.

**Business Rules:**
- BR-14: Unauthorized users must not access protected clinic information.

**Validation Rules:**
- None beyond identity verification.

**Authorization / Permissions:**
- A Doctor may only view their own profile (§3.2, §21).

---

### Use Case UC-14: View Appointments (Doctor)

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** View appointments assigned to the doctor.
**Brief Description:** The doctor retrieves their assigned/scheduled appointments, optionally filtered (e.g., by date or status).
**Trigger:** The doctor wants to review their upcoming or past appointments.

**Preconditions:**
- The doctor is authenticated.

**Postconditions:**
- The doctor's relevant appointment data is returned unmodified.

**Main Success Scenario:**
1. The doctor requests their assigned appointments.
2. The system retrieves appointments where the doctor is the assigned physician.
3. The system returns the results to the doctor.

**Alternative / Exception Flows:**

A1. Appointment not found
1. The doctor requests a specific appointment identifier that does not exist or is not assigned to them.
2. The system returns an appropriate error.

**Business Rules:**
- None beyond retrieval scope.

**Validation Rules:**
- Any supplied filters must be well-formed.

**Authorization / Permissions:**
- A Doctor may access only appointments relevant to them, unless additional permissions are granted (§10, §21).

---

### Use Case UC-15: Confirm Appointment

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** Change an appointment's status from SCHEDULED to CONFIRMED.
**Brief Description:** The doctor confirms an appointment that was previously booked by a patient.
**Trigger:** The doctor reviews a SCHEDULED appointment and confirms it.

**Preconditions:**
- The doctor is authenticated.
- The appointment exists and is assigned to the doctor.
- The appointment is currently in SCHEDULED status.

**Postconditions:**
- The appointment status changes to CONFIRMED.

**Main Success Scenario:**
1. The doctor selects a SCHEDULED appointment and requests confirmation.
2. The system verifies the appointment exists and belongs to the doctor.
3. The system verifies the current status is SCHEDULED.
4. The system changes the appointment status to CONFIRMED and confirms the change.

**Alternative / Exception Flows:**

A1. Appointment not found
1. The referenced appointment does not exist.
2. The system returns an "appointment not found" error.

A2. Invalid status transition
1. The appointment is not currently in SCHEDULED status.
2. The system rejects the status change as an invalid transition.

**Business Rules:**
- BR-11: Appointment status changes must follow permitted business transitions (SCHEDULED → CONFIRMED is valid, §11).

**Validation Rules:**
- The appointment's current status must be SCHEDULED.

**Authorization / Permissions:**
- Only the assigned Doctor may confirm the appointment.

---

### Use Case UC-16: Complete Appointment

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** Change an appointment's status from CONFIRMED to COMPLETED after the consultation takes place.
**Brief Description:** The doctor marks an appointment as completed once the consultation has occurred.
**Trigger:** The doctor finishes a consultation with a patient.

**Preconditions:**
- The doctor is authenticated.
- The appointment exists and is assigned to the doctor.
- The appointment is currently in CONFIRMED status.

**Postconditions:**
- The appointment status changes to COMPLETED.
- The appointment becomes eligible for a medical visit record (UC-19).

**Main Success Scenario:**
1. The doctor selects a CONFIRMED appointment and requests completion.
2. The system verifies the appointment exists and belongs to the doctor.
3. The system verifies the current status is CONFIRMED.
4. The system changes the appointment status to COMPLETED and confirms the change.

**Alternative / Exception Flows:**

A1. Invalid status transition
1. The appointment is not currently in CONFIRMED status.
2. The system rejects the status change.

A2. Appointment not found
1. The referenced appointment does not exist.
2. The system returns an "appointment not found" error.

**Business Rules:**
- BR-11: CONFIRMED → COMPLETED is a valid transition (§11).

**Validation Rules:**
- The appointment's current status must be CONFIRMED.

**Authorization / Permissions:**
- Only the assigned Doctor may complete the appointment.

---

### Use Case UC-17: Mark Appointment as No-Show

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** Record that a patient did not attend a confirmed appointment.
**Brief Description:** The doctor marks a CONFIRMED appointment as NO_SHOW when the patient does not attend.
**Trigger:** The scheduled appointment time passes without the patient attending.

**Preconditions:**
- The doctor is authenticated.
- The appointment exists and is assigned to the doctor.
- The appointment is currently in CONFIRMED status.

**Postconditions:**
- The appointment status changes to NO_SHOW.

**Main Success Scenario:**
1. The doctor selects a CONFIRMED appointment where the patient did not attend and requests the no-show status.
2. The system verifies the appointment exists and belongs to the doctor.
3. The system verifies the current status is CONFIRMED.
4. The system changes the status to NO_SHOW and confirms the change.

**Alternative / Exception Flows:**

A1. Invalid status transition
1. The appointment is not currently in CONFIRMED status.
2. The system rejects the request.

A2. Appointment not found
1. The referenced appointment does not exist.
2. The system returns an "appointment not found" error.

**Business Rules:**
- BR-11: CONFIRMED → NO_SHOW is a valid transition (§11).

**Validation Rules:**
- The appointment's current status must be CONFIRMED.

**Authorization / Permissions:**
- Only the assigned Doctor may mark an appointment as no-show ("when appropriate," §3.2).

---

### Use Case UC-18: View Patient Information (Doctor)

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** Review a patient's information in the context of an appointment.
**Brief Description:** The doctor reviews information about a patient related to one of their appointments.
**Trigger:** The doctor prepares for or conducts a consultation and needs to review the patient's information.

**Preconditions:**
- The doctor is authenticated.
- The patient has an appointment assigned to this doctor.

**Postconditions:**
- The relevant patient information is returned unmodified.

**Main Success Scenario:**
1. The doctor selects an appointment and requests the related patient's information.
2. The system verifies the appointment is assigned to the doctor.
3. The system retrieves and returns the patient's information relevant to that appointment.

**Alternative / Exception Flows:**

A1. Unauthorized access
1. The doctor requests information about a patient with no appointment relationship to them.
2. The system rejects the request as unauthorized.

**Business Rules:**
- BR-14: Unauthorized users must not access protected clinic information.

**Validation Rules:**
- None beyond the appointment-relationship check.

**Authorization / Permissions:**
- A Doctor may view patient information only in connection with their own appointments (§3.2), distinct from the Administrator's broader access (UC-02).

---

### Use Case UC-19: Create Medical Visit Record

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** Record the outcome of a completed consultation.
**Brief Description:** After completing an appointment, the doctor records diagnosis and medical notes for that visit.
**Trigger:** The doctor has completed a consultation and needs to document its outcome.

**Preconditions:**
- The doctor is authenticated.
- The related appointment exists, is assigned to the doctor, and is in COMPLETED status.
- No medical visit record already exists for this appointment.

**Postconditions:**
- A medical visit record is created, linked to the patient, doctor, and appointment, with a creation date.

**Main Success Scenario:**
1. The doctor selects a completed appointment and enters diagnosis and medical notes.
2. The system verifies the appointment is COMPLETED and assigned to the doctor.
3. The system verifies no medical visit record already exists for this appointment.
4. The system creates the medical visit record with the current date and confirms the outcome.

**Alternative / Exception Flows:**

A1. Inappropriate appointment
1. The referenced appointment is not in COMPLETED status, or is not assigned to this doctor.
2. The system rejects the request.

A2. Duplicate medical record
1. A medical visit record already exists for this appointment.
2. The system rejects the creation of a second record for the same appointment.

A3. Appointment not found
1. The referenced appointment does not exist.
2. The system returns an "appointment not found" error.

**Business Rules:**
- BR-12: Medical visit information should only be created after an eligible (completed) appointment.

**Validation Rules:**
- Required visit fields (diagnosis, notes) must be provided.

**Authorization / Permissions:**
- Only the assigned Doctor may create the medical visit record.

---

### Use Case UC-20: Create Prescription

**Primary Actor:** Doctor
**Supporting Actors:** None
**Goal:** Prescribe one or more medications following a consultation.
**Brief Description:** The doctor records prescribed medications tied to a medical visit.
**Trigger:** The doctor decides to prescribe medication as part of or following a recorded medical visit.

**Preconditions:**
- The doctor is authenticated.
- A medical visit record exists for the relevant consultation.

**Postconditions:**
- One or more prescription records are created and linked to the medical visit.

**Main Success Scenario:**
1. The doctor selects a medical visit and enters one or more medications, each with dosage, frequency, treatment duration, and additional instructions.
2. The system verifies the referenced medical visit exists.
3. The system creates the prescription(s) linked to the medical visit and confirms the outcome.

**Alternative / Exception Flows:**

A1. Medical visit not found / invalid
1. The referenced medical visit does not exist.
2. The system rejects the request with an appropriate error.

A2. Invalid prescription information
1. Required prescription fields are missing.
2. The system rejects the request with a validation error.

**Business Rules:**
- BR-13: Prescription information must belong to a valid medical visit.

**Validation Rules:**
- Medication name, dosage, frequency, and treatment duration must be provided.

**Authorization / Permissions:**
- Only the assigned Doctor may create prescriptions.

---

### Use Case UC-21: Manage Personal Information (Patient)

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** View and update one's own personal information.
**Brief Description:** The patient maintains their own stored personal information.
**Trigger:** The patient wants to view or update their own information.

**Preconditions:**
- The patient is authenticated.

**Postconditions:**
- The patient's own information is retrieved or updated as requested.

**Main Success Scenario:**
1. The patient requests to view or update their own personal information.
2. The system verifies the request applies to the authenticated patient's own record.
3. The system validates any submitted changes.
4. The system returns or updates the patient's information and confirms the outcome.

**Alternative / Exception Flows:**

A1. Invalid submitted information
1. Submitted information fails validation (invalid email format, invalid date of birth, etc.).
2. The system rejects the update with a validation error.

A2. Unauthorized access attempt
1. The patient attempts to view or update another patient's information.
2. The system rejects the request as unauthorized.

**Business Rules:**
- BR-02: Patient email addresses must remain unique.
- BR-14: Unauthorized users must not access protected clinic information.

**Validation Rules:**
- Same field validations as patient registration.

**Authorization / Permissions:**
- A Patient may access only their own personal information; users must not be able to access another patient's record by altering an identifier (§21).

---

### Use Case UC-22: Search Doctors

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** Find doctors, particularly by medical specialization.
**Brief Description:** The patient browses available doctors or searches for doctors by specialization.
**Trigger:** The patient wants to find a suitable doctor for a consultation.

**Preconditions:**
- The patient is authenticated (or has access to browse doctors, per §3.3).

**Postconditions:**
- Matching doctor information is returned unmodified.

**Main Success Scenario:**
1. The patient browses available doctors or submits a specialization to search by.
2. The system retrieves doctors matching the criteria.
3. The system returns the matching doctor information to the patient.

**Alternative / Exception Flows:**

A1. No matching doctors / specialization not found
1. The submitted specialization does not exist or has no associated doctors.
2. The system returns an empty result or an appropriate error/message.

**Business Rules:**
- BR-05: A doctor must have a valid medical specialization (used as a search criterion).

**Validation Rules:**
- Specialization criterion, if supplied, must be well-formed.

**Authorization / Permissions:**
- A Patient may browse and search doctors (§3.3, §21).

**Note — Ambiguous in BRD:** §15 also mentions broader doctor search (by name and minimum years of experience) attributed generally to "clinic users," without specifying whether patients, administrators, or both may use these additional criteria. This needs clarification.

---

### Use Case UC-23: View Available Appointment Slots

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** View a doctor's available appointment time slots for a requested date.
**Brief Description:** The patient requests a doctor's available 30-minute appointment slots for a given date, based on the doctor's working schedule and existing active appointments.
**Trigger:** The patient wants to see when a doctor is available before booking.

**Preconditions:**
- The patient is authenticated.
- The target doctor exists and has a defined working schedule.

**Postconditions:**
- The set of currently available appointment slots for the requested date is returned unmodified.

**Main Success Scenario:**
1. The patient selects a doctor and a date and requests available appointment slots.
2. The system determines whether the doctor works on the requested day and retrieves the corresponding working period(s).
3. The system calculates the theoretical 30-minute slots within the working period(s).
4. The system excludes slots already occupied by active (non-cancelled) appointments.
5. The system returns the list of currently available slots.

**Alternative / Exception Flows:**

A1. Doctor does not work on the requested day
1. The doctor has no working period defined for the requested day.
2. The system returns an empty list of available slots (or an appropriate message).

A2. Doctor not found
1. The referenced doctor does not exist.
2. The system returns a "doctor not found" error.

**Business Rules:**
- BR-07: Doctors may only receive appointments during their defined working schedule.
- BR-10: Cancelled appointments do not occupy appointment slots.

**Validation Rules:**
- The requested date must be a valid date.

**Authorization / Permissions:**
- A Patient may view available slots for any doctor (§3.3).

---

### Use Case UC-24: Book Appointment

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** Reserve a specific appointment slot with a doctor.
**Brief Description:** The patient selects a doctor, date, time, and reason for visit, and the system validates and creates the appointment if all business rules are satisfied.
**Trigger:** The patient wants to schedule a consultation with a doctor.

**Preconditions:**
- The patient is authenticated.
- The patient has viewed the doctor's available slots (UC-23) or otherwise knows a candidate date/time.

**Postconditions:**
- A new appointment record exists with status SCHEDULED, a unique system identifier, and the selected date, time, and reason.

**Main Success Scenario:**
1. The patient selects a doctor, date, time, and reason for the visit, and submits the booking request.
2. The system verifies the patient exists.
3. The system verifies the doctor exists.
4. The system verifies the requested appointment is in the future.
5. The system verifies the doctor works on the requested day and that the requested time falls within the doctor's working schedule.
6. The system verifies the doctor has no other active appointment at the same date and time.
7. The system verifies the patient has no other active appointment at the same date and time.
8. The system creates the appointment with status SCHEDULED and confirms the booking.

**Alternative / Exception Flows:**

A1. Patient does not exist
1. The system cannot verify the requesting patient.
2. The system rejects the booking.

A2. Doctor does not exist
1. The referenced doctor does not exist.
2. The system rejects the booking with a "doctor not found" error.

A3. Appointment in the past
1. The requested date/time is not in the future.
2. The system rejects the booking.

A4. Doctor unavailable
1. The doctor does not work on the requested day, or the requested time falls outside the doctor's working schedule.
2. The system rejects the booking with a "doctor unavailable" error.

A5. Doctor double-booking
1. The doctor already has another active appointment at the same date and time.
2. The system rejects the booking with an appointment-conflict error.

A6. Patient double-booking
1. The patient already has another active appointment at the same date and time.
2. The system rejects the booking with an appointment-conflict error.

**Business Rules:**
- BR-06: Appointments cannot be scheduled in the past.
- BR-07: Doctors may only receive appointments during their defined working schedule.
- BR-08: A doctor cannot have two active appointments at the same time.
- BR-09: A patient cannot have two active appointments at the same time.

**Validation Rules:**
- Appointment date must be a valid future date.
- Reason for visit must be provided.

**Authorization / Permissions:**
- A Patient may book appointments for themselves (§3.3, §21).

**Relationship note:** This use case includes UC-23 (View Available Appointment Slots) as a mandatory step to determine a valid time to request (see Step 5).

---

### Use Case UC-25: View My Appointments (Patient)

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** View one's own appointments.
**Brief Description:** The patient retrieves their own appointment history and upcoming appointments.
**Trigger:** The patient wants to review their appointments.

**Preconditions:**
- The patient is authenticated.

**Postconditions:**
- The patient's own appointment data is returned unmodified.

**Main Success Scenario:**
1. The patient requests their appointments.
2. The system retrieves appointments belonging to the authenticated patient.
3. The system returns the results.

**Alternative / Exception Flows:**

A1. Appointment not found
1. The patient requests a specific appointment identifier that does not exist or does not belong to them.
2. The system returns an appropriate error.

**Business Rules:**
- None beyond retrieval scope.

**Validation Rules:**
- None beyond identity verification.

**Authorization / Permissions:**
- A Patient may see only their own appointments (§10, §21).

---

### Use Case UC-26: Cancel Appointment

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** Cancel an eligible upcoming appointment.
**Brief Description:** The patient cancels one of their own appointments; the appointment's slot becomes available again, and the appointment record is preserved for history.
**Trigger:** The patient can no longer attend a scheduled appointment.

**Preconditions:**
- The patient is authenticated.
- The appointment belongs to the patient.
- The appointment is in an eligible (cancellable) status.

**Postconditions:**
- The appointment's status changes to CANCELLED.
- The appointment's time slot becomes available for new bookings.
- The appointment record remains stored for historical purposes.

**Main Success Scenario:**
1. The patient selects one of their own appointments and requests cancellation.
2. The system verifies the appointment belongs to the patient.
3. The system verifies the appointment is currently in an eligible status for cancellation.
4. The system changes the appointment status to CANCELLED, releases its time slot, and confirms the cancellation.

**Alternative / Exception Flows:**

A1. Appointment not eligible for cancellation
1. The appointment is already COMPLETED (a completed appointment cannot normally be cancelled).
2. The system rejects the cancellation as an invalid status transition.

A2. Appointment not found
1. The referenced appointment does not exist or does not belong to the patient.
2. The system returns an appropriate error.

**Business Rules:**
- BR-10: Cancelled appointments do not occupy appointment slots.
- BR-11: Appointment status changes must follow permitted business transitions (e.g., SCHEDULED → CANCELLED and CONFIRMED → CANCELLED are valid; a COMPLETED appointment cannot normally be cancelled).
- BR-15: Historical appointment information should be preserved rather than deleted.

**Validation Rules:**
- The appointment must belong to the requesting patient.

**Authorization / Permissions:**
- A Patient may cancel only their own eligible appointments (§3.3, §12).

---

### Use Case UC-27: View Medical History

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** Review one's own past medical visits.
**Brief Description:** The patient retrieves their own medical visit history, including diagnosis and notes recorded by doctors.
**Trigger:** The patient wants to review their medical history.

**Preconditions:**
- The patient is authenticated.

**Postconditions:**
- The patient's own medical visit records are returned unmodified.

**Main Success Scenario:**
1. The patient requests their medical history.
2. The system retrieves medical visit records belonging to the authenticated patient.
3. The system returns the results.

**Alternative / Exception Flows:**

A1. No medical history available
1. The patient has no recorded medical visits.
2. The system returns an empty result.

**Business Rules:**
- BR-14: Unauthorized users must not access protected clinic information.

**Validation Rules:**
- None beyond identity verification.

**Authorization / Permissions:**
- A Patient may view only their own medical history (§3.3, §21).

---

### Use Case UC-28: View Prescriptions

**Primary Actor:** Patient
**Supporting Actors:** None
**Goal:** Review prescriptions associated with one's own past medical visits.
**Brief Description:** The patient retrieves the prescriptions tied to their medical history.
**Trigger:** The patient wants to review medications previously prescribed to them.

**Preconditions:**
- The patient is authenticated.

**Postconditions:**
- The patient's own prescription records are returned unmodified.

**Main Success Scenario:**
1. The patient requests prescriptions associated with their medical history.
2. The system retrieves prescriptions linked to the patient's medical visits.
3. The system returns the results.

**Alternative / Exception Flows:**

A1. No prescriptions available
1. The patient has no recorded prescriptions.
2. The system returns an empty result.

**Business Rules:**
- BR-13: Prescription information must belong to a valid medical visit.

**Validation Rules:**
- None beyond identity verification.

**Authorization / Permissions:**
- A Patient may view only prescriptions tied to their own medical visits (§3.3, §21).

---

### Use Case UC-29: Login

**Primary Actor:** Registered User (Administrator, Doctor, or Patient)
**Supporting Actors:** None
**Goal:** Authenticate with the system to gain access to role-appropriate functions.
**Brief Description:** A user submits their registered credentials, and the system verifies them and establishes a secure means of identifying the user in later requests.
**Trigger:** A user needs to access protected functionality.

**Preconditions:**
- The user has previously registered credentials with the system.

**Postconditions:**
- On success, the user is authenticated and the system can identify the user's role in subsequent requests.

**Main Success Scenario:**
1. The user submits their registered credentials.
2. The system verifies the credentials.
3. The system establishes a secure method of identifying the user for later requests.
4. The system confirms successful login.

**Alternative / Exception Flows:**

A1. Invalid credentials
1. The submitted credentials do not match any registered user, or the password is incorrect.
2. The system rejects the login attempt with an appropriate error.

**Business Rules:**
- Passwords must never be stored as plain text (§20).

**Validation Rules:**
- Credentials must be provided in the expected format.

**Authorization / Permissions:**
- Available to any Registered User (Administrator, Doctor, or Patient); the resulting role determines subsequent access (§21).

---

## STEP 4 — Requirements-to-Use-Case Traceability Matrix

| BRD Requirement / Business Rule | Related Use Case(s) | Notes |
|---|---|---|
| §4 Register/view/update/remove patient | UC-01, UC-02, UC-03, UC-04 | Fully covered |
| §5 Register/view/update/remove doctor | UC-05, UC-06, UC-07, UC-08 | Fully covered |
| §6 Manage specializations, assign to doctor, retrieve doctors by specialization | UC-09; assignment folded into UC-05/UC-07 | Covered |
| §7 Doctor working schedule | UC-10 | Actor ambiguous — see Step 7 |
| §8 Appointment booking validations | UC-24 | Fully covered |
| §9 Appointment duration and slot calculation | UC-23 | Fully covered |
| §10 Appointment retrieval | UC-11 (Admin), UC-14 (Doctor), UC-25 (Patient) | Split by actor scope, not merged |
| §11 Appointment status transitions | UC-15, UC-16, UC-17, UC-26 | Each transition mapped to the UC that triggers it |
| §12 Appointment cancellation | UC-26 | Fully covered |
| §13 Medical visit records | UC-19, UC-27 | Creation (Doctor) and retrieval (Patient) split by actor |
| §14 Prescriptions | UC-20, UC-28 | Creation (Doctor) and retrieval (Patient) split by actor |
| §15 Search (doctors, appointments) | UC-22 (doctor search by specialization); appointment search folded into UC-11/UC-14/UC-25 as filters | Broader doctor search (name, min experience) actor ambiguous — see Step 7 |
| §16 Pagination and sorting | Not a use case | Cross-cutting non-functional capability applied to list-returning use cases (UC-02, UC-06, UC-11, etc.), not a goal in itself |
| §17 Validation requirements | Embedded as Validation Rules in relevant UCs | Supporting rule, not a standalone use case |
| §18 Error handling / consistent error format | Embedded as Alternative/Exception Flows across all UCs | Cross-cutting non-functional requirement |
| §19 API response structure | Not a use case | Non-functional / technical response-format requirement |
| §20 Authentication | UC-29 | Fully covered |
| §21 Authorization by role | Embedded as Authorization/Permissions in each UC | Cross-cutting; BR-14 referenced throughout |
| §22 API documentation | Not a use case | Non-functional requirement aimed at developers, not an interactive actor goal |
| §23 Testing requirements | Not a use case | Verifies behavior of UC-01, UC-24, UC-15/16/17, UC-19, UC-26; a quality-assurance requirement, not a use case |
| §3.1 Manage clinic information | UC-12 | Ambiguous in BRD — insufficient detail to fully specify |
| BR-01 Unique identifiers | All creation UCs (UC-01, UC-05, UC-24, etc.) | Cross-cutting |
| BR-02 Unique patient email | UC-01, UC-03, UC-21 | Covered |
| BR-03 Unique doctor license | UC-05, UC-07 | Covered |
| BR-04 Unique specialization name | UC-09 | Covered |
| BR-05 Valid specialization required | UC-05, UC-07 | Covered |
| BR-06 No past appointments | UC-24 | Covered |
| BR-07 Appointments within working schedule | UC-10, UC-23, UC-24 | Covered |
| BR-08 No doctor double-booking | UC-24 | Covered |
| BR-09 No patient double-booking | UC-24 | Covered |
| BR-10 Cancelled appointments free the slot | UC-23, UC-26 | Covered |
| BR-11 Valid status transitions only | UC-15, UC-16, UC-17, UC-26 | Covered |
| BR-12 Medical visit only after completed appointment | UC-19 | Covered |
| BR-13 Prescription tied to valid medical visit | UC-20, UC-28 | Covered |
| BR-14 No unauthorized access | Embedded across all UCs | Cross-cutting |
| BR-15 Preserve historical data | UC-04, UC-08, UC-26 | Covered |

---

## STEP 5 — Use Case Relationships

| Relationship | From | To | Reason |
|---|---|---|---|
| <<include>> | UC-24 Book Appointment | UC-23 View Available Appointment Slots | Determining available slots is a mandatory, reusable step required every time an appointment is booked (§8–9); it is not optional or conditional. |
| <<extend>> | UC-20 Create Prescription | UC-19 Create Medical Visit Record | §14 states the doctor "may" prescribe medication after a consultation — this is explicitly optional and conditional on the doctor's decision, extending the base flow of recording a visit rather than being a mandatory reused step. |
| Generalization | Administrator, Doctor, Patient | Registered User (abstract) | All three concrete actors log in using the same credential-based mechanism (§20); the abstract actor exists solely to associate UC-29 (Login) without duplicating it three times. |

**Relationships considered but not included:**
- No <<include>> was added from every use case to UC-29 (Login) even though §20–21 imply authentication precedes all protected actions. Modeling this literally would require dozens of near-identical include arrows to every use case, which would add clutter without adding modeling insight; the authentication precondition is instead captured in each use case's "Preconditions" section. This is a documented modeling choice, not an omission of a BRD requirement.
- No <<extend>> was added between UC-16 (Complete Appointment) and UC-19 (Create Medical Visit Record), even though one follows the other. §13 does not describe the visit record as being created as an in-line optional branch of the "complete appointment" interaction; rather, it is a distinct, separately triggered goal performed afterward. The dependency is instead captured as a precondition on UC-19 ("the related appointment is in COMPLETED status").
- No Generalization was added among UC-15/UC-16/UC-17 (status-change use cases), since each has a distinct trigger, distinct precondition status, and distinct postcondition status; merging or generalizing them would obscure the individually testable transitions called out in §11 and §23.

---

## STEP 6 — UML Use Case Diagram Preparation

### Actors
- Administrator
- Doctor
- Patient
- Registered User (abstract; generalized by the three actors above, for UC-29 only)

### Use Cases
- UC-01 — Register Patient
- UC-02 — View Patients
- UC-03 — Update Patient Information (Admin)
- UC-04 — Remove Patient
- UC-05 — Register Doctor
- UC-06 — View Doctors
- UC-07 — Update Doctor Information
- UC-08 — Remove Doctor
- UC-09 — Manage Medical Specializations
- UC-10 — Manage Doctor Availability
- UC-11 — Review Appointments (Admin)
- UC-12 — Manage Clinic Information
- UC-13 — View Doctor Profile
- UC-14 — View Appointments (Doctor)
- UC-15 — Confirm Appointment
- UC-16 — Complete Appointment
- UC-17 — Mark Appointment as No-Show
- UC-18 — View Patient Information (Doctor)
- UC-19 — Create Medical Visit Record
- UC-20 — Create Prescription
- UC-21 — Manage Personal Information (Patient)
- UC-22 — Search Doctors
- UC-23 — View Available Appointment Slots
- UC-24 — Book Appointment
- UC-25 — View My Appointments (Patient)
- UC-26 — Cancel Appointment
- UC-27 — View Medical History
- UC-28 — View Prescriptions
- UC-29 — Login

### Actor → Use Case Associations
- Administrator → UC-01
- Administrator → UC-02
- Administrator → UC-03
- Administrator → UC-04
- Administrator → UC-05
- Administrator → UC-06
- Administrator → UC-07
- Administrator → UC-08
- Administrator → UC-09
- Administrator → UC-10
- Administrator → UC-11
- Administrator → UC-12
- Doctor → UC-10 (supporting; see ambiguity note)
- Doctor → UC-13
- Doctor → UC-14
- Doctor → UC-15
- Doctor → UC-16
- Doctor → UC-17
- Doctor → UC-18
- Doctor → UC-19
- Doctor → UC-20
- Patient → UC-21
- Patient → UC-22
- Patient → UC-23
- Patient → UC-24
- Patient → UC-25
- Patient → UC-26
- Patient → UC-27
- Patient → UC-28
- Registered User → UC-29

### <<include>> Relationships
- UC-24 → UC-23

### <<extend>> Relationships
- UC-20 → UC-19

### Generalization Relationships
- Administrator → Registered User
- Doctor → Registered User
- Patient → Registered User

---

## Potential Issues / Ambiguities in the BRD

1. **Doctor availability ownership (§7):** The BRD does not state whether the Administrator or the Doctor defines/edits the doctor's working schedule. This affects both the actor assignment for UC-10 and the actor → use case association in the diagram.

2. **"Manage" is used broadly without defined scope (§3.1, §5, §6):** "Manage doctors," "manage medical specializations," and "manage clinic information" are used as umbrella terms. Where the BRD later details specific operations (register/view/update/remove for doctors; add/view/assign for specializations), those operations were used to build precise use cases. For "manage clinic information" (§3.1), no further detail is given anywhere else in the document, so UC-12 remains a thin placeholder pending clarification of exactly what "clinic information" includes.

3. **Overlap between Administrator and Patient on patient data (§3.1 vs §3.3):** Both the Administrator ("Update patient information") and the Patient ("Maintain personal information") can modify patient records. The BRD does not clarify whether these are the same operation with different permission scopes, or whether patients can only edit a subset of fields (e.g., not registration date). UC-03 and UC-21 were modeled as separate use cases due to differing actors and likely differing scopes, but this should be confirmed.

4. **Broad doctor search criteria attributed to "clinic users" (§15):** Searching doctors by name or minimum years of experience is attributed generically to "clinic users," while §3.3 explicitly gives patients only "search by specialization." It is unclear whether administrators, patients, or both may use the full set of search criteria.

5. **Removal conditions for patients and doctors (§4, §5):** Both "Remove a patient when business rules allow it" and "Remove doctors when allowed" reference conditions that are never defined elsewhere in the BRD (e.g., whether existing appointment history blocks removal). UC-04 and UC-08 include this as an explicit precondition/exception flow, but the exact rule needs clarification.

6. **Medical history retrieval scope (§13):** "A patient's medical history should be retrievable" does not specify by whom. §3.3 confirms the Patient can view their own history (UC-27); it is not stated whether Doctors or Administrators can also retrieve a given patient's full medical history (as opposed to a doctor viewing patient information tied to one specific appointment, UC-18).

7. **Doctor confirmation vs. automatic confirmation:** The BRD lists "Confirm an appointment" as a doctor action (§3.2) and SCHEDULED → CONFIRMED as a valid transition (§11), but does not clarify whether confirmation is always a manual doctor action or could occur automatically under some condition. It was modeled here strictly as a manual doctor-triggered use case (UC-15), consistent with §3.2.

8. **Sequential vs. structural relationship between Complete Appointment, Medical Visit, and Prescription (§11, §13, §14):** These are clearly sequentially dependent (an appointment must be COMPLETED before a visit record can be created, and a visit record should exist before a prescription is created), but the BRD does not describe them as a single combined workflow. They were kept as separate use cases connected only through preconditions, per the modeling notes in Step 5, to avoid overstating a UML relationship not clearly justified by the text.

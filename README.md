# Mini Hospital Emergency Management System

**Student Name:** A.R. Mohammed Haazeem
**Student ID:** 23DA2-0476
**University:** SLTC (Sri Lanka Technological Campus)
**Module:** CIT300 - Data Structures and Algorithms and Individual Mid Assignment

## Project Description

This is a Java console application built for the CIT300 Individual Mid Assignment. It simulates a mini hospital emergency management system covering patient registration, the emergency waiting queue, treatment history, and patient visit history. All four assessed data structures are implemented manually using linked nodes, not Java's built-in collection classes.

## Folder Structure

```text
Mini Hospital Emergency Management System/
|-- README.md
`-- SRC/
    |-- Main.java
    |-- Patient.java
    |-- PatientBST.java
    |-- EmergencyQueue.java
    |-- TreatmentRecord.java
    |-- TreatmentStack.java
    |-- Visit.java
    |-- VisitLinkedList.java
    `-- DataStructureTests.java
```

## Implemented Requirements

### 1. Binary Search Tree - Patient Records

- Insert a patient (duplicate patient IDs are rejected)
- Search using patient ID
- Delete a patient
- Display patients in ascending patient-ID order using in-order traversal

### 2. Queue - Emergency Patients

- Enqueue a registered patient
- Dequeue the next patient for treatment
- Display all patients currently waiting
- Display the patient currently under treatment
- Handle an empty queue
- Follows FIFO order

### 3. Stack - Treatment History

- Push a completed treatment
- Pop (remove) the most recently completed treatment record
- Display treatment records, most recent first
- Handle an empty stack
- Follows LIFO order

### 4. Singly Linked List - Patient Visit History

- Add a visit (duplicate visit IDs for the same patient are rejected)
- Remove a visit
- Search for a visit
- Display all visits belonging to a selected patient

## How to Compile and Run

Open PowerShell inside the main project folder.

Compile all Java files:

```powershell
New-Item -ItemType Directory -Force OUT | Out-Null
javac -d OUT SRC\*.java
```

Run the hospital system:

```powershell
java -cp OUT Main
```

Run the tests:

```powershell
java -cp OUT DataStructureTests
```

Expected test result (fill in the actual count after running it yourself):

```text
All data-structure tests passed ([46] checks).
```

## Program Workflow

1. Register patients in the BST.
2. Add registered patients to the emergency queue.
3. Dequeue the next patient for treatment.
4. Complete the treatment.
5. The completed treatment is pushed to the stack.
6. The same treatment is added to the patient's visit linked list.

## Main Design Decisions

- Patient ID is the BST key, and duplicate patient IDs are rejected.
- The emergency queue uses `front` and `rear` nodes to maintain FIFO order.
- Only one patient can be under treatment at a time — the system blocks dequeuing a new patient until the current one's treatment is completed.
- The treatment stack uses a `top` node to maintain LIFO order.
- Every patient owns a separate singly linked list of visits, and duplicate visit IDs for the same patient are rejected.
- A patient cannot be deleted while waiting in the queue or under treatment.
- Invalid input, missing records, duplicate IDs, empty queue, and empty stack are all handled with clear messages instead of crashing.
- Java lists are used only to build display/test snapshots. The assessed data-structure operations themselves use manually implemented nodes.

## Time Complexity Summary

| Operation                  |                 Time complexity |
| -------------------------- | ------------------------------: |
| BST insert, search, delete |    Average O(log n), worst O(n) |
| BST in-order traversal     |                            O(n) |
| Queue enqueue              | O(n), including duplicate check |
| Queue dequeue              |                            O(1) |
| Stack push and pop         |                            O(1) |
| Visit-list append          | O(n), including duplicate check |
| Visit search and removal   |                            O(n) |

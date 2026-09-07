import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/** Console interface for the Mini Hospital Emergency Management System. */
public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final PatientBST patientTree = new PatientBST();
    private final EmergencyQueue emergencyQueue = new EmergencyQueue();
    private final TreatmentStack treatmentStack = new TreatmentStack();
    private Patient currentPatient;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        printBanner();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Choose an option: ", 0, 4);
            switch (choice) {
                case 1 -> patientRecordsMenu();
                case 2 -> emergencyQueueMenu();
                case 3 -> treatmentHistoryMenu();
                case 4 -> visitHistoryMenu();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Thank you for using the Mini Hospital System.");
    }

    private void printBanner() {
        System.out.println("============================================================");
        System.out.println("      MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM");
        System.out.println("============================================================");
    }

    private void printMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Patient Records");
        System.out.println("2. Emergency Waiting Queue");
        System.out.println("3. Treatment History");
        System.out.println("4. Patient Visit History");
        System.out.println("0. Exit");
    }

    private void patientRecordsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nPATIENT RECORDS");
            System.out.println("1. Insert a new patient");
            System.out.println("2. Search by patient ID");
            System.out.println("3. Delete a patient");
            System.out.println("4. Display patients in ascending ID order");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ", 0, 4)) {
                case 1 -> insertPatient();
                case 2 -> searchPatient();
                case 3 -> deletePatient();
                case 4 -> displayPatients();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void insertPatient() {
        int id = readInt("Patient ID: ", 1, Integer.MAX_VALUE);
        if (patientTree.search(id) != null) {
            System.out.println("A patient with ID " + id + " already exists.");
            return;
        }
        String name = readNonBlank("Patient name: ");
        int age = readInt("Age: ", 0, 130);
        String contact = readNonBlank("Contact number: ");
        String condition = readNonBlank("Medical condition: ");
        patientTree.insert(new Patient(id, name, age, contact, condition));
        System.out.println("Patient registered successfully.");
    }

    private void searchPatient() {
        int id = readInt("Patient ID to search: ", 1, Integer.MAX_VALUE);
        Patient patient = patientTree.search(id);
        System.out.println(patient == null ? "Patient not found." : "Found: " + patient);
    }

    private void deletePatient() {
        int id = readInt("Patient ID to delete: ", 1, Integer.MAX_VALUE);
        if (emergencyQueue.contains(id)
                || (currentPatient != null && currentPatient.getPatientId() == id)) {
            System.out.println("Cannot delete this patient while queued or under treatment.");
            return;
        }
        System.out.println(patientTree.delete(id)
                ? "Patient deleted successfully."
                : "Patient not found.");
    }

    private void displayPatients() {
        List<Patient> patients = patientTree.inOrderTraversal();
        if (patients.isEmpty()) {
            System.out.println("No patient records are available.");
            return;
        }
        System.out.println("\nPatients in ascending Patient ID order:");
        for (Patient patient : patients) {
            System.out.println("  " + patient);
        }
        System.out.println("Total patients: " + patientTree.size());
    }

    private void emergencyQueueMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nEMERGENCY PATIENT QUEUE");
            System.out.println("1. Enqueue a registered patient");
            System.out.println("2. Dequeue/call next patient for treatment");
            System.out.println("3. Display waiting patients");
            System.out.println("4. Display current patient under treatment");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ", 0, 4)) {
                case 1 -> enqueuePatient();
                case 2 -> callNextPatient();
                case 3 -> displayQueue();
                case 4 -> displayCurrentPatient();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void enqueuePatient() {
        int id = readInt("Registered patient ID: ", 1, Integer.MAX_VALUE);
        Patient patient = patientTree.search(id);
        if (patient == null) {
            System.out.println("Patient not found. Register the patient first.");
            return;
        }
        if (currentPatient != null && currentPatient.getPatientId() == id) {
            System.out.println("This patient is already under treatment.");
            return;
        }
        System.out.println(emergencyQueue.enqueue(patient)
                ? "Patient added to the emergency queue. Position: " + emergencyQueue.size()
                : "This patient is already waiting in the queue.");
    }

    private void callNextPatient() {
        if (currentPatient != null) {
            System.out.println("Complete the current patient's treatment before calling another patient.");
            System.out.println("Current: " + currentPatient);
            return;
        }
        currentPatient = emergencyQueue.dequeue();
        if (currentPatient == null) {
            System.out.println("The emergency queue is empty.");
        } else {
            System.out.println("Next patient called for treatment:");
            System.out.println("  " + currentPatient);
        }
    }

    private void displayQueue() {
        List<Patient> patients = emergencyQueue.toList();
        if (patients.isEmpty()) {
            System.out.println("The emergency queue is empty.");
            return;
        }
        System.out.println("\nWaiting order (front to rear):");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + patients.get(i));
        }
    }

    private void displayCurrentPatient() {
        System.out.println(currentPatient == null
                ? "No patient is currently under treatment."
                : "Currently under treatment: " + currentPatient);
    }

    private void treatmentHistoryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nTREATMENT HISTORY");
            System.out.println("1. Complete current patient's treatment");
            System.out.println("2. Remove most recent treatment record");
            System.out.println("3. Display completed treatments");
            System.out.println("4. Display current patient under treatment");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ", 0, 4)) {
                case 1 -> completeTreatment();
                case 2 -> popTreatment();
                case 3 -> displayTreatments();
                case 4 -> displayCurrentPatient();
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void completeTreatment() {
        if (currentPatient == null) {
            System.out.println("No patient is currently under treatment. Dequeue a patient first.");
            return;
        }

        String treatmentId = readNonBlank("Treatment record ID: ");
        if (treatmentStack.containsTreatmentId(treatmentId)) {
            System.out.println("That treatment record ID already exists.");
            return;
        }
        String visitId = readNonBlank("Visit ID: ");
        if (currentPatient.getVisitHistory().search(visitId) != null) {
            System.out.println("That visit ID already exists for this patient.");
            return;
        }
        String doctor = readNonBlank("Doctor name: ");
        String diagnosis = readNonBlank("Diagnosis: ");
        String treatment = readNonBlank("Treatment given: ");

        LocalDateTime completedAt = LocalDateTime.now();
        TreatmentRecord record = new TreatmentRecord(
                treatmentId,
                currentPatient.getPatientId(),
                currentPatient.getPatientName(),
                doctor,
                diagnosis,
                treatment,
                completedAt);
        Visit visit = new Visit(visitId, completedAt.toLocalDate(), doctor, diagnosis, treatment);

        treatmentStack.push(record);
        currentPatient.getVisitHistory().add(visit);
        System.out.println("Treatment completed and stored in both histories.");
        currentPatient = null;
    }

    private void popTreatment() {
        TreatmentRecord removed = treatmentStack.pop();
        if (removed == null) {
            System.out.println("The treatment stack is empty.");
        } else {
            System.out.println("Most recent treatment record removed:");
            System.out.println("  " + removed);
            System.out.println("Note: the patient's visit record is retained as medical history.");
        }
    }

    private void displayTreatments() {
        List<TreatmentRecord> records = treatmentStack.toList();
        if (records.isEmpty()) {
            System.out.println("The treatment stack is empty.");
            return;
        }
        System.out.println("\nTreatment records (top/newest to oldest):");
        for (int i = 0; i < records.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + records.get(i));
        }
    }

    private void visitHistoryMenu() {
        int patientId = readInt("Patient ID: ", 1, Integer.MAX_VALUE);
        Patient patient = patientTree.search(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        VisitLinkedList visits = patient.getVisitHistory();
        boolean back = false;
        while (!back) {
            System.out.println("\nVISIT HISTORY - " + patient.getPatientName()
                    + " (ID " + patient.getPatientId() + ")");
            System.out.println("1. Add a visit");
            System.out.println("2. Remove a visit");
            System.out.println("3. Search for a visit");
            System.out.println("4. Display visit history");
            System.out.println("0. Back");
            switch (readInt("Choose an option: ", 0, 4)) {
                case 1 -> addVisit(visits);
                case 2 -> removeVisit(visits);
                case 3 -> searchVisit(visits);
                case 4 -> displayVisits(visits);
                case 0 -> back = true;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addVisit(VisitLinkedList visits) {
        String visitId = readNonBlank("Visit ID: ");
        if (visits.search(visitId) != null) {
            System.out.println("That visit ID already exists for this patient.");
            return;
        }
        LocalDate date = readDate("Visit date (YYYY-MM-DD): ");
        String doctor = readNonBlank("Doctor name: ");
        String diagnosis = readNonBlank("Diagnosis: ");
        String treatment = readNonBlank("Treatment: ");
        visits.add(new Visit(visitId, date, doctor, diagnosis, treatment));
        System.out.println("Visit added successfully.");
    }

    private void removeVisit(VisitLinkedList visits) {
        String visitId = readNonBlank("Visit ID to remove: ");
        System.out.println(visits.remove(visitId)
                ? "Visit removed successfully."
                : "Visit not found.");
    }

    private void searchVisit(VisitLinkedList visits) {
        String visitId = readNonBlank("Visit ID to search: ");
        Visit visit = visits.search(visitId);
        System.out.println(visit == null ? "Visit not found." : "Found: " + visit);
    }

    private void displayVisits(VisitLinkedList visits) {
        List<Visit> history = visits.toList();
        if (history.isEmpty()) {
            System.out.println("This patient has no recorded visits.");
            return;
        }
        System.out.println("\nVisits (oldest entered to newest entered):");
        for (int i = 0; i < history.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + history.get(i));
        }
    }

    private int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number >= minimum && number <= maximum) {
                    return number;
                }
            } catch (NumberFormatException ignored) {
                // A clear validation message is printed below.
            }
            System.out.println("Enter a whole number between " + minimum + " and " + maximum + ".");
        }
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This value cannot be empty.");
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            String input = readNonBlank(prompt);
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException ignored) {
                System.out.println("Use a valid date in YYYY-MM-DD format.");
            }
        }
    }
}

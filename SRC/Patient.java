/** Patient data stored in the patient binary search tree. */
public class Patient {
    private final int patientId;
    private final String patientName;
    private final int age;
    private final String contactNumber;
    private final String medicalCondition;
    private final VisitLinkedList visitHistory;

    public Patient(int patientId, String patientName, int age,
                   String contactNumber, String medicalCondition) {
        if (patientId <= 0) {
            throw new IllegalArgumentException("Patient ID must be positive");
        }
        if (age < 0 || age > 130) {
            throw new IllegalArgumentException("Age must be between 0 and 130");
        }
        this.patientId = patientId;
        this.patientName = requireText(patientName, "Patient name");
        this.age = age;
        this.contactNumber = requireText(contactNumber, "Contact number");
        this.medicalCondition = requireText(medicalCondition, "Medical condition");
        this.visitHistory = new VisitLinkedList();
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value.trim();
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getAge() {
        return age;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public VisitLinkedList getVisitHistory() {
        return visitHistory;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Age: %d | Contact: %s | Condition: %s",
                patientId, patientName, age, contactNumber, medicalCondition);
    }
}

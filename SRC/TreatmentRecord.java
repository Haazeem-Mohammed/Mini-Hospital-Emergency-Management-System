import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/** Stores one completed treatment for the treatment-history stack. */
public class TreatmentRecord {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final String treatmentId;
    private final int patientId;
    private final String patientName;
    private final String doctorName;
    private final String diagnosis;
    private final String treatment;
    private final LocalDateTime completedAt;

    public TreatmentRecord(String treatmentId, int patientId, String patientName,
                           String doctorName, String diagnosis, String treatment,
                           LocalDateTime completedAt) {
        this.treatmentId = requireText(treatmentId, "Treatment ID");
        if (patientId <= 0) {
            throw new IllegalArgumentException("Patient ID must be positive");
        }
        this.patientId = patientId;
        this.patientName = requireText(patientName, "Patient name");
        this.doctorName = requireText(doctorName, "Doctor name");
        this.diagnosis = requireText(diagnosis, "Diagnosis");
        this.treatment = requireText(treatment, "Treatment");
        this.completedAt = Objects.requireNonNull(completedAt, "Completion time cannot be null");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value.trim();
    }

    public String getTreatmentId() {
        return treatmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    @Override
    public String toString() {
        return String.format(
                "Treatment ID: %s | Patient: %d - %s | Doctor: %s | Diagnosis: %s | Treatment: %s | Completed: %s",
                treatmentId, patientId, patientName, doctorName, diagnosis, treatment,
                completedAt.format(DISPLAY_FORMAT));
    }
}

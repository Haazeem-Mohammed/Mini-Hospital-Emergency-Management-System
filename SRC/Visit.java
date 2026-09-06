import java.time.LocalDate;
import java.util.Objects;

/** Stores one previous hospital visit. */
public class Visit {
    private final String visitId;
    private final LocalDate visitDate;
    private final String doctorName;
    private final String diagnosis;
    private final String treatment;

    public Visit(String visitId, LocalDate visitDate, String doctorName,
                 String diagnosis, String treatment) {
        this.visitId = requireText(visitId, "Visit ID");
        this.visitDate = Objects.requireNonNull(visitDate, "Visit date cannot be null");
        this.doctorName = requireText(doctorName, "Doctor name");
        this.diagnosis = requireText(diagnosis, "Diagnosis");
        this.treatment = requireText(treatment, "Treatment");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value.trim();
    }

    public String getVisitId() {
        return visitId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
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

    @Override
    public String toString() {
        return String.format("Visit ID: %s | Date: %s | Doctor: %s | Diagnosis: %s | Treatment: %s",
                visitId, visitDate, doctorName, diagnosis, treatment);
    }
}

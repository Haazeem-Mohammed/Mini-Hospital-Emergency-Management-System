import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A manually implemented FIFO queue for emergency patients. */
public class EmergencyQueue {
    private static class QueueNode {
        private final Patient patient;
        private QueueNode next;

        private QueueNode(Patient patient) {
            this.patient = patient;
        }
    }

    private QueueNode front;
    private QueueNode rear;
    private int size;

    /** Adds a patient at the rear. A patient cannot wait in the queue twice. */
    public boolean enqueue(Patient patient) {
        Objects.requireNonNull(patient, "Patient cannot be null");
        if (contains(patient.getPatientId())) {
            return false;
        }

        QueueNode newNode = new QueueNode(patient);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
        return true;
    }

    /** Removes and returns the patient at the front, or null when empty. */
    public Patient dequeue() {
        if (front == null) {
            return null;
        }
        Patient patient = front.patient;
        front = front.next;
        size--;
        if (front == null) {
            rear = null;
        }
        return patient;
    }

    public Patient peek() {
        return front == null ? null : front.patient;
    }

    public boolean contains(int patientId) {
        QueueNode current = front;
        while (current != null) {
            if (current.patient.getPatientId() == patientId) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public List<Patient> toList() {
        List<Patient> patients = new ArrayList<>();
        QueueNode current = front;
        while (current != null) {
            patients.add(current.patient);
            current = current.next;
        }
        return List.copyOf(patients);
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}

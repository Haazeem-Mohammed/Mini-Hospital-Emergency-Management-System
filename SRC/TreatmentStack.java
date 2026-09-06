import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A manually implemented LIFO stack for completed treatments. */
public class TreatmentStack {
    private static class StackNode {
        private final TreatmentRecord record;
        private StackNode next;

        private StackNode(TreatmentRecord record, StackNode next) {
            this.record = record;
            this.next = next;
        }
    }

    private StackNode top;
    private int size;

    public void push(TreatmentRecord record) {
        Objects.requireNonNull(record, "Treatment record cannot be null");
        top = new StackNode(record, top);
        size++;
    }

    /** Removes and returns the newest record, or null when empty. */
    public TreatmentRecord pop() {
        if (top == null) {
            return null;
        }
        TreatmentRecord record = top.record;
        top = top.next;
        size--;
        return record;
    }

    public TreatmentRecord peek() {
        return top == null ? null : top.record;
    }

    public boolean containsTreatmentId(String treatmentId) {
        if (treatmentId == null) {
            return false;
        }
        StackNode current = top;
        while (current != null) {
            if (current.record.getTreatmentId().equalsIgnoreCase(treatmentId.trim())) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /** Returns records from newest to oldest. */
    public List<TreatmentRecord> toList() {
        List<TreatmentRecord> records = new ArrayList<>();
        StackNode current = top;
        while (current != null) {
            records.add(current.record);
            current = current.next;
        }
        return List.copyOf(records);
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}

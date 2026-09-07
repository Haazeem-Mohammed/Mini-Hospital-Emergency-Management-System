import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A manually implemented singly linked list for one patient's visits. */
public class VisitLinkedList {
    private static class VisitNode {
        private final Visit visit;
        private VisitNode next;

        private VisitNode(Visit visit) {
            this.visit = visit;
        }
    }

    private VisitNode head;
    private VisitNode tail;
    private int size;

    /** Adds a visit at the end of the list. Duplicate visit IDs are rejected. */
    public boolean add(Visit visit) {
        Objects.requireNonNull(visit, "Visit cannot be null");
        if (search(visit.getVisitId()) != null) {
            return false;
        }

        VisitNode newNode = new VisitNode(visit);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    public Visit search(String visitId) {
        if (visitId == null) {
            return null;
        }
        VisitNode current = head;
        while (current != null) {
            if (current.visit.getVisitId().equalsIgnoreCase(visitId.trim())) {
                return current.visit;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(String visitId) {
        if (head == null || visitId == null) {
            return false;
        }

        String cleanId = visitId.trim();
        if (head.visit.getVisitId().equalsIgnoreCase(cleanId)) {
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return true;
        }

        VisitNode previous = head;
        VisitNode current = head.next;
        while (current != null) {
            if (current.visit.getVisitId().equalsIgnoreCase(cleanId)) {
                previous.next = current.next;
                if (current == tail) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /** Returns a read-only snapshot for displaying or testing the manual structure. */
    public List<Visit> toList() {
        List<Visit> visits = new ArrayList<>();
        VisitNode current = head;
        while (current != null) {
            visits.add(current.visit);
            current = current.next;
        }
        return List.copyOf(visits);
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }
}

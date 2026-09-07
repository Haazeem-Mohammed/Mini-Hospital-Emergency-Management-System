import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** Simple tests that run without external libraries. */
public class DataStructureTests {
    private static int checks;

    public static void main(String[] args) {
        testBinarySearchTree();
        testEmergencyQueue();
        testTreatmentStack();
        testVisitLinkedList();
        System.out.println("All data-structure tests passed (" + checks + " checks).");
    }

    private static void testBinarySearchTree() {
        PatientBST tree = new PatientBST();
        check(tree.isEmpty(), "New BST should be empty");
        int[] ids = {50, 30, 70, 20, 40, 60, 80};
        for (int id : ids) {
            check(tree.insert(patient(id)), "Insert patient " + id);
        }
        check(!tree.insert(patient(50)), "Reject duplicate patient ID");
        check(tree.size() == 7, "BST size is seven");
        check(tree.search(60) != null, "Find existing patient");
        check(tree.search(999) == null, "Missing patient returns null");
        check(patientIds(tree).equals(List.of(20, 30, 40, 50, 60, 70, 80)),
                "In-order traversal is ascending");
        check(tree.delete(20), "Delete leaf node");
        check(tree.delete(30), "Delete node with one child");
        check(tree.delete(70), "Delete node with two children");
        check(!tree.delete(999), "Reject deletion of missing node");
        check(patientIds(tree).equals(List.of(40, 50, 60, 80)),
                "BST remains ordered after deletions");
    }

    private static List<Integer> patientIds(PatientBST tree) {
        return tree.inOrderTraversal().stream().map(Patient::getPatientId).toList();
    }

    private static void testEmergencyQueue() {
        EmergencyQueue queue = new EmergencyQueue();
        Patient first = patient(1);
        Patient second = patient(2);
        Patient third = patient(3);
        check(queue.dequeue() == null, "Empty queue returns null");
        check(queue.enqueue(first), "Enqueue first patient");
        check(queue.enqueue(second), "Enqueue second patient");
        check(queue.enqueue(third), "Enqueue third patient");
        check(!queue.enqueue(first), "Reject duplicate queue patient");
        check(queue.peek() == first, "Peek returns front patient");
        check(queue.dequeue() == first, "FIFO first patient");
        check(queue.dequeue() == second, "FIFO second patient");
        check(queue.dequeue() == third, "FIFO third patient");
        check(queue.isEmpty(), "Queue empty after all dequeues");
    }

    private static void testTreatmentStack() {
        TreatmentStack stack = new TreatmentStack();
        TreatmentRecord first = treatment("T1", 1, 9);
        TreatmentRecord second = treatment("T2", 2, 10);
        check(stack.pop() == null, "Empty stack returns null");
        stack.push(first);
        stack.push(second);
        check(stack.size() == 2, "Stack size is two");
        check(stack.containsTreatmentId("t2"), "Find treatment ID without case sensitivity");
        check(!stack.containsTreatmentId("missing"), "Missing treatment ID returns false");
        check(stack.peek() == second, "Peek returns newest treatment");
        check(stack.pop() == second, "LIFO newest treatment first");
        check(stack.pop() == first, "LIFO oldest treatment second");
        check(stack.isEmpty(), "Stack empty after all pops");
    }

    private static void testVisitLinkedList() {
        VisitLinkedList visits = new VisitLinkedList();
        Visit first = visit("V1");
        Visit second = visit("V2");
        Visit third = visit("V3");
        check(visits.add(first), "Add first visit");
        check(visits.add(second), "Add second visit");
        check(visits.add(third), "Add third visit");
        check(!visits.add(visit("V2")), "Reject duplicate visit ID");
        check(visits.search("v2") == second, "Search ignores ID case");
        check(visits.remove("V1"), "Remove head visit");
        check(visits.remove("V2"), "Remove middle visit");
        check(visits.remove("V3"), "Remove tail visit");
        check(!visits.remove("missing"), "Reject removal of missing visit");
        check(visits.isEmpty() && visits.size() == 0, "Visit list is empty");
    }

    private static Patient patient(int id) {
        return new Patient(id, "Patient " + id, 25, "0700000000", "Test condition");
    }

    private static TreatmentRecord treatment(String id, int patientId, int hour) {
        return new TreatmentRecord(id, patientId, "Patient " + patientId,
                "Dr. Test", "Diagnosis", "Treatment",
                LocalDateTime.of(2026, 1, 1, hour, 0));
    }

    private static Visit visit(String id) {
        return new Visit(id, LocalDate.of(2026, 1, 1),
                "Dr. Test", "Diagnosis", "Treatment");
    }

    private static void check(boolean condition, String description) {
        checks++;
        if (!condition) {
            throw new AssertionError("FAILED: " + description);
        }
    }
}

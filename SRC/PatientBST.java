import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A manually implemented binary search tree keyed by patient ID. */
public class PatientBST {
    private static class TreeNode {
        private Patient patient;
        private TreeNode left;
        private TreeNode right;

        private TreeNode(Patient patient) {
            this.patient = patient;
        }
    }

    private TreeNode root;
    private int size;

    public boolean insert(Patient patient) {
        Objects.requireNonNull(patient, "Patient cannot be null");
        if (root == null) {
            root = new TreeNode(patient);
            size = 1;
            return true;
        }

        TreeNode current = root;
        while (true) {
            if (patient.getPatientId() == current.patient.getPatientId()) {
                return false;
            }
            if (patient.getPatientId() < current.patient.getPatientId()) {
                if (current.left == null) {
                    current.left = new TreeNode(patient);
                    size++;
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new TreeNode(patient);
                    size++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    public Patient search(int patientId) {
        TreeNode current = root;
        while (current != null) {
            int currentId = current.patient.getPatientId();
            if (patientId == currentId) {
                return current.patient;
            }
            current = patientId < currentId ? current.left : current.right;
        }
        return null;
    }

    public boolean delete(int patientId) {
        if (search(patientId) == null) {
            return false;
        }
        root = deleteNode(root, patientId);
        size--;
        return true;
    }

    private TreeNode deleteNode(TreeNode node, int patientId) {
        if (patientId < node.patient.getPatientId()) {
            node.left = deleteNode(node.left, patientId);
        } else if (patientId > node.patient.getPatientId()) {
            node.right = deleteNode(node.right, patientId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            TreeNode successor = findSmallest(node.right);
            node.patient = successor.patient;
            node.right = deleteNode(node.right, successor.patient.getPatientId());
        }
        return node;
    }

    private TreeNode findSmallest(TreeNode node) {
        TreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public List<Patient> inOrderTraversal() {
        List<Patient> patients = new ArrayList<>();
        collectInOrder(root, patients);
        return List.copyOf(patients);
    }

    private void collectInOrder(TreeNode node, List<Patient> patients) {
        if (node == null) {
            return;
        }
        collectInOrder(node.left, patients);
        patients.add(node.patient);
        collectInOrder(node.right, patients);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }
}

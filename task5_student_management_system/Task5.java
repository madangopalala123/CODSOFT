package task5_student_management_system;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student findStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            // Write column headings
            writer.println("Sno\tName\tRoll No\tGrade");

            // Write student data
            int sno = 1;
            for (Student student : students) {
                writer.printf("%d\t%s\t%d\t%s%n", sno++, student.getName(), student.getRollNumber(), student.getGrade());
            }
            System.out.println("Data saved to " + filename + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        students.clear(); // Clear existing students before loading from file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip the column headings
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");
                if (data.length == 4) {
                    String name = data[1];
                    int rollNumber = Integer.parseInt(data[2]);
                    String grade = data[3];
                    students.add(new Student(name, rollNumber, grade));
                }
            }
            System.out.println("Data loaded from " + filename + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Task5 {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter roll number: ");
                    int rollNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();

                    Student student = new Student(name, rollNumber, grade);
                    sms.addStudent(student);
                    break;
                case 2:
                    System.out.print("Enter roll number to remove: ");
                    int rollToRemove = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    sms.removeStudent(rollToRemove);
                    break;
                case 3:
                    System.out.print("Enter roll number to search: ");
                    int rollToSearch = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    Student foundStudent = sms.findStudent(rollToSearch);
                    if (foundStudent != null) {
                        System.out.println("Student found: " + foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    List<Student> allStudents = sms.getAllStudents();
                    if (allStudents.isEmpty()) {
                        System.out.println("No students in the system.");
                    } else {
                        System.out.println("Sno\tName\tRoll No\tGrade");
                        int sno = 1;
                        for (Student s : allStudents) {
                            System.out.printf("%d\t%s\t%d\t%s%n", sno++, s.getName(), s.getRollNumber(), s.getGrade());
                        }
                    }
                    break;
                case 5:
                    sms.saveToFile("student.txt");
                    break;
                case 6:
                    sms.loadFromFile("student.txt");
                    break;
                case 7:
                    System.out.println("Exiting application.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

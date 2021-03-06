package com.company.DAO;

import com.company.model.Student;
import com.company.model.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StudentDao {
    public void createStudent(Student student) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Students/StudentDatabase.txt", true))) {
            writer.write(student.toStringFileFormat());
            writer.newLine();
        } catch (IOException ignored) {
        }
    }

    public ArrayList<Student> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Database/Students/StudentDatabase.txt"))) {
            ArrayList<Student> students = new ArrayList<>();
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(tempLine, "-");
                long id = Long.parseLong(st.nextToken());
                String login = st.nextToken();
                String password = st.nextToken();
                String email = st.nextToken();
                students.add(new Student(id, login, password, email));
            }
            return students;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Students/StudentDatabase.txt"))) {
            writer.write("");
        } catch (IOException ignored) {
        }
    }

    public void deleteById(long inputId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Database/Students/StudentDatabase.txt"))) {
            ArrayList<Student> students = new ArrayList<>();
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(tempLine, "-");
                long id = Long.parseLong(st.nextToken());
                String login = st.nextToken();
                String password = st.nextToken();
                String email = st.nextToken();
                students.add(new Student(id, login, password, email));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Students/StudentDatabase.txt"));
            for (Student studentIter : students) {
                if (studentIter.getId() != inputId) {
                    createStudent(studentIter);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Student GetById(long inputId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Database/Students/StudentDatabase.txt"))) {
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(tempLine, "-");
                long id = Long.parseLong(st.nextToken());
                String login = st.nextToken();
                String password = st.nextToken();
                String email = st.nextToken();
                if (id == inputId) {
                    return new Student(id, login, password, email);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        //throw new ...Exception();
    }

    public void UpdateById (long inputId, Student newStudent) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Database/Students/StudentDatabase.txt"))) {
            ArrayList<Student> students = new ArrayList<>();
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(tempLine, "-");
                long id = Long.parseLong(st.nextToken());
                String login = st.nextToken();
                String password = st.nextToken();
                String email = st.nextToken();
                if((id == inputId)) {
                    students.add(new Student(newStudent.getId(), newStudent.getLogin(), newStudent.getPassword(), newStudent.getEmail()));
                } else {
                    students.add(new Student(id, login, password, email));
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("Database/Students/StudentDatabase.txt"));
            //Teacher teacher;
            for (Student studentIter : students) {
                // teacher = teacherIter;
                if (studentIter.getId() != inputId) {
                    createStudent(studentIter);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

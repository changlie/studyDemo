package com.changlie.pattern;

import org.junit.Test;

public class j2ee01Mvc {
    class Student {
        private String rollNo;
        private String name;
        public String getRollNo() {
            return rollNo;
        }
        public void setRollNo(String rollNo) {
            this.rollNo = rollNo;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    class StudentView {
        public void printStudentDetails(String studentName, String studentRollNo){
            System.out.print("Student: ");
            System.out.print("[Name: " + studentName);
            System.out.println(", Roll No: " + studentRollNo);
        }
    }

    class StudentController {
        private Student model;
        private StudentView view;

        public StudentController(Student model, StudentView view){
            this.model = model;
            this.view = view;
        }

        public void setStudentName(String name){
            model.setName(name);
        }

        public String getStudentName(){
            return model.getName();
        }

        public void setStudentRollNo(String rollNo){
            model.setRollNo(rollNo);
        }

        public String getStudentRollNo(){
            return model.getRollNo();
        }

        public void updateView(){
            view.printStudentDetails(model.getName(), model.getRollNo());
        }
    }

    @Test
    public void test() throws Exception{
        //从数据库获取学生记录
        Student model  = retriveStudentFromDatabase();

        //创建一个视图：把学生详细信息输出到控制台
        StudentView view = new StudentView();

        StudentController controller = new StudentController(model, view);

        controller.updateView();

        //更新模型数据
        controller.setStudentName("John");

        controller.updateView();
    }

    Student retriveStudentFromDatabase(){
        Student student = new Student();
        student.setName("Robert");
        student.setRollNo("10");
        return student;
    }
}

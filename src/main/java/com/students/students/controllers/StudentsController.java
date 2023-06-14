package com.students.students.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.students.students.models.Student;
import com.students.students.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentsController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllStudents(Model model){
        System.out.println("Getting all students");
        // TODO: get all students from database
        List<Student> students = studentRepo.findAll();
        
        model.addAttribute("st", students);
        return "students/showAll";
    }

    @PostMapping("/students/view/{uid}")
    public String viewStudent(Model model, @RequestParam(name = "uid") String uid, HttpServletResponse response){

        System.out.println("s");
        System.out.println("Get User " + uid);
        int id = Integer.parseInt(uid);
        Student s = studentRepo.findById(id).get();

        model.addAttribute("student", s);
        return "students/indivStudent";
    }

    @PostMapping("/students/remove")
    public String removeStudent(Model model, @RequestParam(name = "uidd") String uid, HttpServletResponse response){

        System.out.println("d");
        System.out.println("Delete User " + uid);
        int id = Integer.parseInt(uid);
        Student s = studentRepo.findById(id).get();
        studentRepo.delete(s); //delete from database

        return "students/removedStudent";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response, Model model){
        System.out.println("ADD student");
        String newName = newstudent.get("name");
        String newClr = newstudent.get("colour");
        String tempWeight = newstudent.get("weight");
        String tempHeight = newstudent.get("height");
        String tempGpa = newstudent.get("gpa");
        String newEmail = newstudent.get("email");
        
        if (tempWeight.length() == 0 || tempHeight.length() == 0 || newName.length() == 0 || tempGpa.length() == 0 || newEmail.length() == 0) {
            model.addAttribute("error", "Please ensure all input fields are filled");
            return "students/errorPage";
        }
        if (tempWeight.matches(".*[^0-9.].*") && tempHeight.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid weight and height inputs (non-numerical characters)");
            return "students/errorPage";
        } else if (tempWeight.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid weight input (non-numerical characters)");
            return "students/errorPage";
        } else if (tempHeight.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid height input (non-numerical characters)");
            return "students/errorPage";
        }
        int newWeight = Integer.parseInt(newstudent.get("weight"));
        int newHeight = Integer.parseInt(newstudent.get("height"));
        float newGpa = Float.parseFloat(newstudent.get("gpa"));


        if (newWeight < 30 && newHeight < 36){
            String error = "Invalid weight and height inputs (both too low)";
            model.addAttribute("error", error);
            System.out.println("wh");
            return "students/errorPage";
        } else if (newWeight < 30){
            model.addAttribute("error", "Invalid weight input (too low)");
            return "students/errorPage";
        } else if (newHeight < 36){
            model.addAttribute("error", "Invalid height input (too low)");
            return "students/errorPage";
        } else if (newWeight > 400 && newHeight > 96){
            model.addAttribute("error", "Invalid weight and height inputs (both too high)");
            return "students/errorPage";
        } else if (newWeight > 400){
            model.addAttribute("error", "Invalid weight input (too high)");
            return "students/errorPage";
        } else if (newHeight > 96){
            model.addAttribute("error", "Invalid height input (too high)");
            return "students/errorPage";
        }

        if (tempGpa.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid GPA input (non-numerical characters)");
            return "students/errorPage";
        }

        if (newGpa < 0.0){
            String error = "Invalid GPA input (too low)";
            model.addAttribute("error", error);
            return "students/errorPage";
        } else if (newGpa > 4.0) {
            String error = "Invalid GPA input (too high)";
            model.addAttribute("error", error);
            return "students/errorPage";
        }
        
        studentRepo.save(new Student(newName, newClr, newWeight, newHeight, newGpa, newEmail));
        response.setStatus(201);
        model.addAttribute("name", newName);
        return "students/addedStudent";
    }

    @PostMapping("/students/view/{uid}/edit")
    public String editStudent(Model model, @RequestParam(name = "uiddd") String uid, HttpServletResponse response){
        //List<Student> student = studentRepo.findByUid(uid);  
        System.out.println("s");
        System.out.println("Get User " + uid);
        int id = Integer.parseInt(uid);
        Student s = studentRepo.findById(id).get();

        model.addAttribute("student", s);
        //return "showUser";
        return "students/editStudent";
    }

    @PostMapping("/students/view/{uid}/edited")
    public String editedStudent(Model model, @RequestParam Map<String, String> newstudent, HttpServletResponse response){
        System.out.println("ADD student");
        String newName = newstudent.get("name");
        String newClr = newstudent.get("colour");
        String tempWeight = newstudent.get("weight");
        String tempHeight = newstudent.get("height");
        String tempGpa = newstudent.get("gpa");
        String newEmail = newstudent.get("email");
        String tempId = newstudent.get("uidddd");
        int id = Integer.parseInt(tempId);
        
        if (tempWeight.length() == 0 || tempHeight.length() == 0 || newName.length() == 0 || tempGpa.length() == 0 || newEmail.length() == 0) {
            model.addAttribute("error", "Please ensure all input fields are filled");
            return "students/errorPage";
        }
        if (tempWeight.matches(".*[^0-9.].*") && tempHeight.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid weight and height inputs (non-numerical characters)");
            return "students/errorPage";
        } else if (tempWeight.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid weight input (non-numerical characters)");
            return "students/errorPage";
        } else if (tempHeight.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid height input (non-numerical characters)");
            return "students/errorPage";
        }
        int newWeight = Integer.parseInt(newstudent.get("weight"));
        int newHeight = Integer.parseInt(newstudent.get("height"));
        float newGpa = Float.parseFloat(newstudent.get("gpa"));

        if (newWeight < 30 && newHeight < 36){
            String error = "Invalid weight and height inputs (both too low)";
            model.addAttribute("error", error);
            System.out.println("wh");
            return "students/errorPage";
        } else if (newWeight < 30){
            model.addAttribute("error", "Invalid weight input (too low)");
            return "students/errorPage";
        } else if (newHeight < 36){
            model.addAttribute("error", "Invalid height input (too low)");
            return "students/errorPage";
        } else if (newWeight > 400 && newHeight > 96){
            model.addAttribute("error", "Invalid weight and height inputs (both too high)");
            return "students/errorPage";
        } else if (newWeight > 400){
            model.addAttribute("error", "Invalid weight input (too high)");
            return "students/errorPage";
        } else if (newHeight > 96){
            model.addAttribute("error", "Invalid height input (too high)");
            return "students/errorPage";
        }

        if (tempGpa.matches(".*[^0-9.].*")){
            model.addAttribute("error", "Invalid GPA input (non-numerical characters)");
            return "students/errorPage";
        }

        if (newGpa < 0.0){
            String error = "Invalid GPA input (too low)";
            model.addAttribute("error", error);
            return "students/errorPage";
        } else if (newGpa > 4.0) {
            String error = "Invalid GPA input (too high)";
            model.addAttribute("error", error);
            return "students/errorPage";
        }

        Student s = studentRepo.findById(id).get();

        studentRepo.delete(s); //delete from database

        studentRepo.save(new Student(newName, newClr, newWeight, newHeight, newGpa, newEmail));

        return "students/edited";
    }
}
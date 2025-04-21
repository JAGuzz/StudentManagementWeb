package com.web.studentsApp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.studentsApp.dto.AddressDto;
import com.web.studentsApp.dto.EmailDto;
import com.web.studentsApp.dto.PhoneDto;
import com.web.studentsApp.dto.StudentDto;
import com.web.studentsApp.exception.ResourceNotFoundException;
import com.web.studentsApp.model.Email;
import com.web.studentsApp.model.Phone;
import com.web.studentsApp.model.Student;
import com.web.studentsApp.service.IStudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final IStudentService studentService;

    // Mostrar formulario de creación
    @GetMapping("/create")
    public String showCreateForm(Model model) {

        StudentDto studentDto = new StudentDto();
        studentDto.getEmails().add(new EmailDto());
        studentDto.getAddresses().add(new AddressDto());
        studentDto.getPhones().add(new PhoneDto());

        model.addAttribute("studentDto", studentDto);
        loadFormData(model);
        return "create-student";
    }

    // Procesar formulario
    @PostMapping("/create")
    public String createStudent(@Valid @ModelAttribute("studentDto") StudentDto studentDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(value = "addEmail", required = false) String addEmail,
            @RequestParam(value = "addPhone", required = false) String addPhone,
            @RequestParam(value = "addAddress", required = false) String addAddress) {
        
        model.addAttribute("bindingResult", bindingResult);
        // Validación mínima de elementos
        validateMinElements(studentDto, bindingResult);

        // Si el usuario presionó "Agregar Email"
        if (addEmail != null) {
            studentDto.getEmails().add(new EmailDto());

            // Recarga los datos necesarios para volver al formulario
            loadFormData(model);
            return "create-student";
        }

        // Si el usuario presionó "Agregar Teléfono"
        if (addPhone != null) {
            studentDto.getPhones().add(new PhoneDto());

            loadFormData(model);
            return "create-student";
        }

        // Si el usuario presionó "Agregar Direccion"
        if (addAddress != null) {
            studentDto.getAddresses().add(new AddressDto());

            loadFormData(model);
            return "create-student";
        }

        // Si hubo errores de validación
        if (bindingResult.hasErrors()) {
            loadFormData(model);
            return "create-student";
        }

        studentService.createStudent(studentDto);

        redirectAttributes.addFlashAttribute("success", "Student created successfully");
        return "redirect:/students/list";
    }

    // Listar todos los estudiantes
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student-list";
    }

    @GetMapping("/{id}")
    public String getStudentDetails(@PathVariable Long id, Model model) {
        StudentDto studentDto = studentService.getStudentById(id).get();
        model.addAttribute("student", studentDto);
        return "student-details";
    }

    // muestra el formulario de edicion
    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        StudentDto studentDto = studentService.getStudentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", String.valueOf(id)));

        // Asegura que haya al menos un campo por tipo para poder agregar más en la
        // vista
        if (studentDto.getEmails().isEmpty())
            studentDto.getEmails().add(new EmailDto());
        if (studentDto.getPhones().isEmpty())
            studentDto.getPhones().add(new PhoneDto());
        if (studentDto.getAddresses().isEmpty())
            studentDto.getAddresses().add(new AddressDto());

        model.addAttribute("studentDto", studentDto);
        loadFormData(model);
        return "edit-student";
    }

    // procesa formulario update
    @PostMapping("/update/{id}")
    public String updateStudent(@Valid @PathVariable Long id,
            @ModelAttribute("studentDto") StudentDto studentDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(value = "addEmail", required = false) String addEmail,
            @RequestParam(value = "addPhone", required = false) String addPhone,
            @RequestParam(value = "addAddress", required = false) String addAddress) {

        studentDto.setStudentId(id);
        if (addEmail != null) {
            studentDto.getEmails().add(new EmailDto());
        } else if (addPhone != null) {
            studentDto.getPhones().add(new PhoneDto());
        } else if (addAddress != null) {
            studentDto.getAddresses().add(new AddressDto());
        } else {
            if (bindingResult.hasErrors()) {
                loadFormData(model);
                return "edit-student";
            }

            studentService.updateStudent(id, studentDto);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully");
            return "redirect:/students/list";
        }

        // Recargar los datos necesarios
        model.addAttribute("studentDto", studentDto);
        loadFormData(model);
        return "edit-student";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudentById(id);
        redirectAttributes.addFlashAttribute("success", "Student deleted successfully");
        return "redirect:/students/list";
    }

    @GetMapping("/{studentId}/delete-phone/{phoneNumber}")
    public String deletePhone(@PathVariable Long studentId, @PathVariable String phoneNumber,
            RedirectAttributes redirectAttributes) {
        StudentDto studentDto = studentService.getStudentById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId.toString()));
        if (studentDto.getPhones().size() <= 1) {
            redirectAttributes.addFlashAttribute("error", "The only remaining phone cannot be deleted");
            return "redirect:/students/update/" + studentId;
        }

        studentService.deletePhone(studentId, phoneNumber);
        redirectAttributes.addFlashAttribute("success", "Phone deleted successfully");
        return "redirect:/students/update/" + studentId;
    }

    @GetMapping("/{studentId}/delete-email/{emailV}")
    public String deleteEmail(@PathVariable Long studentId, @PathVariable String emailV,
            RedirectAttributes redirectAttributes) {

        StudentDto studentDto = studentService.getStudentById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId.toString()));

        if (studentDto.getEmails().size() <= 1) {
            redirectAttributes.addFlashAttribute("error", "The only remaining email cannot be deleted");
            return "redirect:/students/update/" + studentId;
        }

        studentService.deleteEmail(studentId, emailV);
        redirectAttributes.addFlashAttribute("success", "Email deleted successfully");
        return "redirect:/students/update/" + studentId;
    }

    @GetMapping("/{studentId}/delete-address/{addressId}")
    public String deleteAddress(@PathVariable Long studentId, @PathVariable Long addressId,
            RedirectAttributes redirectAttributes) {

        StudentDto studentDto = studentService.getStudentById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId.toString()));

        if (studentDto.getAddresses().size() <= 1) {
            redirectAttributes.addFlashAttribute("error", "The only remaining address cannot be deleted");
            return "redirect:/students/update/" + studentId;
        }

        studentService.deleteAddress(studentId, addressId);
        redirectAttributes.addFlashAttribute("success", "Address deleted successfully");
        return "redirect:/students/update/" + studentId;
    }

    private static void loadFormData(Model model) {
        model.addAttribute("emailTypes", List.of(Email.EmailType.values()));
        model.addAttribute("phoneTypes", List.of(Phone.PhoneType.values()));
        model.addAttribute("genders", List.of(Student.Gender.values()));
    }

    private static void validateMinElements(StudentDto studentDto, BindingResult bindingResult) {

        if (studentDto.getEmails().isEmpty() || studentDto.getEmails().stream()
                .allMatch(e -> e.getEmail() == null || e.getEmail().trim().isEmpty())) {
            bindingResult.rejectValue("emails", "NotEmpty.studentDto.emails", "You must provide at least one email");
        }

        if (studentDto.getPhones().isEmpty() || studentDto.getPhones().stream()
                .allMatch(p -> p.getPhoneNumber() == null || p.getPhoneNumber().trim().isEmpty())) {
            bindingResult.rejectValue("phones", "NotEmpty.studentDto.phones", "You must provide at least one phone");
        }

        if (studentDto.getAddresses().isEmpty() || studentDto.getAddresses().stream()
                .allMatch(a -> (a.getAddressLine() == null || a.getAddressLine().trim().isEmpty()) &&
                        (a.getCity() == null || a.getCity().trim().isEmpty()) &&
                        (a.getZipPostcode() == null || a.getZipPostcode().trim().isEmpty()) &&
                        (a.getState() == null || a.getState().trim().isEmpty()))) {
            bindingResult.rejectValue("addresses", "NotEmpty.studentDto.addresses",
                    "You must provide at least one address");
        }
    }
}

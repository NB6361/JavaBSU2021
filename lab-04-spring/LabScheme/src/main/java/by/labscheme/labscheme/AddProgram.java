package by.labscheme.labscheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AddProgram {

    @Autowired
    ProgramRepo programs;

    @GetMapping("/add")
    public String secret(Model model) {
        return "add";
    }

    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addProgram(@RequestParam String code,
                             @RequestParam String result) throws IOException {
        Program program = new Program();
        program.code = code;
        program.result = result;
        programs.save(program);
        return "redirect:/add";
    }
}
package by.labscheme.labscheme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Controller
public class SchemeController {

    @Autowired
    ProgramRepo programs;

    @GetMapping("/")
    public String intpr(Model model, HttpSession session) {
        if (programs.count() == 0) {
            Program pr = new Program();
            pr.code = "(+ 2 3)";
            pr.result = "5";
            programs.save(pr);
        }
        model.addAttribute("programs", programs.findAll());
        model.addAttribute("code", session.getAttribute("code"));
        model.addAttribute("result", session.getAttribute("result"));
        return "intpr";
    }

    @PostMapping(value = "/",
                consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addProgram(@RequestParam String code, HttpSession session) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("./scheme-repl");
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
        pb.redirectInput(ProcessBuilder.Redirect.PIPE);
        Process p = pb.start();

        p.getOutputStream().write(code.getBytes(StandardCharsets.UTF_8));
        p.getOutputStream().close();

        InputStreamReader tmp = new InputStreamReader(p.getInputStream());
        BufferedReader reader = new BufferedReader(tmp);
        String str;
        StringBuilder buf = new StringBuilder();


        while ((str = reader.readLine()) != null) {
            buf.append(str);
        }
        String result = buf.toString();
        System.out.println("ANS " + buf.toString());

        session.setAttribute("code", code);
        session.setAttribute("result", result);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        programs.deleteAll();
        return "redirect:/";
    }
}

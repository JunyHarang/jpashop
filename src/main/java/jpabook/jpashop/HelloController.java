package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        // 이름이 data이고, 값이 hello!!인 것을 model에게 속성으로 만들어서 준다.
        model.addAttribute("data","hello!!");
        return "hello";
    }
}

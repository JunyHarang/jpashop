package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping
// Model에 Data를 실어서 View에 넘길 수 있다. data라는 Key에 값을 hello로 넘기겠다는 의미
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");

        // 여기서 return은 화면 이름(hello.html)이 된다.
        return "hello";
    }
}

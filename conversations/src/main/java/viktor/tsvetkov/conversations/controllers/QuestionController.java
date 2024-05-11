package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viktor.tsvetkov.conversations.dto.QuestionDto;
import viktor.tsvetkov.conversations.services.QuestionService;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public void askQuestion(@RequestBody QuestionDto questionDto) {
        questionService.askQuestion(questionDto);
    }
}

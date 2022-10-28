package de.htwberlin.vocab.export;

import java.util.List;

public interface QuestionGenerator {
    /**
     * Generates questions for a given vocab list
     * @param vocabListId ID of the vocab list
     * @return Question
     */
    public List<Vocab> generateQuestions(int vocabListId);

    public List<Translation> generateAnswers(int vocabListId);
}


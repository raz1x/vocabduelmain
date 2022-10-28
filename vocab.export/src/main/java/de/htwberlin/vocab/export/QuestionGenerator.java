package de.htwberlin.vocab.export;

public interface QuestionGenerator {
    /**
     * Generates a question for a given vocab list
     * @param vocabListId Id of the vocab list
     * @return Question
     */
    Question generateQuestion(int vocabListId);
}


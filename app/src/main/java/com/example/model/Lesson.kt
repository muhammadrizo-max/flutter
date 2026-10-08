package com.example.model

enum class QuestionType {
    SINGLE_CHOICE,
    TRUE_FALSE,
}

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val hint: String? = null
)

data class TheorySection(
    val title: String,
    val content: String,
    val codeSnippet: String? = null,
    val flutterTip: String? = null
)

enum class SimulatedWidgetType {
    TEXT_ONLY,
    CONTAINER_TEXT,
    ROW_ICONS,
    COLUMN_BUTTON,
    COUNTER_APP,
    LIST_VIEW,
    APP_BAR_SCAFFOLD
}

data class CodingChallenge(
    val id: String,
    val title: String,
    val instruction: String,
    val startingCode: String,
    val solutionCode: String,
    val requiredKeywords: List<String>,
    val forbiddenKeywords: List<String> = emptyList(),
    val widgetType: SimulatedWidgetType,
    val hint: String,
    val initialPreviewParam: String = ""
)

data class Lesson(
    val id: String,
    val moduleNumber: Int,
    val moduleTitle: String,
    val orderNumber: Int,
    val title: String,
    val subtitle: String,
    val iconName: String,
    val isFree: Boolean,
    val unlockCost: Int = 40,
    val xpReward: Int = 50,
    val coinReward: Int = 20,
    val estimatedMinutes: Int = 5,
    val theorySections: List<TheorySection>,
    val quizQuestions: List<QuizQuestion>,
    val codingChallenge: CodingChallenge
)

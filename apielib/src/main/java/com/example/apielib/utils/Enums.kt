@file:JvmName("Enums")
package com.example.apielib.utils

enum class EyeOpening{
    SPONTANEOUS,
    TO_SOUND,
    TO_PRESSURE,
    NONE,
    NOT_TESTABLE,
    SWELLING
}
enum class VerbalResponse{
    ORIENTATED,
    CONFUSED,
    WORDS,
    SOUNDS,
    NONE,
    NOT_TESTABLE,
    ENDOTRACHEAL_TUBE_OR_TRACHEOSTOMY
}

enum class BestMotorResponse{
    OBEYS_COMMANDS,
    LOCALISING,
    NORMAL_FLEXION,
    ABNORMA_FLEXION,
    EXTENSION,
    NONE,
    NOT_TESTABLE
}

enum class LimbMovement{
    NORMAL_POWER,
    MILD_WEAKNESS,
    SEVERE_WEAKNESS,
    SPASTIC_FLEXION,
    EXTENSION,
    NO_RESPONSE
}
enum class PupilReaction{
    REACTS,
    NO_REACTION,
    EYE_CLOSED
}
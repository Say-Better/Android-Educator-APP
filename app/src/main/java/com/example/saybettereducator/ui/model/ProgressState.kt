package com.example.saybettereducator.ui.model

import com.example.saybettereducator.domain.model.Symbol

data class ProgressState(
    val selectedMode: Int = 1, // 보기 모드
    val symbols: List<Symbol> = emptyList(), // 모든 상징
    val selectedSymbols: List<Symbol> = emptyList(), // 선택된 상징
    val isVoicePlaying: Boolean = false, // tts 출력 중
    val playingSymbol: Symbol? = null, // tts 출력 중인 상징
    val isBottomSheetOpen: Boolean = false, // 바텀 시트 열림 여부
    val responseFilter: ResponseFilterType = ResponseFilterType.NONE, // 학습자 응답에 따른 캠 필터 (예/아니오)
    val communicationCount: Int = 0, // 소통 횟수
    val timerTime: Long = 0, // 타이머 남은 시간 (밀리초)
    val timerMaxTime: Long = 0, // 타이머 최대 시간 (밀리초)
    val communicationState: CommunicationType = CommunicationType.NotCommunicating // 소통 상태
)


enum class ResponseFilterType {
    YES, NO, NONE
}

enum class CommunicationType {
    NotCommunicating, // 소통 안하는중
    Communicating, // 소통중
    Paused // 소통중지
}
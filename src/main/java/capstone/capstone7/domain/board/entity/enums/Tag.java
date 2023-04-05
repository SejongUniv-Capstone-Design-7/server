package capstone.capstone7.domain.board.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Tag {
    KNOWHOW, QUESTION;

    // DTO에서 ENUM 타입을 받고, KNOWHOW, QUESTION이 아닌 값이 들어왔다면 null 반환
    // 나중에 @Valid를 통해서 null validation 필요
    @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
    public static Tag get(String inputTag) {
        return Arrays.stream(values())
                .filter(tag -> tag.toString().equals(inputTag))
                .findAny()
                .orElse(null);
    }

}

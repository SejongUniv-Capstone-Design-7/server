package capstone.capstone7.domain.board.entity.enums;

import org.springframework.core.convert.converter.Converter;


public class TagConverter implements Converter<String, Tag> {

    @Override
    public Tag convert(String source) {
        return Tag.get(source.toUpperCase());
    }
}

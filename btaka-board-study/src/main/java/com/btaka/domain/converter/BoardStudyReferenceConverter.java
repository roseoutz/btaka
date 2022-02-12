package com.btaka.domain.converter;

import com.btaka.domain.entity.BoardStudyEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.mapping.DocumentPointer;

@WritingConverter
public class BoardStudyReferenceConverter implements Converter<BoardStudyEntity, DocumentPointer<String>> {

    @Override
    public DocumentPointer<String> convert(BoardStudyEntity source) {
        return () -> source.getOid();
    }
}

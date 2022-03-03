package com.btaka.dto;

import com.btaka.board.common.page.DefaultPageResult;
import com.btaka.board.common.page.PageResult;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListResponseDTO {

    private boolean success;
    private String error;
    private String errorMessage;
    private int statusCode;
    private PageResult<BoardDevStudyDTO> pageResult;

    private BoardListResponseDTO(Builder builder) {
        this.success = builder.success;
        this.error = builder.error;
        this.errorMessage = builder.errorMessage;
        this.statusCode = builder.statusCode;
        this.pageResult = builder.pageResult;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static BoardListResponseDTO of(PageResult<BoardDevStudyDTO> pageResult) {
        return new BoardListResponseDTO(true, null, null, 200, pageResult);
    }

    public static class Builder {
        private boolean success = true;
        private String error;
        private String errorMessage;
        private int statusCode = 200;
        private PageResult<BoardDevStudyDTO> pageResult;


        public Builder board(List<BoardDevStudyDTO> boardDevStudyDTOList) {
            this.pageResult = new DefaultPageResult<>(boardDevStudyDTOList);
            return this;
        }

        public Builder pageResult(PageResult<BoardDevStudyDTO> pageResult) {
            this.pageResult = pageResult;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public BoardListResponseDTO build() {
            return new BoardListResponseDTO(this);
        }
    }
}

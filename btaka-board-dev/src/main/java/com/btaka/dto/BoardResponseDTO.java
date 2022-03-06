package com.btaka.dto;

import com.btaka.board.common.page.DefaultPageResult;
import com.btaka.board.common.page.PageResult;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDTO  {

    private boolean success;
    private String error;
    private String errorMessage;
    private int statusCode;
    private BoardDevStudyDTO board;
    private PageResult<BoardDevStudyReplyDTO> pageResult;


    private BoardResponseDTO(Builder builder) {
        this.board = builder.board;
        this.pageResult = builder.pageResult;
        this.success = builder.success;
        this.error = builder.error;
        this.errorMessage = builder.errorMessage;
        this.statusCode = builder.statusCode;
    }

    public static BoardResponseDTO of(BoardDevStudyDTO board, PageResult<BoardDevStudyReplyDTO> pageResult) {
        return new BoardResponseDTO(true, null, null, 200, board, pageResult);
    }

    public static BoardResponseDTO of(PageResult<BoardDevStudyReplyDTO> pageResult) {
        return new BoardResponseDTO(true, null, null, 200, null, pageResult);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean success = true;
        private String error;
        private String errorMessage;
        private int statusCode = 200;
        private BoardDevStudyDTO board;
        private PageResult<BoardDevStudyReplyDTO> pageResult;


        public Builder board(BoardDevStudyDTO board) {
            this.board = board;
            return this;
        }

        public Builder replys(List<BoardDevStudyReplyDTO> boardDevStudyReplyDTOList) {
            this.pageResult = new DefaultPageResult<>(boardDevStudyReplyDTOList);
            return this;
        }

        public Builder pageResult(PageResult<BoardDevStudyReplyDTO> pageResult) {
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

        public BoardResponseDTO build() {
            return new BoardResponseDTO(this);
        }
    }
}

package com.btaka.dto;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {

    public BoardResponseDTO(BoardStudyDTO boardStudyDTO, List<BoardStudyReplyDTO> replyDTOList) {
        this.boardStudyDTO = boardStudyDTO;
        this.replyDTOList = replyDTOList;
        this.replyCounts = replyDTOList.size();
    }

    private BoardStudyDTO boardStudyDTO;

    private List<BoardStudyReplyDTO> replyDTOList;

    private int replyCounts;

    private boolean isSuccess = false;

    private String errorMessage;
}

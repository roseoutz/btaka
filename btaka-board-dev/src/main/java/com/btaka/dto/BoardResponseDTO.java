package com.btaka.dto;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
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

    public BoardResponseDTO(BoardDevStudyDTO boardDevStudyDTO, List<BoardDevStudyReplyDTO> replyDTOList) {
        this.boardStudy = boardDevStudyDTO;
        this.replyList = replyDTOList;
        this.replyCounts = replyDTOList.size();
    }

    private BoardDevStudyDTO boardStudy;

    private List<BoardDevStudyReplyDTO> replyList;

    private int replyCounts;

    private boolean isSuccess = false;

    private String errorMessage;
}

package com.issuemoa.batch.infrastructure.reader;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.domain.board.BoardRepository;
import com.issuemoa.batch.infrastructure.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class MakeKeywordReader implements ItemReader<List<Board>> {
    private final BoardRepository boardRepository;
    private int page = 0;

    @Override
    public List<Board> read() throws Exception {
        LocalDateTime startDay = DateUtil.getStartOfYesterday();
        LocalDateTime endDay = DateUtil.getEndOfYesterday();
        log.info("==> [MakeKeywordReader] {} ~ {}", startDay, endDay);

        int size = 1000;
        List<Board> boards = boardRepository.findByRegisterDateTimeBetween(startDay, endDay, PageRequest.of(page, size));

        // 조회 데이터가 없을 때 Reader 종료
        if (boards.isEmpty()) return null;

        page++;

        return boards;
    }
}
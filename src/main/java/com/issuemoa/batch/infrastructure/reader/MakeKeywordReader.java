package com.issuemoa.batch.infrastructure.reader;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class MakeKeywordReader implements ItemReader<List<Board>> {
    private final BoardRepository boardRepository;
    private int page = 0;
    private final int size = 500;

    @Override
    public List<Board> read() throws Exception {
        List<Board> boards = boardRepository.findAll(PageRequest.of(page, size)).getContent();

        // 페이징된 결과가 없을 때 Reader 종료
        if (boards.isEmpty()) {
            return null;
        }

        page++;

        log.info("==> [MakeKeywordReader] Page Number : {}", page);

        return boards;
    }
}
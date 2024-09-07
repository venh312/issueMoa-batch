package com.issuemoa.batch.application;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(String id) {
        return boardRepository.findById(id);
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }
    
    public void saveAll(List<Board> list) {
        boardRepository.saveAll(list);
    }

    public void deleteByType(String type) {
        boardRepository.deleteByType(type);
    }

    public List<Board> findByUrlIn(Set<String> urls) {
        return boardRepository.findByUrlIn(urls);
    }
}

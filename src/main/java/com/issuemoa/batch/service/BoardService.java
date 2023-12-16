package com.issuemoa.batch.service;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
    
    public List<Board> saveAll(List<Board> list) {
        return boardRepository.saveAll(list);
    }

    public Board updateBoard(String id, Board updatedBoard) {
//        if (boardRepository.existsById(id)) {
//            updatedBoard.setId(id);
//            return boardRepository.save(updatedBoard);
//        }
        return null;
    }

    public void deleteById(String id) {
        boardRepository.deleteById(id);
    }

    public void deleteByType(String type) {
        boardRepository.deleteByType(type);
    }

}

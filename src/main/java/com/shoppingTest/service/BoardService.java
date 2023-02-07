package com.shoppingTest.service;

import com.shoppingTest.domain.Board;
import com.shoppingTest.vo.PageRequestVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    public void register(Board board) throws Exception;

    public Board read(Long boardNo) throws Exception;

    public void modify(Board board) throws Exception;

    public void remove(Long boardNo) throws Exception;

    /*public List<Board> list() throws Exception;*/

    public Page<Board> list(PageRequestVO pageRequestVO) throws Exception;
}

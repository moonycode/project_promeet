package test.com.oopsw.model;

import static org.junit.Assert.*;
import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import com.oopsw.model.CommentReplyDAO;
import com.oopsw.model.CommentVO;
import com.oopsw.model.ReplyVO;

public class CommentReplyDAOTest {
    static Connection conn;
    static CommentReplyDAO dao;

    @Before
    public void start() {
        dao = new CommentReplyDAO();
    }

  @Test
  public void getCommentsTest(){
      System.out.println(dao.getComments(3));
  }

  @Test
  public void addCommentTest(){
      assertEquals(dao.addComment(new CommentVO(3,1,"test 댓글",null,null)), 1);
  }

    @Test
    public void addReplyTest(){
        assertEquals(dao.addReply(new ReplyVO(2,1,"test 답글",null,null)), 1);
    }

  @Test
  public void updateCommentTest(){
      assertEquals(dao.updateComment(new CommentVO(9,"test 수정")),1);
  }

  @Test
  public void updateReplyTest(){
      assertEquals(dao.updateReply(new ReplyVO(16,"update 테스트")), 1);
  }

  @Test
  public void deleteCommentTest(){
      assertEquals(dao.deleteComment(3), 1);
  }

  @Test
  public void deleteReplyTest(){
      assertEquals(dao.deleteReply(16), 1);
  }
}

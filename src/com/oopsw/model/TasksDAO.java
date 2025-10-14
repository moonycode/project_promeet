// TasksDAO.java
package com.oopsw.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

import com.oopsw.model.vo.*;

public class TasksDAO {
    private final SqlSession session;
    private static final String NS = "TasksMapper.";

    public TasksDAO(SqlSession session){ this.session = session; }

    public List<TaskRowFlatVO> selectTasksOrderByStartAsc(int projectNo){ return session.selectList(NS+"selectTasksOrderByStartAsc", projectNo); }
    public List<TaskRowFlatVO> selectTasksOrderByStartDesc(int projectNo){ return session.selectList(NS+"selectTasksOrderByStartDesc", projectNo); }
    public List<TaskRowFlatVO> selectTasksOrderByEndAsc(int projectNo){ return session.selectList(NS+"selectTasksOrderByEndAsc", projectNo); }
    public List<TaskRowFlatVO> selectTasksOrderByEndDesc(int projectNo){ return session.selectList(NS+"selectTasksOrderByEndDesc", projectNo); }

    public List<TaskAssigneeSummaryVO> selectTaskAssigneeSummary(int projectNo){
        return session.selectList(NS + "selectTaskAssigneeSummary", projectNo);
    }

    public int insertTask(TaskVO vo){ return session.insert(NS + "insertTask", vo); }
    public int insertTaskJoin(TaskJoinVO vo){ return session.insert(NS + "insertTaskJoin", vo); }
    public int updateTask(TaskVO vo){ return session.update(NS + "updateTask", vo); }
    public int softDeleteTask(int taskNo){ return session.update(NS + "softDeleteTask", taskNo); }

    public List<TaskAssigneeEditViewVO> selectAssigneeEditView(int projectNo, int taskNo){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo); p.put("taskNo", taskNo);
        return session.selectList(NS + "selectAssigneeEditView", p);
    }

    public int removeTaskMember(int taskNo, int projectJoinNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo); p.put("projectJoinNo", projectJoinNo);
        return session.update(NS + "removeTaskMember", p);
    }

    public int readdTaskMember(int taskNo, int projectJoinNo){
        Map<String,Object> p = new HashMap<>();
        p.put("taskNo", taskNo); p.put("projectJoinNo", projectJoinNo);
        return session.update(NS + "readdTaskMember", p);
    }

    public Integer findProjectJoinNo(int projectNo, String employeeId){
        Map<String,Object> p = new HashMap<>();
        p.put("projectNo", projectNo); p.put("employeeId", employeeId);
        return session.selectOne(NS + "findProjectJoinNo", p);
    }
}

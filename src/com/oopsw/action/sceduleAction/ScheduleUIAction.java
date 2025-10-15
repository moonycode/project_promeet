package com.oopsw.action.sceduleAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.oopsw.action.Action;
import com.oopsw.model.ScheduleVO;

public class ScheduleUIAction implements Action {

    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        return "schedule.jsp";
    }
}

<%@ page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap, java.util.Map, com.google.gson.Gson" %>
<%
    Boolean result = (Boolean) request.getAttribute("result");

    Map<String, Object> map = new HashMap<>();
    map.put("result", result);

    String json = new Gson().toJson(map);
    out.print(json);
%>

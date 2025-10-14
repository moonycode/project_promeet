<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

[
<c:forEach var="vo" items="${list}" varStatus="s">
  {
    "checkListNo": ${vo.checkListNo},
    "taskNo": ${vo.taskNo},
    "completeFlag": ${vo.completeFlag},
    "contents": "${vo.contents}",
    "delDate": "${vo.delDate}"
  }<c:if test="${!s.last}">,</c:if>
</c:forEach>
]
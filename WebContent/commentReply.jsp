<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
[
<c:forEach var="vo" items="${list}" varStatus="s">
  {
    "commentNo": ${vo.commentNo},
    "replyNo": ${vo.replyNo},
    "taskName": "${vo.taskName}",
    "commentWriter": "${vo.commentWriter}",
    "commentContents": "${vo.commentContents}",
    "commentIndate": "${vo.commentIndate}",
    "commentFilename": "${vo.commentFilename}",
    "replyWriter": "${vo.replyWriter}",
    "repliesContents": "${vo.repliesContents}",
    "repliesIndate": "${vo.repliesIndate}",
    "repliesFilename": "${vo.repliesFilename}",
    "commentUpdate": "${vo.commentUpdate}",
    "repliesUpdate": "${vo.repliesUpdate}",
    "commentDeldate": "${vo.commentDeldate}",
    "repliesDeldate": "${vo.repliesDeldate}"
  }<c:if test="${!s.last}">,</c:if>
</c:forEach>
]

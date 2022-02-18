<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.List"%>

<%
Optional<List<String[]>> optList = Optional.ofNullable((List<String[]>)request.getAttribute("list"));
List<String[]> list = new ArrayList<>();
if(optList.isPresent()) {
	list = optList.get();
}
%>

<%--[
{"ID":1,"OptName":"トッピング無料券","POINT":500},
{"ID":5,"OptName":"チャーハン無料券","POINT":1500},
{"CODE":"<%=code%>","ID":<%= id %>,"OptName":"<%= name %>","POINT":<%= point %>}
]--%>

[
<%for (String[] s : list){%>
{"ID":<%=s[0]%>,"OptName":"<%=s[1]%>","POINT":<%=s[2]%>}
<%}%>

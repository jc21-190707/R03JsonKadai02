<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String POINT = (String) request.getAttribute("POINT");
%>
{"POINT":<%= POINT %>}
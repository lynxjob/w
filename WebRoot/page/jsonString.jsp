<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--默认是true，不然输出的JSON字符串被转码--%>
<s:property value="jsonString" escape="false" />
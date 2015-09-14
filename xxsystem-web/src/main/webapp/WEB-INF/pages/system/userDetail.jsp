<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<html>
	<head>
	</head>
	<body>
		<div style="width: 100%;height: 100%;background-color: white;overflow: auto;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;font-family: '宋体', 'sans-serif'; border:0px;margin:0px;">
		 		<tr>
		      		<td width="20%" style="border:1px dotted #999;height:28px;text-align:right; padding-right:5px;">
						账号：
					</td>
				    <td width="30%" style="border-top: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
				    
						${user.loginName}&nbsp;
					</td>
					<td style="border-top: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;text-align:right; ">工号：</td>
                    <td style="border-top: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">${user.jobNumber}&nbsp;</td>
		  		</tr>
		 		<tr>
		      		<td width="20%" style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;text-align:right; padding-right:5px;">
						姓名：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.userName}&nbsp;
					</td>
					<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;text-align:right; ">职务：</td>
                    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">${user.job}&nbsp;</td>
		  		</tr>
		  		
		 		<tr>
		      		<!-- <td width="20%" style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;text-align:right; padding-right:5px;">
						性别：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						<c:choose>
							<c:when test="${user.gender == 0}">
								男
							</c:when>
							<c:otherwise>
								女
							</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
					 -->
					<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;text-align:right; height:28px;">电子邮件：</td>
                    <td  colspan="3" style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">${user.email} &nbsp;</td>
		  		</tr>
                
				<tr>
					<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;">
						单位：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
				      ${user.unit.unitName}&nbsp;					
					</td>
		      		<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;;padding-left:5px;text-align:right;">
						是否负责人：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						<c:choose>
							<c:when test="${user.isPrincipal == 1}">
								是
							</c:when>
							<c:otherwise>
								否
							</c:otherwise>
						</c:choose>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right;padding-right:5px; ">
						政治面貌：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.political}&nbsp;					
					</td>
		      		<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;text-align:right; ">
						出生日期：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd"/> &nbsp;
					</td>
				</tr>
				<tr>
					<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;">
						家庭地址：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.address}&nbsp;					
					</td>
		      		<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;text-align:right;">
						身份证：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.identityCard} &nbsp;
					</td>
				</tr>
				<tr>
					<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;">
						专业方向：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.majorIn}&nbsp;					
					</td>
		      		<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;text-align:right; ">
						手机：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.cellPhone} &nbsp;
					</td>
				</tr>
				<tr>
					<td style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;text-align:right; padding-right:5px;">
						办公电话：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.officePhone}&nbsp;					
					</td>
		      		<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;padding-left:5px;text-align:right;">
						家庭电话：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						${user.homePhone} &nbsp;
					</td>
				</tr>
				<tr>
		      		<td width="20%" style="border-left: #999 1px dotted;border-right: #999 1px dotted;border-bottom: #999 1px dotted;height:28px;text-align:right; padding-right:5px;">
						用户照片：
					</td>
				    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
						<c:choose>
							<c:when test="${empty user.pictureURL}">
								<img src="${ctx}/images/photo.jpg" width ="110" height="124"  />
							</c:when>
							<c:otherwise>
								<img src="${ctx}/${user.pictureURL}" width ="110" height="124"  />
							</c:otherwise>
						</c:choose>
					</td>
					<td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;text-align:right; ">用户签字：</td>
                    <td style="border-right: #999 1px dotted;border-bottom: #999 1px dotted;padding-left:5px;">
                     <c:choose>
							<c:when test="${empty user.pictureURL}">
								<img src="${ctx}/images/signer.jpg" width ="110" height="124"  />
							</c:when>
							<c:otherwise>
								<img src="${ctx}/${user.signerPictureURL}" width ="110" height="124"  />
							</c:otherwise>
						</c:choose>
                    </td>
		  		</tr>
			</table>
		</div>
	</body>
</html>

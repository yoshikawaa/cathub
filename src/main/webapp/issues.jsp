<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="http://cdn.metroui.org.ua/css/metro.min.css" />
<link rel="stylesheet" type="text/css" href="http://cdn.metroui.org.ua/css/metro-icons.min.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="http://cdn.metroui.org.ua/js/metro.min.js"></script>

<title>Issues</title>
</head>
<body>

	<h1>
		<span class="mif-github"></span> Issues
	</h1>
	<table class="table striped hovered">
		<thead>
			<tr>
				<th class="sortable-column">Repository</th>
				<th class="sortable-column">Title</th>
				<th class="sortable-column">Labels</th>
				<th class="sortable-column">State</th>
				<th class="sortable-column">Assignees</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="issue" items="${issues}" varStatus="status">
				<tr>
					<td>${issue.repository}</td>
					<td><span class="mif-link"></span><a href="${issue.htmlUrl}">#${issue.number} ${issue.title}</a></td>
					<td><c:forEach var="label" items="${issue.labels}">
							<span class="mif-link"></span>
							<a class="button fg-white" style="background-color: #${label.color" href="${label.url}">${label.name}</a>
						</c:forEach></td>
					<td>${issue.state}</td>
					<td><c:forEach var="assignee" items="${issue.assignees}">
							<span class="mif-link"></span>
							<a href="${assignee.htmlUrl}">${assignee.login}</a>
						</c:forEach></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		$.Notify({
			type : 'success',
			caption : 'Issues',
			content : 'Get Complated'
		});
	</script>

</body>
</html>
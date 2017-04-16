<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.min.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.min.js"></script>
<title>Cathub - Issues / Pull Requests</title>
</head>
<body>

  <h1>
    <i class="icon github"></i>Issues / Pull Requests
  </h1>

  <button class="fluid ui button labeled icon primary modalable">
    <i class="icon search"></i>edit search condition
  </button>

  <form:form servletRelativeAction="search" method="GET" modelAttribute="query" class="ui fullscreen modal">
    <div class="header">Please input search condition.</div>
    <div class="content">
      <div class="ui form">
        <div class="fields">
          <div class="field">
            <form:label path="author">
              <i class="icon user"></i>author</form:label>
            <form:input path="author" placeholder="Please input author" />
          </div>
          <div class="field">
            <form:label path="assignee">
              <i class="icon user"></i>assignee</form:label>
            <form:input path="assignee" placeholder="Please input assignee" />
          </div>
          <div class="field">
            <form:label path="is">
              <i class="icon linkify"></i>is</form:label>
            <form:select path="is">
              <form:option value="" label="" />
              <form:options items="${is}" />
            </form:select>
          </div>
        </div>
      </div>
    </div>
    <div class="actions">
      <div class="ui buttons">
        <button class="ui button labeled icon deny">
          <i class="icon remove"></i>cancel
        </button>
        <div class="or"></div>
        <form:button class="ui button labeled icon primary">
          <i class="icon search"></i>search</form:button>
      </div>
    </div>
  </form:form>

  <script type="text/javascript">
			$('.modalable').on('click', function() {
				$('.ui.modal').modal('show');
			});
		</script>

  <c:choose>
    <c:when test="${empty issues}">
      <script type="text/javascript">
							$('.ui.modal').modal('show');
						</script>
    </c:when>
    <c:otherwise>
      <div class="ui message positive">
        <i class="icon close"></i>
        <div class="header">Search issues completed!</div>
        <p>Getting ${issues.size()} issues / pull requests.</p>
      </div>

      <table class="ui celled table">
        <thead>
          <tr>
            <th>Repository/Issue No.</th>
            <th>Title</th>
            <th>Labels</th>
            <th>Milestone</th>
            <th>State</th>
            <th>Assignees</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="issue" items="${issues}" varStatus="status">
            <tr>
              <td><div class="ui ribbon label">
                  <a href="${issue.htmlUrl}">${issue.repository}#${issue.number}</a>
                </div></td>
              <td>${issue.title}
                <div class="ui label">
                  <i class="icon comments"></i>${issue.comments}</div>
              </td>
              <td><c:forEach var="label" items="${issue.labels}">
                  <a class="ui tag label" style="background-color: ${label.colorCode}" href="${label.url}">${label.name}</a>
                </c:forEach></td>
              <td><c:set var="milestone" value="${issue.milestone}" /> <c:if test="${not empty issue.milestone}">
                  <i class="icon wait"></i>
                  <a href="${milestone.htmlUrl}">${milestone.title}</a>
                </c:if></td>
              <td><c:choose>
                  <c:when test="${issue.state == 'closed'}">
                    <div class="ui label blue">
                      <i class="icon checkmark"></i>${issue.state}</div>
                  </c:when>
                  <c:otherwise>
                    <div class="ui label red">
                      <i class="icon bomb"></i>${issue.state}</div>
                  </c:otherwise>
                </c:choose></td>
              <td><c:forEach var="assignee" items="${issue.assignees}">
                  <i class="icon user"></i>
                  <a href="${assignee.htmlUrl}">${assignee.login}</a>
                </c:forEach></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <script type="text/javascript">
							$('.message .close').on('click', function() {
								$(this).closest('.message').transition('fade');
							});
						</script>
    </c:otherwise>
  </c:choose>

</body>
</html>